package org.roger.study.ExClient.Protocol;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.handler.codec.http.*;
import org.jboss.netty.util.CharsetUtil;
import org.roger.study.ExClient.commons.Base64;
import org.roger.study.ExClient.configuration.Configs;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-21
 * Time: 下午2:25
 * To change this template use File | Settings | File Templates.
 */
public class ProxyRequest {
    public enum RequestType{OptionRequest, ProvisionRequest};

    public static HttpRequest getOptionRequest() {
        HttpRequest request = RequestHandler(RequestType.OptionRequest);
        return request;  //To change body of created methods use File | Settings | File Templates.
    }

    public static HttpRequest getProvisionRequest() {
        HttpRequest request = RequestHandler(RequestType.ProvisionRequest);
        return request;  //To change body of created methods use File | Settings | File Templates.
    }

    private static HttpRequest RequestHandler(RequestType type) {
        String message = BuildMessage(type, "mail01");
        int len = 0;
        try {
            len = message.getBytes("UTF-8").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        HttpRequest  request = BuildHeader(type, len);

        if (len > 0) {
            request.setContent(ChannelBuffers.copiedBuffer(message, CharsetUtil.UTF_8));
        }

        return request;
    }

    private static final int WBXMLVersion = 0x03;
    private static String BuildMessage(RequestType type, String user) {
        if (type.equals(RequestType.OptionRequest))
            return "";  //To change body of created methods use File | Settings | File Templates.
        String message="";
        //WAP Binary XML Version
        message += (char) (WBXMLVersion & 0xff);

        //Public Identifier
        message += (char) 0x1;

        //Character set UTF8
        message += (char) 0x6a;

        //String table
        message += (char) 0x0;

        //Switch Page
        message += (char) 0x0;
        message += (char) 0xe;

        //Provision
        message += (char) 0x45;

        //Switch Page
        message += (char) 0x0;
        message += (char) 0x12;

        //DeviceInformation
        message += (char) 0x56;

        //Set
        message += (char) 0x48;

        //Model
        message += (char) 0x57;
        message += (char) 0x3;
        message += "Client Tester";
        message += (char) 0x0;
        message += (char) 0x1;

        //OS
        message += (char) 0x5a;
        message += (char) 0x3;
        message += "windows7";
        message += (char) 0x0;
        message += (char) 0x1;

        //OSLanguage
        message += (char) 0x5b;
        message += (char) 0x3;
        message += "中文";
        message += (char) 0x0;
        message += (char) 0x1;

        //Friendly Name
        message += (char) 0x59;
        message += (char) 0x3;
        message += "Test_mail01";
        message += (char) 0x0;
        message += (char) 0x1;

        //IMEI
        message += (char) 0x58;
        message += (char) 0x3;
        message += Configs.getDeviceId(user);
        message += (char) 0x0;
        message += (char) 0x1;

        //PhoneNumber
        message += (char) 0x5c;
        message += (char) 0x1;

        //set end
        message += (char) 0x1;

        //device information end
        message += (char) 0x1;

        //Switch Page
        message += (char) 0x0;
        message += (char) 0xe;

        //Policies
        message += (char) 0x46;
        //Policy
        message += (char) 0x47;
        //Policy Type
        message += (char) 0x48;
        message += (char) 0x3;
        message += "MS-EAS-Provisioning-WBXML";
        message += (char) 0x0;
        message += (char) 0x1;
        //Policy end
        message += (char) 0x1;
        //Policies end
        message += (char) 0x1;
        //Provision end
        message += (char) 0x1;

        return message;
    }

    private static HttpRequest BuildHeader(RequestType type, int length) {
        HttpRequest  request = null;

        if (type.equals(RequestType.OptionRequest))
            request = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.OPTIONS, "/Microsoft-Server-ActiveSync" );
        else if (type.equals(RequestType.ProvisionRequest))
            request = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, "/Microsoft-Server-ActiveSync?" +BuildURI.getURI("Provision", "mail01"));

        request.setHeader(HttpHeaders.Names.HOST, Configs.getHost());
        request.setHeader(HttpHeaders.Names.USER_AGENT, "ExClient(0.1)");
        request.setHeader(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        request.setHeader(HttpHeaders.Names.AUTHORIZATION, MessageFormat.format("Basic {0}",
                Base64.Encode("mail01:Pp12345".getBytes())));
        request.setHeader(HttpHeaders.Names.CONTENT_LENGTH, length);
        if (length > 0)
            request.setHeader(HttpHeaders.Names.CONTENT_TYPE, "application/vnd.ms-sync.wbxml");

        return request;
    }
}
