package com.pku.log;

import org.junit.Test;

public class UtilTest {
	@Test
	public void testLog4j(){
		System.out.println(ClassLoader.getSystemResource("conf/log4j.properties"));
		System.out.println(ClassLoader.getSystemResource("conf/log4j.xml"));
	}
}
