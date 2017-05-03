package com.chh.zk;

import java.util.Arrays;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZooKeeperOperator  extends AbstractZooKeeper  {
	private static final Logger LOG = LoggerFactory.getLogger(ZooKeeperOperator.class);
	/** 
     *  
     *<b>function:</b>创建持久态的znode,比支持多层创建.比如在创建/parent/child的情况下,无/parent.无法通过 
     *@createDate 2013-01-16 15:08:38 
     *@param path 对应的路径
     *@param data 绑定的数据
     *@throws KeeperException 
     *@throws InterruptedException 
     */  
    public void create(String path,byte[] data)throws KeeperException, InterruptedException{  
        /** 
         * 此处采用的是CreateMode是PERSISTENT  表示The znode will not be automatically deleted upon client's disconnect. 
         * EPHEMERAL 表示The znode will be deleted upon the client's disconnect. 
         */   
        this.zooKeeper.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);  
    } 
    /** 
     *  
     *<b>function:</b>获取节点信息 
     *@param path 路径
     *@throws KeeperException 
     *@throws InterruptedException 
     */  
    public void getChild(String path) throws KeeperException, InterruptedException{     
        try{  
            List<String> list=this.zooKeeper.getChildren(path, false);  
            if(list.isEmpty()){  
            	LOG.info(path+"中没有节点");  
            }else{  
            	LOG.info(path+"中存在节点");  
                for(String child:list){  
                	LOG.info("节点为："+child);  
                }  
            }  
        }catch (KeeperException.NoNodeException e) {  
             throw e;
        }  
    }  
    public byte[] getData(String path) throws KeeperException, InterruptedException {     
        return  this.zooKeeper.getData(path, false,null);     
    }
    public static void main(String[] args) {
        try {     
               ZooKeeperOperator zkoperator = new ZooKeeperOperator();     
               zkoperator.connect("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183");  
                 
               byte[] data = new byte[]{'a','b','c','d'};     
                    
             zkoperator.create("/root",data);     
             System.out.println(Arrays.toString(zkoperator.getData("/root")));     
                  
//             zkoperator.create("/root/child1",data);     
//             System.out.println(Arrays.toString(zkoperator.getData("/root/child1")));     
//                  
//             zkoperator.create("/root/child2",data);     
//             System.out.println(Arrays.toString(zkoperator.getData("/root/child2")));     
//                    
//               String zktest="ZooKeeper的Java API测试";  
//               zkoperator.create("/root/child4", zktest.getBytes());  
//               LOG.debug("获取设置的信息："+new String(zkoperator.getData("/root/child3")));  
//             //修改节点/root/childone下的数据，第三个参数为版本，如果是-1，那会无视被修改的数据版本，直接改掉
//               zkoperator.setData("/root/child2","范德萨方法发生的发生的".getBytes(), -1);
//               System.out.println("节点孩子信息:");
//               zkoperator.getChild("/root");     
                
               zkoperator.close();     
                    
           } catch (Exception e) {     
               e.printStackTrace();     
           }     
 
   }
    
	private void setData(String path, byte[] bytes, int i) throws KeeperException, InterruptedException {	
		this.zooKeeper.setData(path,bytes, -1);
	}
	
	private void deleteNode(String path, int i) throws InterruptedException, KeeperException {
			this.zooKeeper.delete(path, -1);
	}
	
}
