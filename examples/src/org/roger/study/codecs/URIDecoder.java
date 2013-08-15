package org.roger.study.codecs;

import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import static java.lang.Integer.*;
import static java.lang.Integer.parseInt;

/**
 * Created with IntelliJ IDEA.
 * User: roger
 * Date: 13-8-13
 * Time: 下午2:04
 * To change this template use File | Settings | File Templates.
 */
public class URIDecoder {
    private String string;

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

    private static final HashMap<Integer, String> cmdTags = new HashMap<Integer, String>();
    static {
        cmdTags.put(0x0, "AttachmentName");
        cmdTags.put(0x1, "CollectionId");
        cmdTags.put(0x3, "ItemId");
        cmdTags.put(0x4, "LongId");
        cmdTags.put(0x6, "Occurrence");
        cmdTags.put(0x7, "Options");
        cmdTags.put(0x8, "User");
    }
    public URIDecoder()  {
    }

    public void setString(String string)  {
        this.string = string;
    }

    public void decode() throws UnsupportedEncodingException {
        printURI(decodeB64());
    }


    private void printURI(byte[] s) throws UnsupportedEncodingException {
        if (s.length < 1)  {
            System.err.println("Decode URI is Empty.");
            return;
        }

        String value2 = null;
        int value;
        int i=0;

        //protocol
        value = s[i++] & 0xff;
        System.out.println("Protocol Version: " +(float)value/10);

        //Command code
        value = s[i++] & 0xff;
        System.out.println("Command : " + getKey(value));

        //Locale
        value = s[i++] & 0xff;
        int locale = (value << 8) | (s[i++] & 0xff);
        System.out.println("Locale: " +Integer.toHexString(locale));

        //Device Id Length
        value = s[i++]  & 0xff;
        System.out.println("Device Id Length: " + value);

        //Device Id
        value2 = new String(s, i, value, "ISO-8859-1");
        System.out.println("Device Id:" + value2);
        i +=  value;

        //Policy Key Length & Policy Key
        if (i < s.length)  {
            value = s[i++] & 0xff;
            System.out.println("Policy Key Length: " +value);
            int n = s.length;
            if (value > 0)  {
                System.out.println("Policy Key :" + bytesToString(s, i, value));
                i += value;
            }
        }

        //Device type length
        if (i < s.length)  {
            value = s[i++] & 0xff;
            System.out.println("Device type Length: " +value);

            value2 = new String(s, i, value, "ISO-8859-1");
            System.out.println("Device type :" + value2);

            i +=value;
        }

        if (i < s.length)  {
            value = s[i++]  & 0xff;
            System.out.println("Command parameter tag: " + cmdTags.get(value));

            int len = s[i++]  & 0xff;
            parseParameters(s, value, len, i);
        }
    }

    private void parseParameters(byte[] s, int tag, int len, int start) {
        if (tag == 7)  {
            if (s[start] == 0x1)  {
                System.out.println("Conifged :SaveInSent");
            }else if (s[start] == 0x2)  {
                System.out.println("Conifged :AcceptMultiPart");
            }
        }
    }

    private String getKey(Integer value){
        for(String s : commands.keySet()){
            if(commands.get(s).equals(value)) return s;
        }
        return null;
    }

    private String bytesToString(byte[] bytes, int start, int len)  {
        int value =0;

        for (int i =0; i< 4;i ++)  {
            int tmp = bytes[start + i];     //input to int
            tmp = tmp & 0xff;       //set valid value
            value += (tmp << (8 * i));
        }

        return String.valueOf(value & 0x0FFFFFFFFl);
    }

    private byte[] decodeB64() {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[]  bytes = new byte[0];
        try  {
            bytes = decoder.decodeBuffer(string);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return bytes;
    }

    public static void main(String[]  args) throws UnsupportedEncodingException {
        URIDecoder decoder;
        decoder = new URIDecoder();

        while (true)  {
            byte[] buf = new byte[254];
            int len = 0;
            try {
                len = System.in.read(buf, 0, 254);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            String s="";
            if (len > 0)
                s = new String(buf, 0, len-1);
            else
                continue;

            System.out.println(s);
            if (s.equals("quit"))
                break;
            else  {
                decoder.setString(s);
                decoder.decode();
            }

        }
       // decoder.decode();
    }
}
