package org.roger.study.ExClient.transport;

import org.jboss.netty.channel.*;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.util.CharsetUtil;
import org.roger.study.ExClient.Protocol.ProxyRequest;
import org.roger.study.ExClient.configuration.Configs;
import org.roger.study.ExClient.controller.HandleResponse;

import static org.roger.study.ExClient.controller.HandleChannel.BuildPeer;

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
        BuildPeer(channel);
        System.out.println(channel.getId());
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        if (e.getMessage() instanceof HttpResponse)  {
            HttpResponse response = (HttpResponse) e.getMessage();
            Channel channel = e.getChannel();
            HandleResponse.handle(response, channel);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        super.exceptionCaught(ctx, e);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
