package cc.lee.test.cli.spring;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration (locations = { "classpath:/spring-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseTest {

}
