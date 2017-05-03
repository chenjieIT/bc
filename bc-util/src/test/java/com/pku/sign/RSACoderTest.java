package com.pku.sign;

import java.util.Map;

import org.junit.Test;

public class RSACoderTest {
	
	//@Test
	public void testEncryptAndDecrypt() throws Exception{
		Map<String,Object> keyMap = RSACoder.initKey();
		System.out.println("私钥");
		System.out.println(RSACoder.getPrivateKey(keyMap));
		System.out.println("公钥");
		System.out.println(RSACoder.getPublicKey(keyMap));
		String data = "wo shi mai bao de xiao hangjia";
		
		System.out.println("原始数据：" + data);
		byte [] enData = RSACoder.encryptByPrivateKey(data.getBytes(), RSACoder.getPrivateKey(keyMap));
		System.out.println("加密后的数据"); 
		System.out.println(enData.toString());
		byte [] deData = RSACoder.decryptByPublicKey(enData, RSACoder.getPublicKey(keyMap));
		System.out.println("解密后的数据");
		System.out.println(new String(deData));
	}
	
	@Test
	public void testSign() throws Exception {
		Map<String,Object> keyMap = RSACoder.initKey();
		System.out.println("私钥");
		System.out.println(RSACoder.getPrivateKey(keyMap));
		System.out.println("公钥");
		System.out.println(RSACoder.getPublicKey(keyMap));
		String data = "wo shi mai bao de xiao hangjia";
		String signData = RSACoder.signature(RSACoder.getPrivateKey(keyMap), data.getBytes());
		System.out.println(RSACoder.verify(data.getBytes(), RSACoder.getPublicKey(keyMap), signData));
	}
}
