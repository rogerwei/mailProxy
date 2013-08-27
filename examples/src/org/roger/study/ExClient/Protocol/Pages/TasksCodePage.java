package org.roger.study.ExClient.Protocol.Pages;

/**
 * This class is the specific code page for Tasks in the ActiveSync protocol.
 * The code page number is 9.
 * 
 * @version 1.0
 * @author  Matthew Brace
 */
public class TasksCodePage extends CodePage {
    /**
     * Constructor for TasksCodePage.  Initializes all of the code page values.
     */
     public TasksCodePage() {
        /* Maps String to Token for the code page */
    /*    codepageTokens.put("Body", 0x05);
        codepageTokens.put("BodySize", 0x06);
        codepageTokens.put("BodyTruncated", 0x07);
        codepageTokens.put("Categories", 0x08);
        codepageTokens.put("Category", 0x09);
        codepageTokens.put("Complete", 0x0a);
        codepageTokens.put("DateCompleted", 0x0b);
        codepageTokens.put("DueDate", 0x0c);
        codepageTokens.put("UTCDueDate", 0x0d);
        codepageTokens.put("Importance", 0x0e);
        codepageTokens.put("Recurrence", 0x0f);
        codepageTokens.put("RecurrenceType", 0x10);
        codepageTokens.put("RecurrenceStart", 0x11);
        codepageTokens.put("RecurrenceUntil", 0x12);
        codepageTokens.put("RecurrenceOccurrences", 0x13);
        codepageTokens.put("RecurrenceInterval", 0x14);
        codepageTokens.put("RecurrenceDayOfMonth", 0x15);
        codepageTokens.put("RecurrenceDayOfWeek", 0x16);
        codepageTokens.put("RecurrenceWeekOfMonth", 0x17);
        codepageTokens.put("RecurrenceMonthOfYear", 0x18);
        codepageTokens.put("RecurrenceRegenerate", 0x19);
        codepageTokens.put("RecurrenceDeadOccur", 0x1a);
        codepageTokens.put("ReminderSet", 0x1b);
        codepageTokens.put("ReminderTime", 0x1c);
        codepageTokens.put("Sensitivity", 0x1d);
        codepageTokens.put("StartDate", 0x1e);
        codepageTokens.put("UTCStartDate", 0x1f);
        codepageTokens.put("Subject", 0x20);
        codepageTokens.put("CompressedRTF", 0x21);
        codepageTokens.put("OrdinalDate", 0x22);
        codepageTokens.put("SubOrdinalDate", 0x23);      */



        codepageTokens.put("Categories", 0x08);
        codepageTokens.put("Category", 0x09);
        codepageTokens.put("Complete", 0x0A);
        codepageTokens.put("DateCompleted", 0x0B);
        codepageTokens.put("DueDate", 0x0C);
        codepageTokens.put("UtcDueDate", 0x0D);
        codepageTokens.put("Recurrence", 0x0E);
        codepageTokens.put("Type", 0x10);
        codepageTokens.put("Start", 0x11);


        codepageTokens.put("Until", 0x12);
        codepageTokens.put("Occurrences", 0x13);
        codepageTokens.put("Interval", 0x14);
        codepageTokens.put("DayOfMonth", 0x15);
        codepageTokens.put("DayOfWeek", 0x16);
        codepageTokens.put("WeekOfMonth", 0x17);
        codepageTokens.put("MonthOfYear", 0x18);
        codepageTokens.put("Regenerate", 0x19);
        codepageTokens.put("DeadOccur", 0x1A);

        codepageTokens.put("ReminderSet", 0x1B);
        codepageTokens.put("ReminderTime", 0x1C);
        codepageTokens.put("Sensitivity", 0x1D);

        codepageTokens.put("StartDate", 0x1E);
        codepageTokens.put("UtcStartDate", 0x1F);
        codepageTokens.put("Subject", 0x20);
        codepageTokens.put("OrdinalDate", 0x22);
        codepageTokens.put("SubOrdinalDate", 0x23);
        codepageTokens.put("CalendarType", 0x24);
        codepageTokens.put("IsLeapMonth", 0x25);
        codepageTokens.put("FirstDayOfWeek", 0x26);


        /* Maps token to string for the code page */
        for (String s : codepageTokens.keySet()) {
            codepageStrings.put(codepageTokens.get(s), s);
        }

        codePageIndex = 0x09;
        codePageName = "Tasks";
    }
}
