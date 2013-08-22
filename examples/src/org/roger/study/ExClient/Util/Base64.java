package org.roger.study.ExClient.Util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-21
 * Time: 下午2:35
 * To change this template use File | Settings | File Templates.
 */
public class Base64 {
    public static String Encode(byte[] src)  {
        BASE64Encoder encoder = new BASE64Encoder();

        String res = encoder.encodeBuffer(src);

        res = res.substring(0, res.length()-2);
        return res;
    }

    public static byte[] Decode(String src)  {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            return decoder.decodeBuffer(src);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
}
