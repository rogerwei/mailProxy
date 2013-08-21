package org.roger.study.ExClient.configuration;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import static java.net.URLEncoder.encode;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-21
 * Time: 上午11:09
 * To change this template use File | Settings | File Templates.
 */
public class Configs {
    private static String host = "192.168.20.188";
    private static int port = 80;
    private static final String DeviceId = "351554059027376";
    private static String[] args = {"roger", "mail01"};
    private static byte[][] keys;

    public static String getHost()  {
        return host;
    }

    public static int getPort()  {
        return port;
    }

    public static String getDeviceId(String user) {
        int index = userIndex(user);

        BigInteger integer = new BigInteger(DeviceId);
        integer = integer.add(new BigInteger(String.valueOf(index)));

        try {
            return encode(String.valueOf(integer), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return "";
    }


    public static byte[] getPolicyKey(String user) {
        return keys[userIndex(user)];
    }

    //from 0
    private static int userIndex(String user)  {
        int index = 0;

        for (String str:args)  {
            if (str.equals(user))
                break;

            index ++;
        }
        return index;
    }

    public static String getDeviceType() {
        return "SmartClient";  //To change body of created methods use File | Settings | File Templates.
    }
}
