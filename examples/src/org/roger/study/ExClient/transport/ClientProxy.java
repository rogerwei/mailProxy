package org.roger.study.ExClient.transport;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.roger.study.ExClient.configuration.Configs;

import java.net.InetSocketAddress;
import java.util.*;
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
    private NioClientSocketChannelFactory factory;
    private List<ClientBootstrap> clients= new ArrayList<ClientBootstrap>();
    private boolean running = false;

    public ClientProxy(int m)  {
        Executor executor = Executors.newCachedThreadPool();

        factory = new NioClientSocketChannelFactory(executor, executor);

        int n = m;
        while(n > 0)  {
            ClientBootstrap bootstrap = new ClientBootstrap(factory);
            bootstrap.setPipelineFactory(new PipelineFactory());
            clients.add(bootstrap);
            n--;
        }

        if (m <= 0)  {
            System.out.println("No Client Bootstrap built!");
        }
    }

    public void start()  {
        if (clients.isEmpty() || running)  {
            System.out.println(running? "All Client is Running." : "No Client Bootstrap built!");
            return;
        }

        for(ClientBootstrap bootstrap : clients){
            bootstrap.connect(new InetSocketAddress(Configs.getHost(), Configs.getPort()));
        }
    }

    public void stop() {
        if (clients.isEmpty() || !running)  {
            System.out.println(running? "All Client is Stopped." : "No Client Bootstrap built!");
            return;
        }

        ClientBootstrap bootstrap;
        Iterator iterator = clients.iterator();

        while (iterator.hasNext()) {
            bootstrap = (ClientBootstrap) iterator;
            bootstrap.releaseExternalResources();
        }

        factory.releaseExternalResources();
    }
}
