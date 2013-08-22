package org.roger.study.ExClient.Protocol;

import org.roger.study.ExClient.configuration.Configs;
import org.roger.study.ExClient.configuration.RunTime;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import static org.roger.study.ExClient.Util.Base64.Encode;
import static org.roger.study.ExClient.Util.SwitchData.LongTo4BytesInt;
import static org.roger.study.ExClient.configuration.RunTime.getDeviceId;
import static org.roger.study.ExClient.configuration.RunTime.getDeviceType;
import static org.roger.study.ExClient.configuration.RunTime.getPolicyKey;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-21
 * Time: 下午2:34
 * To change this template use File | Settings | File Templates.
 */
public class BuildURI {

    private static float version = 14.1f;
    private static final HashMap<String, Integer> commands = new HashMap<String, Integer>();
    static {
        commands.put("Sync", 0);
        commands.put("SendMail", 1);
        commands.put("SmartForward", 2);
        commands.put("SmartReply", 3);
        commands.put("GetAttachment", 4);
        commands.put("FolderSync", 9);
        commands.put("FolderCreate", 10);
        commands.put("FolderDelete", 11);
        commands.put("FolderUpdate", 12);
        commands.put("MoveItems", 13);
        commands.put("GetItemEstimate", 14);
        commands.put("MeetingResponse", 15);
        commands.put("Search", 16);
        commands.put("Settings", 17);
        commands.put("Ping", 18);
        commands.put("ItemOperations", 19);
        commands.put("Provision", 20);
        commands.put("ResolveRecipients", 21);
        commands.put("ValidateCert", 22);
    }

    public static String getURI(String command, String user) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.reset();

        //input Protocol
        outputStream.write(((int)(version*10) & 0xff));

        //input Command code
        outputStream.write(commands.get(command) & 0xff);

        //input Locale
        outputStream.write(0x9);
        outputStream.write(0x4);

        //input device Id length
        String DeviceId = getDeviceId(user);
        outputStream.write(DeviceId.length() & 0xff);

        //input device Id
        try {
            outputStream.write(DeviceId.getBytes());
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        //input Policy Key length
        if (command.equals("Provision"))
            outputStream.write(0x0);
        else  {
            outputStream.write(0x4);
            try {
                outputStream.write(LongTo4BytesInt(getPolicyKey(user)));
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }


        String DeviceType = getDeviceType();
        //input Device type length
        outputStream.write(DeviceType.length() & 0xff);

        //input device Type
        try {
            outputStream.write(DeviceType.getBytes());
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        //Parameters
        //Options Used by SmartReply, SmartForward, SendMail, ItemOperations
        if (command.equals("SendMail"))  {
            outputStream.write(0x7);     //Tags
            outputStream.write(0x1);    //len
            outputStream.write(0x1);    //value  0x1:SaveInSent  0x2:AcceptMultiPart
        }
        return Encode(outputStream.toByteArray());  //To change body of created methods use File | Settings | File Templates.
    }


}
