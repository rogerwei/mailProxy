package org.roger.study.ExClient.Protocol.Pages;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-23
 * Time: 下午2:30
 * To change this template use File | Settings | File Templates.
 */
public class CodePageHelper {
    private static CodePage[] mCodePages = null;
    private static HashMap<String, Integer> PageId = new HashMap<String, Integer>();

    static  {
        if (mCodePages == null) {
            mCodePages = new CodePage[25];
            mCodePages[0] = new AirSyncCodePage();
            mCodePages[1] = new ContactsCodePage();
            mCodePages[2] = new EmailCodePage();
            mCodePages[3] = new AirNotifyCodePage();
            mCodePages[4] = new CalendarCodePage();
            mCodePages[5] = new MoveCodePage();
            mCodePages[6] = new ItemEstimateCodePage();
            mCodePages[7] = new FolderHierarchyCodePage();
            mCodePages[8] = new MeetingResponseCodePage();
            mCodePages[9] = new TasksCodePage();
            mCodePages[0xa] = new ResolveRecipientsCodePage();
            mCodePages[0xb] = new ValidateCertCodePage();
            mCodePages[0xc] = new Contacts2CodePage();
            mCodePages[0xd] = new PingCodePage();
            mCodePages[0xe] = new ProvisionCodePage();
            mCodePages[0xf] = new SearchCodePage();
            mCodePages[0x10] = new GALCodePage();
            mCodePages[0x11] = new AirSyncBaseCodePage();
            mCodePages[0x12] = new SettingsCodePage();
            mCodePages[0x13] = new DocumentLibraryCodePage();
            mCodePages[0x14] = new ItemOperationsCodePage();
            mCodePages[0x15] = new ComposeMailCodePage();
            mCodePages[0x16] = new Email2CodePage();
            mCodePages[0x17] = new NotesCodePage();
            mCodePages[0x18] = new RightsManagementCodePage();

            int n = 0x0;
            PageId.put("AirSync", n++);
            PageId.put("Contacts", n++);
            PageId.put("Email", n++);
            PageId.put("AirNotify", n++);
            PageId.put("Calendar", n++);
            PageId.put("Move", n++);
            PageId.put("GetItemEstimate", n++);
            PageId.put("FolderHierarchy", n++);
            PageId.put("MeetingResponse", n++);
            PageId.put("Tasks", n++);
            PageId.put("ResolveRecipients", n++);
            PageId.put("ValidateCert", n++);
            PageId.put("Contacts2", n++);
            PageId.put("Ping", n++);
            PageId.put("Provision", n++);
            PageId.put("Search", n++);  //15
            PageId.put("GAL", n++);
            PageId.put("AirSyncBase", n++);
            PageId.put("Settings", n++);
            PageId.put("DocumentLibrary", n++);
            PageId.put("ItemOperations", n++);
            PageId.put("ComposeMail", n++);
            PageId.put("Email2", n++);
            PageId.put("Notes", n++);
            PageId.put("RightsManagement", n++);
        }
    }

    public static Integer getPageId(String key)  {
        if (PageId.containsKey(key))
            return PageId.get(key);
        return -1;
    }

    public static Integer getToken(int page, String identity)  {
        if (page < 0 || page > 25 || mCodePages.length <= 0)
            return -1;

        return  mCodePages[page].getCodePageToken(identity);
    }
}
