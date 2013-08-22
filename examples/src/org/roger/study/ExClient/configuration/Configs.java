package org.roger.study.ExClient.configuration;

import java.io.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * 来自配置文件的数据，并序列化
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-21
 * Time: 上午11:09
 * To change this template use File | Settings | File Templates.
 */
public class Configs {
    private static String configfile = "config.ini";
    private static String runConfig = "running.ini";
    private static Properties properties = new Properties();
    private static Properties running = new Properties();
    //keys
    private static String Type = "type";
    private static String UserPeers = "userpeers";
    private static String Host = "serverhost";
    private static String Port = "serverport";
    private static String Times = "runtimes";
    static final String  ClientId = "clientId";
    //values
    private static String host;
    private static int port;
    private static int runTimes =0;
    private static String clientId;


    private static HashMap<String,String> auths = new HashMap<String, String>(); //username,password

    public static void init()  {
        try {
            properties.load(new FileInputStream(configfile));
            running.load(new FileInputStream(runConfig));
            initValues();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private static void initValues() {
        String peersValue = properties.getProperty(UserPeers);

        String[]  peers = peersValue.split(";");
        for (String peer: peers) {
            if (peer.contains(":")) {
                String[] auth = peer.split(":");
                auths.put(auth[0], auth[1]);
            }
        }

        host = properties.getProperty(Host);
        if (host.isEmpty())
            System.out.println("Get Server Host occur Error!");

        String tmp = properties.getProperty(Port);
        if (tmp.isEmpty()) {
            System.err.println("Get Server Port Occur Error");
        }
        port = Integer.parseInt(tmp);

        tmp = properties.getProperty(Times);
        if (tmp.isEmpty()) {
            System.err.println("Get Server Port Occur Error");
        }
        runTimes = Integer.parseInt(tmp);

        //running part
        clientId = getClientIdValue().toString();

        if (runTimes > 0)  {
            updateClientId(runTimes);
        }
    }

    private static BigInteger getClientIdValue() {
        BigInteger init=null;
        String value = running.getProperty(ClientId);
        if (value == null || value.isEmpty())  {
            init = new BigInteger("1376969465174");
        }else  {
            init = new BigInteger(value);
        }

        return init;
    }

    private static void updateClientId(int runTimes) {
        BigInteger init = getClientIdValue();
        init = init.add(new BigInteger(String.valueOf(runTimes)));
        running.setProperty(ClientId, String.valueOf(init));

        //update value
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(runConfig);
            running.store(outputStream, "Update '" + ClientId + "' value");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static String getHost()  {
        return  host;
    }

    public static int getPort()  {
        return port;
    }

    public static Set<String> getUsers()  {
        return auths.keySet();
    }

    public static String getPd(String user)  {
        return auths.get(user);
    }

    //from 0
    public static int userIndex(String user)  {
        int index = 0;

        for (String str:auths.keySet())  {
            if (str.equals(user))
                break;

            index ++;
        }
        return index;
    }

    public static int getRunTimes()  {
        return runTimes;
    }

    public static String getStartId() {
        return clientId;
    }
}
