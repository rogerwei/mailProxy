package org.roger.study.ExClient.Protocol;

import org.roger.study.ExClient.Util.SwitchData;

import java.io.UnsupportedEncodingException;

import static java.net.URLEncoder.encode;
import static org.roger.study.ExClient.configuration.RunTime.*;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-22
 * Time: 上午11:26
 * To change this template use File | Settings | File Templates.
 */
public class BuildMessage {
    private BuildRequest.Type type;
    private String user;
    private static String policyType = "MS-EAS-Provisioning-WBXML";
    public BuildMessage(BuildRequest.Type type, String user) {
        this.type = type;
        this.user = user;
    }


    public String build() {
        if (type.equals(BuildRequest.Type.Options)) {
            return "";
        }

        String message = buildWBXMLHeader();
        message += buildWBXMLBody();

        return message;
    }

    private String buildWBXMLBody() {
        switch (type) {
            case Provision:
                return buildProvisionBody();
            case SendMail:
                return buildSendMailBody();
            default:
                System.err.println("目前不支持WBXML类型.");
                return "";
        }
    }

    private String buildSendMailBody() {
        String wbxml ="";
        //Switch Page
        wbxml += (char) 0x0;
        wbxml += (char)0x15;

        //SendMail
        wbxml += (char) 0x45;

        //ClientId
        wbxml += (char) 0x51;
        wbxml += (char) 0x3;
        try {
            wbxml += encode(getClientId(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        wbxml += (char) 0x0;

        //CollectionId end
        wbxml += (char) 0x1;

        //SaveInSentItems
        wbxml += (char) 0x08;

        //MIME
        wbxml += (char) 0x50;

        //MIME Content
        byte[] bytes = SwitchData.HexStringToBytes("c3850e546f3a20726f67657230314073657261642e636f6d0d0a46726f6d3a206d61696c30314073657261642e636f6d0d0a446174653a205468752c2032322041756720323031332031373a30373a3537202b303830300d0a582d4d61696c65723a20546f756368446f776e0d0a4d494d452d56657273696f6e3a20312e300d0a5375626a6563743a203d3f7574662d383f423f4d54497a3f3d0d0a436f6e74656e742d547970653a206d756c7469706172742f6d697865643b626f756e646172793d225f5f31333737313632343737353830544f554348444f574e5f424f554e444152595f5f220d0a0d0a0d0a2d2d5f5f31333737313632343737353830544f554348444f574e5f424f554e444152595f5f0d0a436f6e74656e742d547970653a20746578742f68746d6c3b20636861727365743d227574662d38220d0a436f6e74656e742d5472616e736665722d456e636f64696e673a2071756f7465642d7072696e7461626c650d0a0d0a3c68746d6c3e3c626f6479207374796c653d334427666f6e742d66616d696c793a43616c696272692c20417269616c2c2048656c7665746963612c2073616e732d73657269663b666f6e742d3d0d0a73697a653a313170743b636f6c6f723a626c61636b27203e6162633c62723e3c62723e262332323331323b262332353130353b262333303334303b20416e64726f696420262332353136333b3d0d0a262332363432363b262331393937383b262332393939323b20546f756368446f776e20262332313435373b262333363836353b20287777772e6e6974726f6465736b2e636f6d293c2f626f643d0d0a793e3c2f68746d6c3e0d0a0d0a2d2d5f5f31333737313632343737353830544f554348444f574e5f424f554e444152595f5f2d2d0d0a");
        String tmp = null;
        try {
            tmp = new String(bytes,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        System.out.println(tmp.getBytes().length);

        wbxml += tmp;

        //MIME end
        wbxml += (char) 0x1;

        //SendMail end
        wbxml += (char) 0x1;
        return wbxml;  //To change body of created methods use File | Settings | File Templates.
    }

    private String buildProvisionBody() {
        String wbxml = "";
        //Switch Page
        wbxml += (char) 0x0;
        wbxml += (char) 0xe;

        //Provision
        wbxml += (char) 0x45;

        //Switch Page
        wbxml += (char) 0x0;
        wbxml += (char) 0x12;

        //DeviceInformation
        wbxml += (char) 0x56;

        //Set
        wbxml += (char) 0x48;

        //Model
        wbxml += (char) 0x57;
        wbxml += (char) 0x3;
        wbxml += getModel();
        wbxml += (char) 0x0;
        wbxml += (char) 0x1;

        //OS
        wbxml += (char) 0x5a;
        wbxml += (char) 0x3;
        wbxml += getOS();
        wbxml += (char) 0x0;
        wbxml += (char) 0x1;

        //OSLanguage
        wbxml += (char) 0x5b;
        wbxml += (char) 0x3;
        wbxml += getLanguage();
        wbxml += (char) 0x0;
        wbxml += (char) 0x1;

        //Friendly Name
        wbxml += (char) 0x59;
        wbxml += (char) 0x3;
        wbxml += getFriendlyName(user);
        wbxml += (char) 0x0;
        wbxml += (char) 0x1;

        //IMEI
        wbxml += (char) 0x58;
        wbxml += (char) 0x3;
        wbxml += getDeviceId(user);
        wbxml += (char) 0x0;
        wbxml += (char) 0x1;

        //PhoneNumber
        wbxml += (char) 0x5c;
        wbxml += getPhoneNumber(user);
        wbxml += (char) 0x1;

        //set end
        wbxml += (char) 0x1;

        //device information end
        wbxml += (char) 0x1;

        //Switch Page
        wbxml += (char) 0x0;
        wbxml += (char) 0xe;

        //Policies
        wbxml += (char) 0x46;
        //Policy
        wbxml += (char) 0x47;
        //Policy Type
        wbxml += (char) 0x48;
        wbxml += (char) 0x3;
        wbxml += policyType;
        wbxml += (char) 0x0;
        wbxml += (char) 0x1;
        //Policy end
        wbxml += (char) 0x1;
        //Policies end
        wbxml += (char) 0x1;
        //Provision end
        wbxml += (char) 0x1;
        return wbxml;  //To change body of created methods use File | Settings | File Templates.
    }

    private static final int WBXMLVersion = 0x03;
    private String buildWBXMLHeader() {
        String headler = (char)(WBXMLVersion & 0xff) + "";

        //Public Identifier
        headler += (char) 0x1;

        //Character set UTF8
        headler += (char) 0x6a;

        //String table
        headler += (char) 0x0;
        return headler;  //To change body of created methods use File | Settings | File Templates.
    }
}
