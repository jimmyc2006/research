package research;

import java.io.File;

public class PicRename {
  public static void main(String[] args) {
    String dir = "/Users/apple/Downloads/20180607/";
    File dirFile = new File(dir);
    String[] fileNames = dirFile.list();
    for (String fileName : fileNames) {
      if (fileName.startsWith(".")) {
        continue;
      } else {
        File f = new File(dir + fileName);
        fileName = fileName.substring(2);
        f.renameTo(new File(dir + fileName));
      }
    }
  }
}
