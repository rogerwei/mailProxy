package org.roger.study.ExClient.Protocol.Pages;


/**
 * Created with IntelliJ IDEA.
 * User: jh
 * Date: 13-8-5
 * Time: 下午3:24
 * To change this template use File | Settings | File Templates.
 */

public class RightsManagementCodePage extends  CodePage {

  public   RightsManagementCodePage()
    {

        codepageTokens.put("RightsManagementSupport", 0x05);
        codepageTokens.put("RightsManagementTemplates", 0x06);
        codepageTokens.put("RightsManagementTemplate", 0x07);
        codepageTokens.put("RightsManagementLicense", 0x08);
        codepageTokens.put("EditAllowed", 0x09);


        codepageTokens.put("ReplyAllowed", 0x0A);
        codepageTokens.put("ReplyAllAllowed", 0x0B);
        codepageTokens.put("ForwardAllowed", 0x0C);
        codepageTokens.put("ModifyRecipientsAllowed", 0x0D);
        codepageTokens.put("ExtractAllowed", 0x0E);
        codepageTokens.put("PrintAllowed", 0x0F);


        codepageTokens.put("ExportAllowed", 0x10);
        codepageTokens.put("ProgrammaticAccessAllowed", 0x11);
        codepageTokens.put("Owner", 0x12);
        codepageTokens.put("ContentExpiryDate", 0x13);
        codepageTokens.put("TemplateID", 0x14);
        codepageTokens.put("TemplateName", 0x15);

        codepageTokens.put("TemplateDescription", 0x16);
        codepageTokens.put("ContentOwner", 0x17);
        codepageTokens.put("RemoveRightsManagementDistribution", 0x18);



        /* Maps token to string for the code page */
        for (String s : codepageTokens.keySet()) {
            codepageStrings.put(codepageTokens.get(s), s);
        }

        codePageIndex = 24;
        codePageName = "RightsManagement";
    }
}
