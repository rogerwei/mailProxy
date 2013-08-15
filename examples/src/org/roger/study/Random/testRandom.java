package org.roger.study.Random;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-15
 * Time: 下午4:20
 * To change this template use File | Settings | File Templates.
 */
public class testRandom {
    public static void main(String[] args) {
        Random random1 = new Random(100);
        System.out.println(random1.nextInt());
        System.out.println(random1.nextFloat());
        System.out.println(random1.nextBoolean());
        Random random2 = new Random(100);
        System.out.println(random2.nextInt());
        System.out.println(random2.nextFloat());
        System.out.println(random2.nextBoolean());
    }
}
