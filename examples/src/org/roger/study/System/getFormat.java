package org.roger.study.System;

import java.nio.charset.Charset;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-20
 * Time: 下午5:32
 * To change this template use File | Settings | File Templates.
 */
public class getFormat {
    public static void main(String[] args)  {
        System.getProperty(String.valueOf(Charset.defaultCharset()));
        System.out.println(Charset.defaultCharset());
    }
}
