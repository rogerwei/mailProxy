package org.roger.study.ExClient.controller;

import org.jboss.netty.util.internal.ConcurrentHashMap;
import org.roger.study.ExClient.configuration.Configs;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-22
 * Time: 下午5:34
 * To change this template use File | Settings | File Templates.
 */
public class TestCounter {
    private static ConcurrentHashMap<Integer, Integer> counter = new ConcurrentHashMap<Integer, Integer>();  //Channel Id, test times

    public static boolean testOne(Integer key)  {
        if (!counter.containsKey(key))  {
            counter.put(key, 1);
            return true;
        }

        Integer value = counter.get(key);
        if (value < Configs.getRunTimes())  {
            counter.replace(key, value+1);

            return true;
        }

        return false;
    }
}
