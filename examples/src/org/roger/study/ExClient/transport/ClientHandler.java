package org.roger.study.ExClient.transport;

import org.jboss.netty.channel.*;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.util.CharsetUtil;
import org.roger.study.ExClient.Protocol.ProxyRequest;
import org.roger.study.ExClient.configuration.Configs;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-21
 * Time: 上午11:35
 * To change this template use File | Settings | File Templates.
 */
public class ClientHandler extends SimpleChannelUpstreamHandler {
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("connected-->[" + Configs.getHost() + ":" + Configs.getPort() +"]");

        Channel channel = e.getChannel();
        HttpRequest optionReq = ProxyRequest.getOptionRequest();
        channel.write(optionReq);
        System.out.println(channel.getId());
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        System.out.println("Handler receivce!");
        /*if (e.getMessage() instanceof HttpResponse)  {
            HttpResponse response = (HttpResponse)  e.getMessage();
            String message = response.getContent().toString(CharsetUtil.UTF_8);
            System.out.println("MS-Server-ActiveSync:" + response.getHeader("MS-Server-ActiveSync"));
            System.out.println("MS-ASProtocolVersions:" + response.getHeader("MS-ASProtocolVersions"));
            System.out.println("MS-ASProtocolCommands:" + response.getHeader("MS-ASProtocolCommands"));
            System.out.println("Allow:" + response.getHeader("Allow"));
            System.out.println(HttpHeaders.Names.CONTENT_LENGTH + ":" + response.getHeader(HttpHeaders.Names.CONTENT_LENGTH));

            System.out.println(message);
        }*/

        Channel channel = e.getChannel();
        System.out.println(channel.getId());
        channel.write(ProxyRequest.getProvisionRequest());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        super.exceptionCaught(ctx, e);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
