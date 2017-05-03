package com.pku.json;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.pku.bean.Transiction;

public class TestFastJson {
	@Test
	public void testJson(){
		Transiction t =new Transiction();
		t.setDest("12311");
		t.setId("1111");
		t.setMessage("test");
		t.setSource("3333333333");
		t.setValue(123);
		System.out.println(JSON.toJSONString(t));
	}
}
