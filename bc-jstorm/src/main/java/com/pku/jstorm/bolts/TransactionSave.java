package com.pku.jstorm.bolts;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.pku.hbase.HBaseUtil;


import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

public class TransactionSave implements IRichBolt {
	//private static Logger logger = LoggerFactory.getLogger(TransactionSave.class);
	private static final long serialVersionUID = -1009884991457768941L;
	Integer id;
	String name;
	Map<String, Integer> counters;
	private OutputCollector collector;

	@Override
	public void cleanup() {
		System.out.println("------" + name + "-" + id + " --");
		for (Map.Entry<String, Integer> entry : counters.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}

	@Override
	public void execute(Tuple input) {
		String str = input.getString(0);
		System.out.println("storm transaction save received:" + str);
		try {
			String r1 = UUID.randomUUID().toString();
			HBaseUtil.insterRow("transaction", r1, "value", "tran", str);
			collector.ack(input);
		} catch (IOException e) {
			System.out.println("Save message to hbase error" + e.getMessage());
		}
//		if (!counters.containsKey(str)) {
//			counters.put(str, 1);
//		} else {
//			Integer c = counters.get(str) + 1;
//			counters.put(str, c);
//		}
//		collector.ack(input);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.counters = new HashMap<String, Integer>();
		this.collector = collector;
		this.name = context.getThisComponentId();
		this.id = context.getThisTaskId();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}
}