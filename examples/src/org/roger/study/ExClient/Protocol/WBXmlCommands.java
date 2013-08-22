package org.roger.study.ExClient.Protocol;

import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-22
 * Time: 下午1:22
 * To change this template use File | Settings | File Templates.
 */
public class WBXmlCommands {

    public static String getValue(byte[] wbxml, int cmd)  {
        int start=-1, end = -1;
        int i = 6;
        int len = wbxml.length;
        byte currentCmd;

        for (;i < len;i++)  {
            currentCmd = wbxml[i];

            //set start and move command
            start = ++i;

            if (wbxml[i] == 0x01 && wbxml[i] != 0x03 )  //command's value is null, for loog goto next
                continue;

            //has content
            while (wbxml[i] != 0x0 && wbxml[i+1] != 0x1 && (i+1) < len) i++;

            if (currentCmd == cmd)  {
                end = i;
                break;
            }

            i++;  //go to end of command
        }

        if (end < 0)
            return "";
        if (end <= start)
            return "";

        start++;
        try {
            return new String(wbxml, start, end -start, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return "";
    }
}
