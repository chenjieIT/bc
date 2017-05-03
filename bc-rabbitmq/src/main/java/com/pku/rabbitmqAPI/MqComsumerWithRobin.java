package com.pku.rabbitmqAPI;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.pku.bean.Transiction;
import com.pku.multithread.VerifySign;
import com.pku.redisAPI.RedisUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
/*
 * 运行两次这个文件即可
 */
public class MqComsumerWithRobin {
	
	private static Logger logger = LoggerFactory.getLogger(MqComsumerWithRobin.class);
	private final static String QUEUE_NAME = "transactionDurable";

	public static void main(String[] argv) throws java.io.IOException,
	             java.lang.InterruptedException, TimeoutException {

	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    final Connection connection = factory.newConnection();
	    final Channel channel = connection.createChannel();
	    boolean durable = true;//是否持久化rabbitmq信息
	    channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
	    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
	    channel.basicQos(1);
	    final Consumer consumer = new DefaultConsumer(channel) {
	    	private RedisUtil redisUtil =RedisUtil.getRedisUtil();
	    	@Override
	    	public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
	    	    String message = new String(body, "UTF-8");
	    	    System.out.println(" [x] Received '" + message + "'");
	          Transiction t = JSON.parseObject(message, Transiction.class);
	          String signData = t.getSignInfo();
	          t.setSignInfo(null);
	          String data = JSON.toJSONString(t);
	          try {
	        	  	if(VerifySign.verifySign(data, signData)) {
	        	  		redisUtil.saveMessage(data);
	        	  		try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
	        	  		System.out.println(" [x] Received '" + message + "'");
	        	  	} else {
	        	  		logger.info("verifySign data failed: "+data);
	        	  	}
			   } catch (Exception e) {
				   logger.error("verifySign error"+e.getLocalizedMessage());
			   } finally {
	    	      System.out.println(" [x] Done");
	    	      channel.basicAck(envelope.getDeliveryTag(), false);
	    	    }
	    	  }
	    	};
	    boolean autoAck = false;
	    channel.basicConsume(QUEUE_NAME, autoAck, consumer);
	}
}
