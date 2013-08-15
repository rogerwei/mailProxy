package org.roger.study.codecs;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import static java.net.URLDecoder.decode;


/**
 * Created with IntelliJ IDEA.
 * User: roger
 * Date: 13-8-12
 * Time: 下午12:11
 * To change this template use File | Settings | File Templates.
 * From:http://bijian1013.iteye.com/blog/1841030
 */
public class ShowGB2312 {
    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println("Pls input the encode string:");

        byte[] data = new byte[1000];

        while(true)
        {
            try{
                int len = System.in.read(data);

                if (len < 1)
                    continue;
                String cmd = new String(data, 0, len-1);
                System.out.println(decode(addSeparator(cmd), "GB2312"));
            }
            catch(IOException e){
                System.out.println("Could not get data from user!!!!");
            }
        }
    }

    public static  String addSeparator(String  src)  {
        String dst="";
        for(int i=0;i < src.length(); i ++)  {
            if (i%2 == 0)
                dst += "%";

            dst += src.charAt(i);
        }

        return dst;
    }
}
