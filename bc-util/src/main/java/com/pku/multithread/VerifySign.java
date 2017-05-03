package com.pku.multithread;

import com.pku.sign.RSACoder;
/**
 * 公钥验证签名
 * @author chenhanghang
 *
 */

public class VerifySign {
	private static String publicKey = 
			"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCf7tkIGp+qdtKuLndCYZGccX5rR75Ocf4wj7Eg"
			+"QhIwnYdJUJF4FftBKqR7NC2WlSdHN0D3ynrq8xmnrLJtDeoon3HS6QCmd87DYjFY4SU6pj4hSwmW"
			+"jpN1OoEvI5BYRX73hd3ddbJVr7+bwJhJIEEccjskcdam1FssHS2rRcSywQIDAQAB";
	
	public static boolean verifySign(byte[] data, String signData) throws Exception{
		return RSACoder.verify(data, VerifySign.publicKey, signData);
	}
	public static boolean verifySign(String data, String signData) throws Exception{
		return VerifySign.verifySign(data.getBytes(), signData);
	}
}
