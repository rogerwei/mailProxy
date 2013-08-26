package org.roger.study.ExClient.Protocol;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.handler.codec.http.*;
import org.jboss.netty.util.CharsetUtil;
import org.roger.study.ExClient.Util.Base64;
import org.roger.study.ExClient.configuration.Configs;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;

import static org.roger.study.ExClient.controller.HandleChannel.getAuth;
import static org.roger.study.ExClient.controller.HandleChannel.getUser;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-22
 * Time: 上午11:18
 * To change this template use File | Settings | File Templates.
 */
public class BuildRequest {
    public enum Type {None, Options, Provision, AckProvision, SendMail};

    private Type type;
    private Channel channel;
    public BuildRequest(Channel channel, Type type) {
        this.channel = channel;
        this.type = type;
    }

    public HttpRequest build() {
        if (type == Type.None)
            return null;

        String message = new BuildMessage(type, getUser(channel)).build();

        int length = 0;
        try {
            length = message.getBytes("UTF-8").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1,
                type.equals(Type.Options)? HttpMethod.OPTIONS: HttpMethod.POST,
                "/Microsoft-Server-ActiveSync"+  BuildURI.getURI(type, getUser(channel)));

        if (request != null)  {

            request.setHeader(HttpHeaders.Names.HOST, Configs.getHost());
            request.setHeader(HttpHeaders.Names.USER_AGENT, "ExClient(0.1)");
            request.setHeader(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            request.setHeader(HttpHeaders.Names.AUTHORIZATION, MessageFormat.format("Basic {0}", getEncodeAuth(channel)));
            request.setHeader(HttpHeaders.Names.CONTENT_LENGTH, length);
            if (length > 0) {
                request.setHeader(HttpHeaders.Names.CONTENT_TYPE, "application/vnd.ms-sync.wbxml");
                request.setContent(ChannelBuffers.copiedBuffer(message, CharsetUtil.UTF_8));
            }
        }

        return request;
    }

    private String getEncodeAuth(Channel channel) {
        String auth = getAuth(channel);
        if (auth.isEmpty())   {
            System.err.println("Get authorization Ocurr Error!");
            return "";
        }
        return Base64.Encode(auth.getBytes());
    }

}
