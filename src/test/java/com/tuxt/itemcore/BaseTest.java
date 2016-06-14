package com.tuxt.itemcore;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author shilei6@asiainfo.com
 * @since 2015-04-20
 */
public class BaseTest extends TestCase {

	public static ApplicationContext context = new ClassPathXmlApplicationContext(
			"spring/spring-all.xml");

	public void testContext() {
		assertNotNull("ApplicationContext is null!", context);
	}

}
