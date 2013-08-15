package org.roger.study.stream.input;

import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-15
 * Time: 下午12:19
 * To change this template use File | Settings | File Templates.
 */
public class ReadKey {
    public static void main(String[] args) {
        BufferedInputStream in = new BufferedInputStream(System.in);
        byte buf[]  = new byte[10];
        try {
            in.read(buf, 0, 10);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        String s = new String(buf, 0);
        System.out.println(s);
    }
}
