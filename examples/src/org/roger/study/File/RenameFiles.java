package org.roger.study.File;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-20
 * Time: 下午2:04
 讲head_first_c.pdf重命名为Head First C.pdf
 */
public class RenameFiles {
    private final String dir;

    public RenameFiles(String dir)  {
        this.dir = dir;
    }

    public void rename()  {
        File file = new File(dir);
        File f;

        String[] files = file.list();
        String fullFilename ="";
        for (String filename:files) {
            f = new File(file, filename);
            fullFilename = f.getName();
            System.out.println(fullFilename);
            f.renameTo(new File(file.getAbsolutePath() + "\\" + ChangeFilename(filename)));
        }
    }

    private String ChangeFilename(String filename) {
        String nickname = "";
        boolean switchOn = true;

        for (int i =0;i < filename.length();i++)  {
            int ch = filename.charAt(i);
            if (switchOn  && (ch >= 'a' && ch <= 'z'))  {
                nickname += (char) ( ch- 32);
                switchOn = false;
            }else if (ch == '_') {
                nickname += " ";
                switchOn = true;
            }else {
                nickname += (char)ch;
            }
        }

        System.out.println(nickname);
        return nickname;
    }

    public static void main(String[] args)  {
        new RenameFiles("E:\\test").rename();
    }
}
