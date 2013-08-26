package org.roger.study.ExClient;

import org.roger.study.ExClient.configuration.Configs;
import org.roger.study.ExClient.transport.ClientProxy;

import java.io.IOException;

import static org.roger.study.ExClient.Util.Number.isNumeric;
import static org.roger.study.ExClient.controller.UserInterface.sendMail;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-20
 * Time: 下午6:10
 * To change this template use File | Settings | File Templates.
 */
public class ExClient {

    public static void main(String[] args)  {
        //init config
        Configs.init();
        //start console
        ClientProxy proxy = new ClientProxy(Configs.getUsers().size());
        proxy.start();

        //Human–Machine Interaction
        interaction();
    }

    private static void interaction() {
        byte[] data = new byte[100];
        while (true)  {
            try{
                System.in.read(data);

                int res = paraCmd(new String(data));
                if (res == -1)  {
                    showUsage();
                }else  {
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
