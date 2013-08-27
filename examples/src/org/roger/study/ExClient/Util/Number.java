package org.roger.study.ExClient.Util;

import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-26
 * Time: 下午4:28
 * To change this template use File | Settings | File Templates.
 */
public class Number {
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public static boolean isNumericOrLetter(String cmd) {
        Pattern pattern = Pattern.compile("[0-9a-zA-Z]*");
        return pattern.matcher(cmd).matches();
    }
}
