package org.roger.study.stream.input;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-15
 * Time: 下午12:22
 * To change this template use File | Settings | File Templates.
 */
public class ReadFloat {
    public static void main(String[] args)  {
        DataInputStream in = new DataInputStream(System.in);
        String s = new String();
        try {
            s = in.readLine();
            float f = Float.valueOf(s).floatValue();
            System.out.println(f);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }
}
