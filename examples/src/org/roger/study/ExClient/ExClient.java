package org.roger.study.ExClient;

import org.roger.study.ExClient.configuration.Configs;
import org.roger.study.ExClient.controller.Booter;
import org.roger.study.ExClient.controller.UserInterface;
import org.roger.study.ExClient.test.Report;
import java.io.IOException;

import static org.roger.study.ExClient.Util.Number.*;
import static org.roger.study.ExClient.controller.UserInterface.*;
/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-20
 * Time: 下午6:10
 * To change this template use File | Settings | File Templates.
 */
public class ExClient {

    public static void main(String[] args)  {
        Report.TestStart();
        //init config
        Configs.init();
        //start console
        //ClientProxy proxy = new ClientProxy(Configs.getUsers().size());
        Booter booted = new Booter(Configs.getUsers().size());
        UserInterface.setBooter(booted);
        //proxy.start();

        //Human–Machine Interaction
        interaction();
    }

    private static void interaction() {
        byte[] data = new byte[100];
        while (true)  {
            try{
                System.in.read(data);

                int res = paraCmd(new String(data, "UTF-8"));
                if (res == -1)  {
                    showUsage();
                }else if (res == -3)  {   //quit
                    stop();
                    break;
                }else if (res == -4)  {   //stop
                    stop();
                }else if (res == -5)  {   //start
                    start();
                    break;
                }else if (res == -6)  {   //restart
                    restart();
                    break;
                }else if (res > 0){
                    sendMail(res);
                }
            }
            catch(IOException e){
                System.out.println("Could not get data from the console!");
            }
        }
    }

    private static void showUsage() {
        System.out.println("Command Line Usage:");
        System.out.println("        sendmail times");
        System.out.println("The argument of times must large then zero.");
    }

    /***
     *
     * @param cmdline
     * @return
     *  -6 :restart
     *  -5 :start
     *  -4 :stop
     *  -3 :quit
     *  -2 :cmd is null
     *  -1 :cmd usage error
     *  0 or large than 0: 0k
     */
    private static int paraCmd(String cmdline) {
        if (cmdline == null)
            return -2;
        int step = 0;

        int index = cmdline.indexOf("\r");
        if (index < 0)
            index = cmdline.indexOf("\n");

        String tmp = cmdline.substring(0, index);

        if (tmp.isEmpty())  return -2;

        String[] cmds = tmp.split(" ");
        for(String cmd:cmds)  {
            if (!cmd.isEmpty() && isNumericOrLetter(cmd))  {
                if (step == 0)  {
                    if (cmd.toLowerCase().equals("quit"))
                        return -3;
                    if (cmd.toLowerCase().equals("stop"))
                        return -4;
                    if (cmd.toLowerCase().equals("start"))
                        return -5;
                    if (cmd.toLowerCase().equals("restart"))
                        return -6;
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

        return -2;
    }
}
