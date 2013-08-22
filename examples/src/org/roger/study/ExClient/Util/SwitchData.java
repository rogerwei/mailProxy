package org.roger.study.ExClient.Util;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-22
 * Time: 下午5:12
 * To change this template use File | Settings | File Templates.
 */
public class SwitchData {

    public static byte[] LongTo4BytesInt(Long data)  {
        byte[]  key = new byte[4];

        //System.out.println(policyKey);
        for (int i=0;i < 4;i ++)  {
            int ch =  (((int)(data.longValue() >> (8 * i))) & 0xff);
            key[i] = (byte)ch;
        }

        return key;
    }

    public static byte[] HexStringToBytes(String hexString) {
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
