package research;

import java.io.File;

public class DeleteLastUpdate {
  public static void main(String[] args) {
    String dir = "/Users/apple/.m2/repository";
    deleteLastUpdatedFile(new File(dir));
  }
  
  public static void deleteLastUpdatedFile(File dir) {
    for (File f : dir.listFiles()) {
      if (f.isDirectory()) {
        deleteLastUpdatedFile(f);
      } else {
        if(f.getName().endsWith(".lastUpdated")) {
          System.out.println(f.getAbsolutePath());
          f.delete();
        }
      }
    }
  }
}
