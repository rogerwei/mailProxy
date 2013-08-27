package org.roger.study.ExClient.Protocol.Pages;

/**
 * Created with IntelliJ IDEA.
 * User: jh
 * Date: 13-8-5
 * Time: 下午3:24
 * To change this template use File | Settings | File Templates.
 */

public class Email2CodePage extends  CodePage {


    public  Email2CodePage()
    {
        codepageTokens.put("UmCallerID", 0x05);
        codepageTokens.put("UmUserNotes", 0x06);
        codepageTokens.put("UmAttDuration", 0x07);
        codepageTokens.put("UmAttOrder", 0x08);
        codepageTokens.put("ConversationId", 0x09);

        codepageTokens.put("ConversationIndex", 0x0A);
        codepageTokens.put("LastVerbExecuted", 0x0B);
        codepageTokens.put("LastVerbExecutionTime", 0x0C);
        codepageTokens.put("ReceivedAsBcc", 0x0D);

        codepageTokens.put("Sender", 0x0E);
        codepageTokens.put("CalendarType", 0x0F);
        codepageTokens.put("IsLeapMonth", 0x10);
        codepageTokens.put("AccountId", 0x11);
        codepageTokens.put("FirstDayOfWeek", 0x12);
        codepageTokens.put("MeetingMessageType", 0x13);

        /* Maps token to string for the code page */
        for (String s : codepageTokens.keySet()) {
            codepageStrings.put(codepageTokens.get(s), s);
        }

        codePageIndex = 22;
        codePageName = "Email2";
    }

}
