package org.roger.study.mailClient;

import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;

import static java.net.URLEncoder.encode;

/**
 * Created with IntelliJ IDEA.
 * User: roger
 * Date: 13-8-14
 * Time: 上午9:38
 * To change this template use File | Settings | File Templates.
 */
public class Commons {
    private static final float version = (float) 14.1;
    private static final String DeviceId = "351554059027376";
    private static final String DeviceType = "TouchDown";
    private static final int policyKey = 1919813980;
    private static long clientId = 51456017;

    private static final HashMap<String, Integer> commands = new HashMap<String, Integer>();
    static {
        commands.put("Sync", 0);
        commands.put("SendMail", 1);
        commands.put("SmartForward", 2);
        commands.put("SmartReply", 3);
        commands.put("GetAttachment", 4);
        commands.put("FolderSync", 9);
        commands.put("FolderCreate", 10);
        commands.put("FolderDelete", 11);
        commands.put("FolderUpdate", 12);
        commands.put("MoveItems", 13);
        commands.put("GetItemEstimate", 14);
        commands.put("MeetingResponse", 15);
        commands.put("Search", 16);
        commands.put("Settings", 17);
        commands.put("Ping", 18);
        commands.put("ItemOperations", 19);
        commands.put("Provision", 20);
        commands.put("ResolveRecipients", 21);
        commands.put("ValidateCert", 22);
    }
    public static String Base64Encode(byte[] src) throws UnsupportedEncodingException {
        BASE64Encoder encoder = new BASE64Encoder();

        String res = encoder.encodeBuffer(src);

        res = res.substring(0, res.length()-2);
        System.out.println(res);
        return res;
    }

    public static String GeneralURI(String command) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.reset();

        //input Protocol
        outputStream.write(((int)(version*10) & 0xff));

        //input Command code
        outputStream.write(commands.get(command) & 0xff);

        //input Locale
        outputStream.write(0x9);
        outputStream.write(0x4);

        //input device Id length
        outputStream.write(DeviceId.length() & 0xff);

        //input device Id
        outputStream.write(DeviceId.getBytes());

        //input Policy Key length
        outputStream.write(0x4);
        outputStream.write(getPolicyKey().getBytes());


        //input Device type length
        outputStream.write(DeviceType.length() & 0xff);

        //input device Type
        outputStream.write(DeviceType.getBytes());

        //Parameters
        //Options Used by SmartReply, SmartForward, SendMail, ItemOperations
        outputStream.write(0x7);     //Tags
        outputStream.write(0x1);    //len
        outputStream.write(0x1);    //value  0x1:SaveInSent  0x2:AcceptMultiPart

        return Base64Encode(outputStream.toByteArray());
    }

    private static String getPolicyKey() {
        String res = "";

        res += (char)((policyKey >> 24) & 0xff);
        res += (char)((policyKey >> 16) & 0xff);
        res += (char)((policyKey >> 8) & 0xff);
        res += (char)(policyKey  & 0xff);

        return res;
    }

    private static final int WBXMLVersion = 0x03;

    public static String BuildMsg() throws UnsupportedEncodingException {
        String res ="";

        //WAP Binary XML Version
        res += (char) (WBXMLVersion & 0xff);

        //Public Identifier
        res += (char) 0x1;

        //Character set UTF8
        res += (char) 0x6a;

        //String table
        res += (char) 0x0;

        //Switch Page
        res += (char) 0x0;
        res += (char)0x15;

        //SendMail
        res += (char) 0x45;

        //CollectionId
        res += (char) 0x51;
        //Id
        res += (char) 0x3;

        System.out.println("len:" + res.length());
        res += encode(getClientId(), "utf-8");
        System.out.println("len:" + res.length());
        res += (char) 0x0;

        //CollectionId end
        res += (char) 0x1;

        //SaveInSentItems
        res += (char) 0x08;

        //MIME
        res += (char) 0x50;

        System.out.println("len:" + res.length());
        //MIME Content
        byte[] bytes = hexStringToBytes("c38509546f3a207765694073657261642e636f6d0d0a46726f6d3a20726f6765724073657261642e636f6d0d0a446174653a205765642c2031342041756720323031332031313a33373a3335202b303830300d0a582d4d61696c65723a20546f756368446f776e0d0a4d494d452d56657273696f6e3a20312e300d0a5375626a6563743a203d3f7574662d383f423f4d54497a3f3d0d0a436f6e74656e742d547970653a206d756c7469706172742f6d697865643b626f756e646172793d225f5f31333736343531343535393830544f554348444f574e5f424f554e444152595f5f220d0a0d0a0d0a2d2d5f5f31333736343531343535393830544f554348444f574e5f424f554e444152595f5f0d0a436f6e74656e742d547970653a20746578742f68746d6c3b20636861727365743d227574662d38220d0a436f6e74656e742d5472616e736665722d456e636f64696e673a2071756f7465642d7072696e7461626c650d0a0d0a3c68746d6c3e3c626f6479207374796c653d334427666f6e742d66616d696c793a43616c696272692c20417269616c2c2048656c7665746963612c2073616e732d73657269663b666f6e742d3d0d0a73697a653a313170743b636f6c6f723a626c61636b27203e3132333c62723e3c62723e262332323331323b262332353130353b262333303334303b20416e64726f696420262332353136333b3d0d0a262332363432363b262331393937383b262332393939323b20546f756368446f776e20262332313435373b262333363836353b20287777772e6e6974726f6465736b2e636f6d293c2f626f643d0d0a793e3c2f68746d6c3e0d0a0d0a2d2d5f5f31333736343531343535393830544f554348444f574e5f424f554e444152595f5f2d2d0d0a");
        res += new String(bytes,"UTF-8");
        System.out.println("len:" + res.length());

        System.out.println("len:" + bytes.length);
        //MIME end
        res += (char) 0x1;

        //SendMail end
        res += (char) 0x1;
        return res;
    }

    private static synchronized String getClientId() {
        return String.valueOf(clientId++);
    }

    private static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase(Locale.getDefault());
        int length = hexString.length() / 2;
        //将十六进制字符串转换成字符数组
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        int i = 0;
        for (; i < length; i++) {
            //一次去两个字符
            int pos = i * 2;
            //两个字符一个对应byte的高四位一个对应第四位
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }

        System.out.println(i);
        return d;
    }

    /**
     * 将传进来的字符代表的数字转换成二进制数
     * @param c 要转换的字符
     * @return 以byte的数据类型返回字符代表的数字的二进制表示形式
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
