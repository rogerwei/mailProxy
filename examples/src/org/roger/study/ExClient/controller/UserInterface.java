package org.roger.study.ExClient.controller;

import static org.roger.study.ExClient.configuration.Configs.setRumTimes;
import static org.roger.study.ExClient.controller.TestCounter.*;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-26
 * Time: 下午4:32
 * To change this template use File | Settings | File Templates.
 */
public class UserInterface {
    private static Booter booter;
    public static void sendMail(int times)  {
        if (TestOver())  {
            clearTestCounter();
            setRumTimes(times);
            SendRequest.SendMail();
        }else  {
            System.out.println("Your Test is Running.Pls do this test latter.");
        }
    }

    public static void stop()  {
        if (TestOver())  {
            if (booter == null)  {
                System.out.println("Booter is null.Can't controll it.");
            }else
                booter.stop();
        }else  {
            System.out.println("Your Test is Running.Pls quit latter.");
        }
    }

    public static void start()  {
        if (TestOver())  {
            if (booter == null)  {
                System.out.println("Booter is null.Can't controll it.");
            }else
                booter.start();
        }else  {
            System.out.println("Your Test is Running.Pls quit latter.");
        }
    }

    public static void restart()  {
        if (TestOver())  {
            if (booter == null)  {
                System.out.println("Booter is null.Can't controll it.");
            }else
                booter.restart();
        }else  {
            System.out.println("Your Test is Running.Pls quit latter.");
        }
    }

    public static void setBooter(Booter booter) {
        UserInterface.booter = booter;
    }
}
