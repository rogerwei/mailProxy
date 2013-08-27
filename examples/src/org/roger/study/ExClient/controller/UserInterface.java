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
    public static void sendMail(int times)  {
        if (TestOver())  {
            clearTestCounter();
            setRumTimes(times);
            SendRequest.SendMail();
        }else  {
            System.out.println("Your Test is Running.Pls do this test latter.");
        }
    }
}
