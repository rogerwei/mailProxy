package org.roger.study.stream.input;

import java.io.IOException;
import java.io.StringBufferInputStream;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-15
 * Time: 下午12:52
 * To change this template use File | Settings | File Templates.
 */
public class ReadString {
    public static void main(String[] args)  {
        //get a string from the user
        byte buf[] = new byte[64];

        try {
            System.in.read(buf, 0, 64);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        String s1 = new String(buf, 0);

        //Read the string as a string buffer and output it
        StringBufferInputStream in = new StringBufferInputStream(s1);
        byte buf2[] = new byte[64];
        in.read(buf2, 0, 64);

        String s2 = new String(buf2, 0);
        System.out.println(s2);
    }
}
