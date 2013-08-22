package org.roger.study.ExClient.controller;

import org.roger.study.ExClient.transport.ClientProxy;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-21
 * Time: 下午5:39
 * 初始化连接数据接口
 * 启动、停止、重启
 */
public class Booter {
    private int users = 0;
    private ClientProxy proxy;

    public Booter(int users)  {
        this.users = users;
        proxy = new ClientProxy(users);
        start();
    }

    public void start() {
        //start bootstrap
        proxy.start();
    }

    public void stop()  {
        //Stop bootstrap
        proxy.stop();
    }

    public void restart() {
        stop();
        start();
    }
}
