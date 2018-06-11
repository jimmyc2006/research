package research;

import java.io.File;

public class Test {
  public static void main1(String[] args) {
    /*
     * String dir =
     * "/Users/apple/Library/Containers/com.tencent.WeWorkMac/Data/Library/Application Support/WXWork/Data/1688853619545045/Cache/File/2018-05/账单截图样本"
     * ; File fDir = new File(dir); for (String f : fDir.list()) { System.out.println(f); }
     */
    String base = "/Users/apple/Downloads/image_result/";
    File f = new File(base);
    String[] fileNames = f.list();
    int i = 0;
    for (String name : fileNames) {
      if(name.startsWith(".")) {
        continue;
      } else {
        File fi = new File(base + name);
        String last = name.substring(name.lastIndexOf("."));
        String newFName = base + (i++) + last;
        fi.renameTo(new File(newFName));
      }
    }
    System.out.println(f.getAbsolutePath());
    System.out.println(f.isFile());
  }
  
  public static void main(String[] args) {
    Object[] test = new Object[] {1,2,3,4};
    test(test);
  }
  
  public static void test(Object... args) {
    System.out.println(args.length);
  }
}
