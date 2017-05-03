package com.pku.multithread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pku.sign.RSACoder;
/**
 * 私钥加密
 * @author chenhanghang
 *
 */
public class MultiSign implements Runnable{
	private static final Logger LOG = LoggerFactory.getLogger(MultiSign.class);
	private String privateKey = 
			"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ/u2Qgan6p20q4ud0JhkZxxfmtH"
			+"vk5x/jCPsSBCEjCdh0lQkXgV+0EqpHs0LZaVJ0c3QPfKeurzGaessm0N6iifcdLpAKZ3zsNiMVjh"
			+"JTqmPiFLCZaOk3U6gS8jkFhFfveF3d11slWvv5vAmEkgQRxyOyRx1qbUWywdLatFxLLBAgMBAAEC"
			+"gYAQX04ec0NHGxTkOeOBiyVx43hymQyVUTwZUoxqZfkguu+fhLzvkyh+elQOPMAx+yDjPHfNJhBB"
			+"PxOwQ5Ccc2E2D58IlfortzXgrQKc8WOTnAoQXoHEigrQNk5pjRsO7fEEomsw9jQrkc8/mqJFD5Yf"
			+"Q7TE1n5DxVWLWknnB8I6ZQJBAOYdcXeEfiywy4zZ7VVWngojcmXToLC6xlcVoU/41LeqiqjCddSR"
			+"sYlfkZ0Ms07ZmGcSuYOXm5FwZiLXwEPSCjsCQQCx7GScJ5LQrz0hzGnUNeeatHFwjralgx+3Uvxj"
			+"RT6aYrtC15WEnmElUE2Ho0EwkAuCjVL1Jh3vkZKV+Jw5kmszAkEAnU2DQzBPED3K534Rv0R2xHd2"
			+"vCTus+D5tI4Ut5Zh0yvBnZtQ8P6nXkD2SWVlLYGRrOQf7NS2g7VSHH9f7vymAQJBAIhy1iW7Y6iO"
			+"+77z2SVZwaivQWAk0oXULXqWNeCbtgsINLxqQMxX+gB3uT2QQepQTcNtRnWjKcoUReqloom0dGkC"
			+"QEcyK/SOT0adhjUx3DiKWKZlgAS6BVWbK8jVsx5PkkyVg0tuf34Slya0d15+Z7zs/Gc0qV2lvmQa"
			+"MUtGfQiy5TI=";
	private byte[] data;
	public MultiSign(String data) {
		this.data = data.getBytes();
	}
	
	public MultiSign(byte[] data) {
		this.data = data;
	}
	
	@Override
	public void run() {
		try {
			String signData = RSACoder.signature(this.privateKey, data);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		
	}

}
