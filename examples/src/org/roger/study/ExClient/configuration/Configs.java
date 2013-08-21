package org.roger.study.ExClient.configuration;

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

    public static String getHost()  {
        return host;
    }

    public static int getPort()  {
        return port;
    }
}
