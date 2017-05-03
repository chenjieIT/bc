package com.pku.jstorm;

import java.util.UUID;

import org.junit.Test;

public class TestUUID {
	@Test
	public void testUUID() {
		String s = UUID.randomUUID().toString();//用来生成数据库的主键id非常不错.
		System.out.println(s);
	}
}
