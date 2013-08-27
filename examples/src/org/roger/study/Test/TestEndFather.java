package org.roger.study.Test;

import org.roger.study.ExClient.Protocol.WBTree.EndFather;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-26
 * Time: 上午11:49
 * To change this template use File | Settings | File Templates.
 */
public class TestEndFather {
    public static void main(String[] args)  {
        System.out.println(EndFather.isEndFather((byte) 0xe, 0x3d));
    }
}
