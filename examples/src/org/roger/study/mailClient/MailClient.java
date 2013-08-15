package org.roger.study.mailClient;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.http.*;
import org.jboss.netty.util.CharsetUtil;

import javax.xml.ws.spi.http.HttpHandler;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.text.MessageFormat;
import java.util.concurrent.Executors;

import static org.roger.study.mailClient.Commons.BuildMsg;

/**
 * Created with IntelliJ IDEA.
 * User: roger
 * Date: 13-8-8
 * Time: 下午5:29
 * To change this template use File | Settings | File Templates.
 */
public class MailClient {
    private final String host = "192.168.20.249";
    private final int port = 80;
    private String username;
    private String password;

    MailClient()  {}

    public void setUsername(String name)  {
        username = name;
    }

    public void setPassword(String password)  {
        this.password = password;
    }

    public void run() throws IOException {
        PipelineFactory pipelineFactory = new PipelineFactory();

        for(int i =0;i < 10;i ++)  {
            sendMail(pipelineFactory);
        }
    }

    private void sendMail(PipelineFactory pipelineFactory) throws IOException {
        ClientBootstrap clientBootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(
                Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool()
        ));

        clientBootstrap.setPipelineFactory(pipelineFactory);

        ChannelFuture future = clientBootstrap.connect(new InetSocketAddress(host, port));

        Channel channel = future.awaitUninterruptibly().getChannel();

        if (!future.isSuccess())  {
            future.getCause().printStackTrace();
            clientBootstrap.releaseExternalResources();
            return;
        }

        //Prepare the HTTP request
        HttpRequest httpRequest = new DefaultHttpRequest(
                HttpVersion.HTTP_1_1, HttpMethod.POST, "/Microsoft-Server-ActiveSync?" + Commons.GeneralURI("SendMail")
        );
        httpRequest.setHeader(HttpHeaders.Names.HOST, host);
        httpRequest.setHeader(HttpHeaders.Names.USER_AGENT, "Roget-test");
        httpRequest.setHeader(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        if (username.isEmpty() || password.isEmpty())  {
            System.out.println("Username or Password is empty.");
            return;
        }
        httpRequest.setHeader(HttpHeaders.Names.AUTHORIZATION,
                MessageFormat.format("Basic {0}", Commons.Base64Encode((username + ":" + password).getBytes())));
        System.out.println("cm9nZXI6UHAxMjM0NTY=");
        httpRequest.setHeader(HttpHeaders.Names.CONTENT_TYPE, "application/vnd.ms-sync.wbxml");

        String msg = BuildMsg();
        httpRequest.setHeader(HttpHeaders.Names.CONTENT_LENGTH, msg.getBytes().length);

        httpRequest.setContent(ChannelBuffers.copiedBuffer(msg, CharsetUtil.UTF_8));

        channel.write(httpRequest);
    }

    public static void main(String[] args) throws IOException {
        MailClient client = new MailClient();
        client.setUsername("roger");
        client.setPassword("Pp123456");
        client.run();
    }
}
