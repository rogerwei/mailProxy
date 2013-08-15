package org.roger.study.mailClient;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

/**
 * Created with IntelliJ IDEA.
 * User: roger
 * Date: 13-8-8
 * Time: 下午6:14
 * To change this template use File | Settings | File Templates.
 */
public class ClientHandler extends SimpleChannelUpstreamHandler {
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        super.messageReceived(ctx, e);    //To change body of overridden methods use File | Settings | File Templates.
        System.out.println("get response");
    }
}
