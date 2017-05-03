package com.pku.jstorm.bolts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class TransactionCheck implements IRichBolt {
	/**
	 * 
	 */
	//private static Logger logger = LoggerFactory.getLogger(TransactionCheck.class);
	private static final long serialVersionUID = 1L;
	private OutputCollector collector;

	public void cleanup() {
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void execute(Tuple input) {
		String sentence = input.getString(0);
		System.out.println("==================storm Check received transaction:" + sentence);
		List<Tuple> a = new ArrayList();
		a.add(input);
		this.collector.emit(a,new Values(sentence));
//		String[] words = sentence.split(" ");
//		for (String word : words) {
//			word = word.trim();
//			if (!word.isEmpty()) {
//				word = word.toLowerCase();
//	
//				List<Tuple> a = new ArrayList();
//				a.add(input);
//				collector.emit(a, new Values(word));
//			}
//		}
		collector.ack(input);
	}

	@SuppressWarnings("rawtypes")
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {

		return null;
	}

}
