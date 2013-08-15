package org.roger.study.stream.input;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-15
 * Time: 下午12:36
 * To change this template use File | Settings | File Templates.
 */
public class ReadFile {
    public static void main(String[] args) throws IOException {
        byte buf[] = new byte[64];
        try {
            FileInputStream in = new FileInputStream("text.txt");
            in.read(buf, 0, 64);
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        String s = new String(buf, 0);
        System.out.println(s);
    }
}
