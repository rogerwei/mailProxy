package org.roger.study.mailClient;

import java.io.*;
import java.math.BigInteger;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-16
 * Time: 下午4:05
 * To change this template use File | Settings | File Templates.
 */
public class configs {
    static String configFile = "test.config";
    static String runConfig = "running.ini";
    static String startId;

    //key
    static final String  startID = "startId";

    private static Properties properties = new Properties();
    private static Properties running = new Properties();

    public static void init()  {
        try {
            properties.load(new FileInputStream(configFile));
            running.load(new FileInputStream(runConfig));
            setRunning();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private static void setRunning() {
        BigInteger init;
        String value = running.getProperty(startID);
        if (value.isEmpty())  {
            init = new BigInteger("1376969465174");
        }else  {
            init = new BigInteger(value);
        }

        startId = init.toString();

        init = init.add(new BigInteger(getKeyValue("testTimes")));
        running.setProperty(startID, String.valueOf(init));

        //update value
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(runConfig);
            running.store(outputStream, "Update '" + startID + "' value");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static String getKeyValue(String key)  {
        String value = properties.getProperty(key);
        if (value.isEmpty())  {
            if (key.equals("serverHost"))
                value = "192.168.20.249";
            else if (key.equals("serverPort"))
                value = "80";
            else if (key.equals("testTimes"))
                value = "10";
        }

        return value;
    }

    public static String getStartId()  {
        return startId;
    }
}
