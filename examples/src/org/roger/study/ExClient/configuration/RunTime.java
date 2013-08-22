package org.roger.study.ExClient.configuration;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.HashMap;

import static java.net.URLEncoder.encode;
import static org.roger.study.ExClient.configuration.Configs.userIndex;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-22
 * Time: 上午10:18
 * To change this template use File | Settings | File Templates.
 */
public class RunTime {
    private static final String DeviceId = "351554059027376";  //start id
    private static BigInteger clientId = new BigInteger(Configs.getStartId());
    private static byte[][] keys;
    private static HashMap<String, Long> policyKeys = new HashMap<String, Long>();

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

    public static void setPolicyKey(String user, Long key)  {
        policyKeys.put(user, key);
    }

    public static Long getPolicyKey(String user) {
        return policyKeys.get(user);
    }


    public static String getDeviceType() {
        return "SmartClient";  //To change body of created methods use File | Settings | File Templates.
    }

    public static String getModel()  {
        return "Client Tester";
    }

    public static String getOS()  {
        return "windows7";
    }

    public static String getLanguage()  {
        return "中文";
    }

    public static String getFriendlyName(String user)  {
        return "Test_" + user;
    }

    public static String getPhoneNumber(String user)  {
        return "";
    }

    public static synchronized String getClientId() {
        clientId = clientId.add(new BigInteger("1"));
        return String.valueOf(clientId);
    }
}
