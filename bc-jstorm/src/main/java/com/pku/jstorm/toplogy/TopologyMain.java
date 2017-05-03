package com.pku.jstorm.toplogy;

import java.io.IOException;
import java.util.Map;

import org.pku.hbase.HBaseUtil;

import com.alibaba.jstorm.utils.LoadConf;
import com.pku.jstorm.bolts.*;
import com.pku.jstorm.spouts.*;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.TopologyAssignException;
import backtype.storm.topology.TopologyBuilder;

public class TopologyMain {

	//private static Logger log = LoggerFactory.getLogger(TopologyMain.class);
	private static Map conf;
	private static String topologyName = "Batch";
	
	 public static TopologyBuilder SetBuilder() {
		 	TopologyBuilder builder = new TopologyBuilder();
			builder.setSpout("transaction-reader", new TransactionReader(),1);
			builder.setBolt("transaction-check", new TransactionCheck(),1)
					.shuffleGrouping("transaction-reader");
			builder.setBolt("transaction-save", new TransactionSave(),1)
					.shuffleGrouping("transaction-check");

	        return builder;
	   }

    public static void SetLocalTopology() throws Exception {
    	//创建本地模拟的topology
        TopologyBuilder builder = SetBuilder();
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("Getting-Started-Topologie", conf,
				builder.createTopology());
        System.out.println("本地模式启动... ...");
		try {
			Thread.sleep(500*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		cluster.shutdown();
    }

    public static void SetRemoteTopology() throws AlreadyAliveException,
            InvalidTopologyException, TopologyAssignException {
        TopologyBuilder builder = SetBuilder();
        //服务器直接提交
    	try {
			StormSubmitter.submitTopology("Getting-Started-Topologie", conf, builder.createTopology());
		} catch (AlreadyAliveException e) {
			
			e.printStackTrace();
		} catch (InvalidTopologyException e) { 
			
			e.printStackTrace();
		}
    	System.out.println("集群模式启动... ...");
    }

	public static void main(String[] args) throws Exception{

		String filename = "word.txt";
		String runMode = "local";
		//String runMode = "remote";
		conf = LoadConf.LoadYaml("conf/simple.yaml");
		conf.put("wordsFile", filename);
		if(runMode.equals("local")){
			TopologyMain.SetLocalTopology();
		} else {
			TopologyMain.SetRemoteTopology();
		}
		
	}
}