package org.roger.study.Test;


import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-26
 * Time: 下午3:58
 * To change this template use File | Settings | File Templates.
 */
public class TestEnterCmd {
    public static void main(String[] args)  {
        interaction();
    }

    private static void interaction() {
        byte[] data = new byte[100];
        while (true)  {
            try{
                int len = System.in.read(data);

                int res = paraCmd(new String(data));
                System.out.println(res);
            }
            catch(IOException e){
                System.out.println("Could not get data from user!!!!");
            }
        }
    }

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    private static int paraCmd(String cmdline) {
        if (cmdline == null)
            return -1;
        int step = 0;

        String tmp = cmdline.split("\n")[0];
        if (tmp.isEmpty())  return -2;

        String[] cmds = tmp.split(" ");
        for(String cmd:cmds)  {
            if (!cmd.isEmpty())  {
                if (step == 0)  {
                    if (!cmd.toLowerCase().equals("sendmail"))  {
                        return -1;
                    }
                    step ++;
                }else if (step == 1)  {
                    if (isNumeric(cmd))  {
                        return Integer.parseInt(cmd);
                    }else
                        return -1;
                }
            }
        }
        return -1;
    }
}
