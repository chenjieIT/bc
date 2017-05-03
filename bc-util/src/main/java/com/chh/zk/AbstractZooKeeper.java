package com.chh.zk;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AbstractZooKeeper  implements Watcher  {
	 private static final Logger LOG = LoggerFactory.getLogger(AbstractZooKeeper.class);
  
     //缓存时间
     private static final int SESSION_TIME = 2000;
     protected ZooKeeper zooKeeper;
     protected CountDownLatch countDownLatch=new CountDownLatch(1);
     
     //需要连接zookeeper
     public void connect(String hosts) throws IOException, InterruptedException{     
         zooKeeper = new ZooKeeper(hosts,SESSION_TIME,this); 
         countDownLatch.await();
     }
    
	@Override
	public void process(WatchedEvent event) {
		LOG.info("=================="+event.getState());
        if(event.getState()==KeeperState.SyncConnected){
            countDownLatch.countDown();  
        }  
	}
	
	public void close() throws InterruptedException{     
        zooKeeper.close();     
    }
}
