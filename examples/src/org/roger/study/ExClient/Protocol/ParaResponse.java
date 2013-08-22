package org.roger.study.ExClient.Protocol;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import static org.roger.study.ExClient.Protocol.WBXmlCommands.getValue;
import static org.roger.study.ExClient.configuration.Configs.getRunTimes;
import static org.roger.study.ExClient.configuration.RunTime.setPolicyKey;
import static org.roger.study.ExClient.controller.HandleChannel.getUser;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-22
 * Time: 下午1:00
 * To change this template use File | Settings | File Templates.
 */
public class ParaResponse {
    private static final String serverProtocolVersionHeader = "MS-Server-ActiveSync";
    HttpResponse response;
    Channel channel;


    public ParaResponse(HttpResponse response, Channel channel) {
        this.response = response;
        this.channel = channel;
    }

    public BuildRequest.Type next()  {
        if (!response.getStatus().equals(HttpResponseStatus.OK))
            return BuildRequest.Type.None;

        if (response.containsHeader(serverProtocolVersionHeader)
                && response.getHeader(HttpHeaders.Names.CONTENT_LENGTH).equals("0"))  //It's Options Response
            return BuildRequest.Type.Provision;

        int length = Integer.parseInt(response.getHeader(HttpHeaders.Names.CONTENT_LENGTH));
        if (length > 0) {
            //page and first commands
            ByteBuffer byteBuffer = response.getContent().toByteBuffer();
            return getDataAndNext(byteBuffer);
        }
        return BuildRequest.Type.None;
    }

    private BuildRequest.Type getDataAndNext(ByteBuffer byteBuffer) {
        byte[] bytes = byteBuffer.array();

        byte page = bytes[5];
        if (page == 0xe)  {//Provision
            paraPolicyKey(bytes);

            if (getRunTimes() > 0)
                return BuildRequest.Type.SendMail;
        }
        return BuildRequest.Type.None;  //To change body of created methods use File | Settings | File Templates.
    }

    private void paraPolicyKey(byte[] bytes) {
        String user = getUser(channel);
        if (user.isEmpty())
            return;
        String key = getValue(bytes, 0x49);
        if (!key.isEmpty())
            setPolicyKey(user, Long.valueOf((key)));
    }
}