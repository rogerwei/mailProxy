package org.roger.study.ExClient;

import org.roger.study.ExClient.transport.ClientProxy;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-20
 * Time: 下午6:10
 * To change this template use File | Settings | File Templates.
 */
public class ExClient {

    public static void main(String[] args)  {
        //init config

        //start console
        ClientProxy proxy = new ClientProxy();
        proxy.start();
    }
}