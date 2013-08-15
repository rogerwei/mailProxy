package org.roger.study.codecs;

/**
 * Created with IntelliJ IDEA.
 * User: roger
 * Date: 13-8-12
 * Time: 上午11:29
 * To change this template use File | Settings | File Templates.
 */
/*
 * (#)CharsetConvertor.java 1.0 2009-8-19
 */

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.text.normalizer.UTF16;

/**
 * @author 赵博
 * @version $1.0, 2009-8-19
 * @since JDK6
 */
public class CharsetConvertor
{
    public static void main(String[] args)
    {

        try
        {
            String s = "中国人";
            System.out.println(s);
            System.out.println(s.getBytes().length);
            String fullByte = CharsetConvertor.utf8ToUnicode(s);
            System.out.println(fullByte);
            System.out.println(CharsetConvertor.Unicode2GBK(fullByte));
            System.out.println(CharsetConvertor.Unicode2GBK(fullByte).getBytes("gbk").length);

             String fullStr = new String( fullByte.getBytes(), "UTF-8");
             System.out.println("string from GBK to UTF-8 byte:  " + fullStr);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @param chinese
     * @return
     */
    public static byte[] gbk2utf8(String chinese)
    {
        char c[] = chinese.toCharArray();
        byte[] fullByte = new byte[3 * c.length];
        for (int i = 0; i < c.length; i++)
        {
            int m = (int) c[i];
            String word = Integer.toBinaryString(m);
            // System.out.println(word);

            StringBuffer sb = new StringBuffer();
            int len = 16 - word.length();
            // 锟斤拷锟斤拷
            for (int j = 0; j < len; j++)
            {
                sb.append("0");
            }
            sb.append(word);
            sb.insert(0, "1110");
            sb.insert(8, "10");
            sb.insert(16, "10");

            // System.out.println(sb.toString());

            String s1 = sb.substring(0, 8);
            String s2 = sb.substring(8, 16);
            String s3 = sb.substring(16);

            byte b0 = Integer.valueOf(s1, 2).byteValue();
            byte b1 = Integer.valueOf(s2, 2).byteValue();
            byte b2 = Integer.valueOf(s3, 2).byteValue();
            byte[] bf = new byte[3];
            bf[0] = b0;
            fullByte[i * 3] = bf[0];
            bf[1] = b1;
            fullByte[i * 3 + 1] = bf[1];
            bf[2] = b2;
            fullByte[i * 3 + 2] = bf[2];

        }
        return fullByte;
    }

    /**
     * utf-8 转换�?unicode
     *
     * @author fanhui 2007-3-15
     * @param inStr
     * @return
     */
    public static String utf8ToUnicode(String inStr)
    {
        char[] myBuffer = inStr.toCharArray();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < inStr.length(); i++)
        {
            Character.UnicodeBlock ub = Character.UnicodeBlock.of(myBuffer[i]);
            if (ub == Character.UnicodeBlock.BASIC_LATIN)
            {
                // 英文及数字等
                sb.append(myBuffer[i]);
            }
            else if (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)
            {
                // 全角半角字符
                int j = (int) myBuffer[i] - 65248;
                sb.append((char) j);
            }
            else
            {
                // 汉字
                short s = (short) myBuffer[i];
                String hexS = Integer.toHexString(s);
                String unicode = "\\u" + hexS;
                sb.append(unicode.toLowerCase());
            }
        }
        return sb.toString();
    }

    /**
     * unicode 转换�?utf-8
     *
     * @author fanhui 2007-3-15
     * @param theString
     * @return
     */
    public static String unicodeToUtf8(String theString)
    {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;)
        {
            aChar = theString.charAt(x++);
            if (aChar == '\\')
            {
                aChar = theString.charAt(x++);
                if (aChar == 'u')
                {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++)
                    {
                        aChar = theString.charAt(x++);
                        switch (aChar)
                        {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException("Malformed   <a><font color=#4563b9>\\uxxxx</font></a>   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                }
                else
                {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            }
            else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }

    /**
     * 中文转unicode
     *
     * @param str
     * @return 反回unicode编码
     */

    public static String GBK2Unicode(String str)
    {

        StringBuffer result = new StringBuffer();

        for (int i = 0; i < str.length(); i++)
        {

            char chr1 = (char) str.charAt(i);

            if (!CharsetConvertor.isNeedConvert(chr1))
            {

                result.append(chr1);

                continue;

            }

            result.append("\\u" + Integer.toHexString((int) chr1));

        }

        return result.toString();

    }

    /**
     * unicode转中�?
     *
     * @param dataStr
     * @return 中文
     */

    public static String Unicode2GBK(String dataStr)
    {

        int index = 0;

        StringBuffer buffer = new StringBuffer();

        while (index < dataStr.length())
        {

            if (!"\\u".equals(dataStr.substring(index, index + 2)))
            {

                buffer.append(dataStr.charAt(index));

                index++;

                continue;

            }

            String charStr = "";

            charStr = dataStr.substring(index + 2, index + 6);
            char letter = (char) Integer.parseInt(charStr, 16);
            try
            {
                System.out.println("byte length="+(letter+"").getBytes("gbk").length);
                buffer.append(new String((letter+"").getBytes("gbk"),"gbk"));
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }

            index += 6;

        }

        return buffer.toString();

    }

    public static boolean isNeedConvert(char para)
    {

        return ((para & (0x00FF)) != para);

    }
    /**
     * 将文件名中的汉字转为UTF8编码的串,以便下载时能正确显示另存的文件名.
     *
     * @param s
     *            原文件名
     * @return 重新编码后的文件名
     * @author yue
     */
    public static String toUtf8String(String s)
    {
        if (s == null || s.equals(""))
        {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        try
        {
            char c;
            for (int i = 0; i < s.length(); i++)
            {
                c = s.charAt(i);
                if (c >= 0 && c <= 255)
                {
                    sb.append(c);
                }
                else
                {
                    byte[] b;

                    b = Character.toString(c).getBytes("utf-8");

                    for (int j = 0; j < b.length; j++)
                    {
                        int k = b[j];
                        if (k < 0)
                            k += 256;
                        sb.append("%" + Integer.toHexString(k).toUpperCase());
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 将10进制的utf-8字符码，映射成为汉字。
     *
     * @param code
     * @return
     */
    public static String utf8decode(String code)
    {
        Pattern regex = Pattern.compile("&#\\d{5};");
        Matcher matcher = regex.matcher(code);
        int index = 0;
        while (matcher.find())
        {
            code = code.replaceAll(matcher.group(index), UTF16.valueOf(Integer.parseInt(matcher.group(index).replaceAll("[#&;]", ""))));

        }

        return code;
    }
}


