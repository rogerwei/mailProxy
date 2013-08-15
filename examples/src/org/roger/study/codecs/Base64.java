package org.roger.study.codecs;

import sun.misc.BASE64Decoder;

/**
 * Created with IntelliJ IDEA.
 * User: roger
 * Date: 13-8-9
 * Time: 上午9:58
 * To change this template use File | Settings | File Templates.
 */
public class Base64 {

    public static String stringToHexString(String strPart) {
        String hexString = "";
        for (int i = 0; i < strPart.length(); i++) {
            int ch = (int) strPart.charAt(i);
            String strHex = Integer.toHexString(ch);
            hexString = hexString + strHex;
        }
        return hexString;
    }

    public static void main(String[] args)  {
        String data = "jRIJBA8zNTE1NTQwNTkwMjczNzYEcm4JXAlUb3VjaGRvd24=";
        String stat = "jAAJBAp2MTQwRGV2aWNlAApTbWFydFBob25l=";
        String dsds = "YWJj";
        String auth = "cm9nZXI6UHAxMjM0NTY==";

        BASE64Decoder decoder = new BASE64Decoder();

        try  {
            byte[]  bytes = decoder.decodeBuffer(auth);


            System.out.println(stringToHexString(new String(bytes, "ISO-8859-1")));
            System.out.println(new String(bytes));
        } catch (Exception e)  {
            e.printStackTrace();
        }
    }
}
