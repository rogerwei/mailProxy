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
    private static String UserPeers = "user_peers";
    private static String Host = "server_host";
    private static String Port = "server_port";
    private static String Times = "run_times";
    private static String Ssl = "ssl";
    private static String Pfx = "pfx";
    private static String PfxPassword = "pfx_password";
    private static String Ca = "ca";
    private static String CaPassword = "ca_password";
    static final String  ClientId = "clientId";
    //values
    private static String host;
    private static int port;
    private static int runTimes =0;
    private static boolean ssl;
    private static String pfx;
    private static String pfxPassword;
    private static String ca;
    private static String caPassword;
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
        //[1]
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

        //ssl
        tmp = properties.getProperty(Ssl);
        if (!tmp.isEmpty())  {
            setSsl(Boolean.valueOf(tmp));
        }

        if (Configs.ssl)  {
            tmp = properties.getProperty(Pfx);
            if (!tmp.isEmpty())  {
                setPfx(tmp);
            }

            tmp = properties.getProperty(PfxPassword);
            if (!tmp.isEmpty())  {
                setPfxPassword(tmp);
            }

            tmp = properties.getProperty(Ca);
            if (!tmp.isEmpty())  {
                setCa(tmp);
            }

            tmp = properties.getProperty(CaPassword);
            if (!tmp.isEmpty())  {
                setCaPassword(tmp);
            }
        }

        //[2]
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

    public static void setRumTimes(int times) {
        runTimes = times;
    }

    public static String getStartId() {
        return clientId;
    }

    private static boolean isSsl() {
        return ssl;
    }

    private static void setSsl(boolean ssl) {
        Configs.ssl = ssl;
    }

    public static String getPfx() {
        return pfx;
    }

    private static void setPfx(String pfx) {
        Configs.pfx = pfx;
    }

    public static String getPfxPassword() {
        return pfxPassword;
    }

    private static void setPfxPassword(String pfxPassword) {
        Configs.pfxPassword = pfxPassword;
    }

    public static String getCa() {
        return ca;
    }

    private static void setCa(String ca) {
        Configs.ca = ca;
    }

    public static String getCaPassword() {
        return caPassword;
    }

    private static void setCaPassword(String caPassword) {
        Configs.caPassword = caPassword;
    }

    public static boolean isSslEnabled() {
        if (isSsl())  {
            return !pfx.isEmpty() && !pfxPassword.isEmpty()
                    && !ca.isEmpty() && !caPassword.isEmpty();
        }

        return false;
    }
}
