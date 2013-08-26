package org.roger.study.ExClient.controller;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.roger.study.ExClient.Protocol.ParaResponse;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-21
 * Time: 下午5:55
 * 获取消息，并调用上层解析接口，在必要时，回写请求
 */
public class HandleResponse {
    public static void handle(HttpResponse response, Channel channel) {
        //解析
        ParaResponse para = new ParaResponse(response, channel);
        //下一步
        switch (para.next()) {
            case Provision:
                SendRequest.Provision(channel);
                break;
            case AckProvision:
                SendRequest.AckProvision(channel);
                break;
            case SendMail:
                SendRequest.SendMail(channel);
                break;
            default:
                ;
        }

    }
}
