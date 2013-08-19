package org.roger.study.mailClient;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: roger
 * Date: 13-8-8
 * Time: 下午5:29
 * To change this template use File | Settings | File Templates.
 */
public class MailClient {
    MailClient()  {}

    public void run() throws IOException {
        Executor executor = Executors.newCachedThreadPool();
        NioClientSocketChannelFactory channelFactory = new NioClientSocketChannelFactory(executor,executor);

        for(int i =0;i < Integer.parseInt(configs.getKeyValue("testTimes"));i ++)  {
            sendMail(channelFactory);
        }
    }

    private void sendMail(NioClientSocketChannelFactory channelFactory) throws IOException {
        ClientBootstrap clientBootstrap = new ClientBootstrap(channelFactory);

        clientBootstrap.setPipelineFactory(new PipelineFactory());

        clientBootstrap.connect(new InetSocketAddress(Commons.getHost(), Commons.getPort()));
    }

    public static void main(String[] args) throws IOException {
        configs();
        MailClient client = new MailClient();
        client.run();

        System.out.println();
    }

    private static void configs() {

    }
}
