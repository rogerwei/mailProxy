package org.roger.study.codecs;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import static java.net.URLDecoder.decode;
/**
 * Created with IntelliJ IDEA.
 * User: roger
 * Date: 13-8-12
 * Time: 上午11:59
 * To change this template use File | Settings | File Templates.
 * http://bijian1013.iteye.com/blog/1841030
 */
public class utf8ToChinese {

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

                if (cmd.startsWith("quit"))
                    break;

                System.out.println(decode(ShowGB2312.addSeparator(cmd), "utf-8"));
            }
            catch(IOException e){
                System.out.println("Could not get data from user!!!!");
            }
        }
    }

}
