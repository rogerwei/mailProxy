package org.roger.study.ExClient.transport;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.roger.study.ExClient.configuration.Configs;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-20
 * Time: 下午6:13
 * To change this template use File | Settings | File Templates.
 */
public class ClientProxy {
    NioClientSocketChannelFactory factory;

    public ClientProxy()  {
        Executor executor = Executors.newCachedThreadPool();

        factory = new NioClientSocketChannelFactory(executor, executor);
    }

    public void start()  {
        ClientBootstrap bootstrap = new ClientBootstrap(factory);
        bootstrap.setPipelineFactory(new PipelineFactory());
        System.out.println(Configs.getHost());
        System.out.println(Configs.getPort());
        bootstrap.connect(new InetSocketAddress(Configs.getHost(), Configs.getPort()));
    }
}
