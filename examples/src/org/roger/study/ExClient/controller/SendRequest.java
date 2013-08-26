package org.roger.study.ExClient.controller;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.roger.study.ExClient.Protocol.BuildRequest;

import static org.roger.study.ExClient.controller.HandleChannel.activeChannels;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-22
 * Time: 上午11:15
 * To change this template use File | Settings | File Templates.
 */
public class SendRequest {
    public static void Options(Channel channel) {
        buildAndSend(channel, BuildRequest.Type.Options);
    }

    public static void Provision(Channel channel) {
        buildAndSend(channel, BuildRequest.Type.Provision);
    }

    public static void SendMail(Channel channel) {
        while ( true) {
            if (TestCounter.testOne(channel.getId()))
                buildAndSend(channel, BuildRequest.Type.SendMail);
            else
                break;
        }
    }

    public static void SendMail()  {
        for (Channel ch: activeChannels())  {
            SendMail(ch);
        }
    }

    private static void buildAndSend(Channel channel, BuildRequest.Type type) {
        HttpRequest request = new BuildRequest(channel, type).build();
        if (request != null)
            channel.write(request);
    }

    public static void AckProvision(Channel channel) {
        buildAndSend(channel, BuildRequest.Type.AckProvision);
    }
}
