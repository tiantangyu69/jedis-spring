package cc.lee.redis.proxy;

import javassist.*;
import javassist.bytecode.AccessFlag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lizhitao on 16-3-7.
 * 代理Jedis客户端,使用javassist
 */
abstract class AbstractProxy<T> {
    private Logger log = LoggerFactory.getLogger(getClass());
    protected final ClassPool classPool;
    protected static final String PROXY_SUFFIX = "$JavassistProxy";// 代理对象的名称后缀

    protected AbstractProxy() {
        classPool = ClassPool.getDefault();
        ClassPath classPath = new LoaderClassPath(Thread.currentThread().getContextClassLoader());
        classPool.appendClassPath(classPath);
    }

    /**
     * 创建代理对象
     *
     * @param parentClass
     * @param dumpProxyClass
     * @param <E>
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	final <E extends T> Class<E> newProxy(Class<? extends T> parentClass, boolean dumpProxyClass) throws Exception {
        String proxyClassName = parentClass.getName().concat(PROXY_SUFFIX);
        synchronized (parentClass) {
            try {
                classPool.get(proxyClassName);
                return (Class<E>) Class.forName(proxyClassName);
            } catch (Exception e) {
                CtClass ctClass = makeClass(parentClass, proxyClassName);

                if (dumpProxyClass) {
                    ctClass.writeFile();
                }

                return ctClass.toClass();
            }
        }
    }

    /**
     * javassist创建对象
     *
     * @param parentClass
     * @param proxyClassName
     * @return
     */
    private CtClass makeClass(Class<? extends T> parentClass, String proxyClassName) throws Exception {
        CtClass parentCtClass = classPool.get(parentClass.getName());
        Map<CtMethod, List<CtClass>> methodClassesMapper = new HashMap<CtMethod, List<CtClass>>();
        scanInterfaces(parentCtClass, methodClassesMapper);

        CtClass newClass = classPool.makeClass(proxyClassName, parentCtClass);
        inheritAndMakePublicAllConstructors(newClass);

        for (CtMethod ctMethod : newClass.getMethods()) {
            if ((ctMethod.getModifiers() & AccessFlag.ABSTRACT) != 0) {
                List<CtClass> definedClasses = methodClassesMapper.get(ctMethod);
                if (log.isDebugEnabled()) {
                    List<String> names = new ArrayList<String>();
                    for (CtClass definedClass : definedClasses) {
                        names.add(definedClass.getName());
                    }
                    log.debug(ctMethod.toString() + " defined in " + names);
                }
                implementAbstract(newClass, ctMethod, definedClasses);
            }
        }

        newClass.setModifiers(newClass.getModifiers() & ~AccessFlag.ABSTRACT);

        return newClass;

    }

    /**
     * 扫描接口
     *
     * @param theClass
     * @param methodClassesMapper
     * @throws Exception
     */
    private void scanInterfaces(CtClass theClass, Map<CtMethod, List<CtClass>> methodClassesMapper) throws Exception {
        for (CtClass iface : theClass.getInterfaces()) {
            for (CtMethod method : iface.getMethods()) {
                List<CtClass> classes = methodClassesMapper.get(method);
                if (classes == null) {
                    classes = new ArrayList<CtClass>();
                    methodClassesMapper.put(method, classes);
                }
                classes.add(iface);
            }
            scanInterfaces(iface, methodClassesMapper);
        }
    }

    /**
     * Copy from {@link javassist.CtNewClass#inheritAllConstructors()} , and
     * make constructors to public.
     *
     * @param newClass
     * @throws CannotCompileException
     * @throws NotFoundException
     */
    private void inheritAndMakePublicAllConstructors(CtClass newClass) throws CannotCompileException, NotFoundException {
        CtClass superclazz;
        CtConstructor[] cs;

        superclazz = newClass.getSuperclass();
        cs = superclazz.getDeclaredConstructors();

        int n = 0;
        for (int i = 0; i < cs.length; ++i) {
            CtConstructor c = cs[i];
            int mod = c.getModifiers();
            if (isInheritable(mod, newClass, superclazz)) {
                CtConstructor cons = CtNewConstructor.make(c.getParameterTypes(),
                        c.getExceptionTypes(), newClass);
                cons.setModifiers(Modifier.PUBLIC);
                newClass.addConstructor(cons);
                ++n;
            }
        }

        if (n < 1)
            throw new CannotCompileException(
                    "no inheritable constructor in " + superclazz.getName());

    }

    private boolean isInheritable(int mod, CtClass newClass, CtClass superclazz) {
        if (Modifier.isPrivate(mod))
            return false;

        if (Modifier.isPackage(mod)) {
            String pname = newClass.getPackageName();
            String pname2 = superclazz.getPackageName();
            if (pname == null)
                return pname2 == null;
            else
                return pname.equals(pname2);
        }

        return true;
    }

    protected String join(String... lines) {
        StringBuilder builder = new StringBuilder();
        for (String line : lines) {
            builder.append(line);
        }
        return builder.toString();
    }

    protected abstract void implementAbstract(CtClass newClass, CtMethod abstractMethod, List<CtClass> definedClasses) throws Exception;
}
