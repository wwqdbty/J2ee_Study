package com.kulang.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import static java.lang.System.out;

/**
 * Created by wenqiang.wang on 2017/2/17.
 */
public class Test {

    private static ZooKeeper zk;

    public static ZooKeeper getZKInstance() throws IOException {
        if (zk == null) {
            synchronized (Test.class) {
                if (zk == null) {
                    // 创建zk连接并注册默认Watcher, 后续使用中可指定是否使用该默认Watcher
                    zk = new ZooKeeper("127.0.0.1:2181", 2000, new Watcher() {
                        @Override
                        public void process(WatchedEvent event) {
                            out.println("监听, evnet={" + event.toString() + "}");
                        }
                    });
                }
            }
        }

        return zk;
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        Test.getZKInstance();

        /*zk.exists("/root", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                out.println("发生变化了, evnet={" + event.toString() + "}");
            }
        });

        while (true) {

        }*/
        existsDataWatcher();
        //创建一个节点root，数据是mydata,不进行ACL权限控制，节点为永久性的(即客户端shutdown了也不会消失)
//        zk.create("/root", "mydata".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        while (true) {

        }
        //在root下面创建一个childOne znode,数据为childOne,不进行ACL权限控制，节点为永久性的
        /*zk.create("/root/childOne", "childOne".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        //取得/root节点下的子节点名称,返回List<String>, 为true表示注册默认Watcher, false则表示不注册任何Watcher
        zk.getChildren("/root", true);
        //取得/root/childOne节点下的数据,返回byte[]
        zk.getData("/root/childOne", true, null);

        //修改节点/root/childOne下的数据，第三个参数为版本，如果是-1，那会无视被修改的数据版本，直接改掉
        zk.setData("/root/childOne", "childOneModify".getBytes(), -1);

        //删除/root/childOne这个节点，第二个参数为版本，－1的话直接删除，无视版本
        zk.delete("/root/childOne", -1);

        //关闭session
        zk.close();*/
    }

    private static void existsDataWatcher() throws KeeperException, InterruptedException {
        // 监听节点的数据变化, Watcher使用后即失效, 如需监听, 需重新设置, 如下使用递归
        Stat exists = zk.exists("/root", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                out.println("发生变化了, evnet={" + event.toString() + "}");
                try {
                    Test.existsDataWatcher();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
