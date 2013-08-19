package org.roger.study.mailClient;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.codec.http.*;
import org.jboss.netty.util.CharsetUtil;

import java.text.MessageFormat;

import static org.roger.study.mailClient.Commons.BuildMsg;
import static org.roger.study.mailClient.Commons.setCount;

/**
 * Created with IntelliJ IDEA.
 * User: roger
 * Date: 13-8-8
 * Time: 下午6:14
 * To change this template use File | Settings | File Templates.
 */
public class ClientHandler extends SimpleChannelUpstreamHandler {

    private String username="roger";
    private String password="Pp123456";
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        super.messageReceived(ctx, e);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        Channel channel = e.getChannel();

        //Prepare the HTTP request
        HttpRequest httpRequest = new DefaultHttpRequest(
                HttpVersion.HTTP_1_1, HttpMethod.POST, "/Microsoft-Server-ActiveSync?" + Commons.GeneralURI("SendMail")
        );
        httpRequest.setHeader(HttpHeaders.Names.HOST, Commons.getHost());
        httpRequest.setHeader(HttpHeaders.Names.USER_AGENT, "Roget-test");
        httpRequest.setHeader(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        if (username.isEmpty() || password.isEmpty())  {
            System.out.println("Username or Password is empty.");
            return;
        }
        httpRequest.setHeader(HttpHeaders.Names.AUTHORIZATION,
                MessageFormat.format("Basic {0}", Commons.Base64Encode((username + ":" + password).getBytes())));
        httpRequest.setHeader(HttpHeaders.Names.CONTENT_TYPE, "application/vnd.ms-sync.wbxml");

        String msg = BuildMsg();
        httpRequest.setHeader(HttpHeaders.Names.CONTENT_LENGTH, msg.getBytes().length);

        httpRequest.setContent(ChannelBuffers.copiedBuffer(msg, CharsetUtil.UTF_8));

        channel.write(httpRequest);
        setCount();
    }
}
