package org.roger.study.ExClient.Util;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-23
 * Time: 上午11:10
 * To change this template use File | Settings | File Templates.
 */
public class GetData {

    public static byte[] subBytes(byte[] bytes, int from, int length)  {
        byte[] data = new byte[length];
        System.arraycopy(bytes, from, data, 0, length);

        return data;
    }

}
