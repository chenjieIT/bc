block chain redis rabbitmq jstorm hbase use demo
#1.确定启动redis、jstrom、rabbitmq、Hbase、Hadoop
###（1）启动redis
服务端（注意修改redis.conf daemonize yes）
/usr/redis/redis-server /usr/redis/redis.conf
启动客户端
/usr/redis/redis-cli
查看set集合
smembers message
删除
DEL message

###（2）启动zookeeper集群
/usr/opt/zookeeper_startup.sh

### （3）Hbase、Hadoop
进入hadoop用户，启动hadoop
start-all.sh
启动hbase
/usr/opt/hbase/bin/start-hbase.sh
```
hbase shell
扫描表
scan 'transaction',{LIMIT=>5}
清空表
truncate 'transaction'
```
### jstorm 测试不用
#2.启动rabbitmq 消费者任务 
自启动的
#3.启动jstorm任务