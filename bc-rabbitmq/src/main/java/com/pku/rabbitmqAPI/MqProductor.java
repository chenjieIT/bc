package com.pku.rabbitmqAPI;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;

public class MqProductor {
	private final static String QUEUE_NAME = "transactionDurable";
	private Channel channel;
	private Connection connection;
	private static MqProductor mqProductor=null;
	private MqProductor() throws IOException, TimeoutException{
		  ConnectionFactory factory = new ConnectionFactory();
		  factory.setHost("localhost");
		  this.connection = factory.newConnection();
		  this.channel = connection.createChannel();
		  //this.channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		  boolean durable = true;
		  this.channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
	}
	public static MqProductor createProductor(){
		if(mqProductor == null){
			try {
				mqProductor =new MqProductor();
			} catch (IOException e) {
				System.out.println("error create Productor");
			} catch (TimeoutException e) {
				System.out.println("error create Productor");
			}
		}
		return mqProductor;
	}

	public void sendMessageToQueue(String message) throws IOException {
		  this.channel.basicPublish( "", QUEUE_NAME,
	            MessageProperties.PERSISTENT_TEXT_PLAIN,
	            message.getBytes());
		  System.out.println(" [x] Sent '" + message + "' ok...");
	 }
	
	public void close(){
		  try {
			this.channel.close();
			this.connection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
