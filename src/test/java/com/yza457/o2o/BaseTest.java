package com.yza457.o2o;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * used to config spring and junit. When junit starts, load Spring IoC container
 */
@RunWith(SpringJUnit4ClassRunner.class)
// let junit know the location of spring config file
@ContextConfiguration(locations={"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
public class BaseTest {
}
