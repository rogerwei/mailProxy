package org.roger.study.ExClient.Protocol.WBTree;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-26
 * Time: 上午11:37
 * To change this template use File | Settings | File Templates.
 */
public class EndFather {
    private static Map<Integer, List<Integer>> values = new HashMap<Integer, List<Integer>>();
    public static List<Integer> provisionPage = new ArrayList<Integer>();
    static {
        provisionPage.add(0xd);
        provisionPage.add(0x4d);
        values.put(0xe, provisionPage);
    }

    public static boolean isEndFather(byte pageId, int cmd)  {
        Integer iPageId = Integer.valueOf(pageId);
        Integer iCmd = Integer.valueOf(cmd);
        if (values.containsKey(iPageId))  {
            for (Integer in: values.get(iPageId))  {
                if (in.equals(iCmd))
                    return true;
            }
        }

        return  false;
    }
}
