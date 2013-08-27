package org.roger.study.ExClient.Protocol.Pages;

/**
 * Created with IntelliJ IDEA.
 * User: jh
 * Date: 13-8-5
 * Time: 下午3:24
 * To change this template use File | Settings | File Templates.
 */
public class NotesCodePage extends CodePage {


    public  NotesCodePage()
    {

        codepageTokens.put("Subject", 0x05);
        codepageTokens.put("MessageClass", 0x06);
        codepageTokens.put("LastModifiedDate", 0x07);
        codepageTokens.put("Categories", 0x08);
        codepageTokens.put("Category", 0x09);



        /* Maps token to string for the code page */
        for (String s : codepageTokens.keySet()) {
            codepageStrings.put(codepageTokens.get(s), s);
        }

        codePageIndex = 23;
        codePageName = "Notes";
    }
}
