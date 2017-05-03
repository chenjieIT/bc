package com.pku.jstorm.spouts;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.pku.redisAPI.RedisUtil;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class TransactionReader implements IRichSpout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SpoutOutputCollector collector;
	private File file =null;

	private boolean completed = false;


	public void ack(Object msgId) {
		System.out.println("OK:" + msgId);
	}
	
	public void fail(Object msgId) {
		System.out.println("FAIL:" + msgId);
	}
	
	public void nextTuple() {
		if (completed) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

			}
			return;
		}
		while(true) {
			try {
				RedisUtil redis = RedisUtil.getRedisUtil();
				Set<String> set = redis.getMessage();
				Iterator<String> it = set.iterator();
				while(it.hasNext()){
					String line=it.next();
					System.out.println(line);
					this.collector.emit(new Values(line),line);
				}
				Thread.sleep(5*10000);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public void open(Map conf, TopologyContext context,SpoutOutputCollector collector) {
		String filepath = Object.class.getResource("/word.txt").getPath();
		this.file = new File(filepath);
		this.collector = collector;
	}

	
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("line"));
	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}
}
