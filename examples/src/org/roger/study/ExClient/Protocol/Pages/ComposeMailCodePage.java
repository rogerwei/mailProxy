package org.roger.study.ExClient.Protocol.Pages;

/**
 * Created with IntelliJ IDEA.
 * User: jh
 * Date: 13-8-5
 * Time: 下午3:23
 * To change this template use File | Settings | File Templates.
 */

public class ComposeMailCodePage extends CodePage {


    public ComposeMailCodePage() {
        /* Maps String to Token for the code page */


        codepageTokens.put("SendMail", 0x05);
        codepageTokens.put("SmartForward", 0x07);
        codepageTokens.put("SmartReply", 0x0d);
        codepageTokens.put("SaveInSentItems", 0x08);
        codepageTokens.put("ReplaceMime", 0x09);
        codepageTokens.put("Source", 0x0B);
        codepageTokens.put("FolderId", 0x0C);
        codepageTokens.put("ItemId", 0x0D);
        codepageTokens.put("LongId", 0x0E);


        codepageTokens.put("InstanceId", 0x0F);
        codepageTokens.put("Mime", 0x10);
        codepageTokens.put("ClientId", 0x11);
        codepageTokens.put("Status", 0x12);
        codepageTokens.put("AccountId", 0x13);


        /* Maps token to string for the code page */
        for (String s : codepageTokens.keySet()) {
            codepageStrings.put(codepageTokens.get(s), s);
        }

        codePageIndex = 21;
        codePageName = "Composemail";
    }


}
