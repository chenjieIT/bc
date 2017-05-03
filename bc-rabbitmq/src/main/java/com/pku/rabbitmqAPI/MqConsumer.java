package com.pku.rabbitmqAPI;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Connection;

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
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;


public class MqConsumer {
	private static Logger logger = LoggerFactory.getLogger(MqConsumer.class);
	private final static String QUEUE_NAME = "transaction";

	public static void main(String[] argv) throws java.io.IOException,
	             java.lang.InterruptedException, TimeoutException {

	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();
	    //redisUtil.saveMessage(message);
	    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
	    Consumer consumer = new DefaultConsumer(channel) {
	    	private RedisUtil redisUtil =RedisUtil.getRedisUtil();
	        @Override
	        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
	            throws IOException {
	          String message = new String(body, "UTF-8");
	          Transiction t = JSON.parseObject(message, Transiction.class);
	          String signData = t.getSignInfo();
	          t.setSignInfo(null);
	          String data = JSON.toJSONString(t);
	          try {
	        	  	if(VerifySign.verifySign(data, signData)) {
	        	  		redisUtil.saveMessage(data);
	        	  		System.out.println(" [x] Received '" + message + "'");
	        	  	} else {
	        	  		logger.info("verifySign data failed: "+data);
	        	  	}
			   } catch (Exception e) {
				   logger.error("verifySign error"+e.getLocalizedMessage());
			   }
	          
	        }
	      };
	      channel.basicConsume(QUEUE_NAME, true, consumer);
	}
}
