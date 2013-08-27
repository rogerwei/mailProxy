package org.roger.study.ExClient.Protocol;

import org.roger.study.ExClient.Protocol.Pages.CodePageHelper;
import org.roger.study.ExClient.Protocol.WBTree.WbXMLTree;

import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-23
 * Time: 下午1:40
 * To change this template use File | Settings | File Templates.
 */
public class ParaCmd {
    public enum StatusType{RootCmd, DeviceInformation, Policy};

    private byte[] wbXml;
    private WbXMLTree wbXMLTree;

    //cmd
    private byte status = 0x46;

    public ParaCmd(byte[] wbXml)  {
        this.wbXml = wbXml;
        wbXMLTree = new WbXMLTree(this.wbXml);
        wbXMLTree.init();
    }

    public boolean isProvision() {
        byte pageId = (byte) (CodePageHelper.getPageId("Provision") & 0xff);
        Integer token = CodePageHelper.getToken(pageId, "Provision");
        byte cmd = (byte)((token & 0xff)+0x40);
        return wbXMLTree.isThisRoot(pageId, cmd);  //To change body of created methods use File | Settings | File Templates.
    }

    public Long policyKey()  {
        byte pageId = (byte) (CodePageHelper.getPageId("Provision") & 0xff);
        Integer token = CodePageHelper.getToken(pageId, "PolicyKey");
        byte cmd = (byte)((token & 0xff)+0x40);

        byte[] value = wbXMLTree.value(pageId, cmd);
        try {
            return Long.valueOf(new String(value, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return Long.valueOf(-1);
    }

    public int Status(StatusType type) {
        switch (type) {
            case RootCmd:  {
                byte id = wbXMLTree.rootPageId();
                Integer value = CodePageHelper.getToken(id, "Status");
                byte cmd = (byte)((value & 0xff)+0x40);
                return wbXMLTree.value(id,cmd)[0];
            }
            case DeviceInformation:  {
                return value("Settings", "DeviceInformation", "Status");
            }
            case Policy:  {
                return value("Provision", "Policy", "Status");
            }

            default:
                return -1;
        }
    }

    private int value(String page, String father, String leaf) {
        Integer id = CodePageHelper.getPageId(page);
        Integer firstValue = CodePageHelper.getToken(id, father);
        Integer secondValue = CodePageHelper.getToken(id, leaf);
        byte first = (byte)((firstValue & 0xff)+0x40);
        byte second = (byte)((secondValue & 0xff)+0x40);

        byte[]  va =  wbXMLTree.value(first, (byte)(id & 0xff) ,second);

        if (va == null)
            return -1;

        try {
            return Integer.valueOf(new String(va, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return -1;
    }
}
