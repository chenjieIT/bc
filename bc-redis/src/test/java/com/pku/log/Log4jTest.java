package com.pku.log;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4jTest {
	static Logger logger = LoggerFactory.getLogger(Log4jTest.class);
	
	@Test
	public void testLog(){
		logger.debug("this is a test!!!!");
	}
}
