package org.roger.study.ExClient.controller;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.util.internal.ConcurrentHashMap;
import org.roger.study.ExClient.configuration.Configs;
import org.roger.study.ExClient.test.Report;

import java.util.Set;

import static org.roger.study.ExClient.configuration.Configs.getPd;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-21
 * Time: 下午5:53
 * 保存新的channel，并与用户关联.提供Channel与用户的映射关系
 * 原则：channel就在传输接口部分停止向上传输
 */
public class HandleChannel {
    private static ChannelGroup channels = new DefaultChannelGroup();
    private static ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<Integer, String>();

    /**
     *
     * @param channel  add new channel
     * @return  peer user
     */
    public static synchronized String BuildPeer(Channel channel)  {
        String user = getNewUser();
        if (user.isEmpty())
            return "";

        channels.add(channel);
        map.put(channel.getId(), user);

        Report.logon(user);
        SendRequest.Options(channel);

        return user;
    }

    /**
     * delete user-channel peer with channel
     * @param channel
     * @return success or fail
     */
    public static boolean DeletePeer(Channel channel)  {
        Integer key = channel.getId();

        return map.remove(key, getUser(channel));
    }

    /**
     * get user name by channel
     * @param channel
     * @return  username
     */
    public static String getUser(Channel channel)  {
        Integer key = channel.getId();

        return map.get(key);
    }

    private static String getNewUser() {
        Set<String> users = Configs.getUsers();

        for (String user:users)  {
            if (!map.contains(user))
                return user;
        }
        return "";
    }

    public static String getAuth(Channel channel)  {
        String user = getUser(channel);
        if (user.isEmpty())
            return "";

        String pd = getPd(user);

        if (pd.isEmpty())
            return "";

        return (user + ":" + pd);
    }

    public static ChannelGroup activeChannels()  {
        return channels;
    }
}
