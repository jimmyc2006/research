package research;

import java.util.HashSet;
import java.util.Set;

/** 
* @author shuwei 
* @version 创建时间：2018年10月29日 下午4:11:08 
* 类说明 
*/
public class CollectionTest {
  public static void main(String[] args) {
    Set<String> set1 = new HashSet<>();
    Set<String> set2 = new HashSet<>();

    set1.add("a");
    set1.add("b");
    set1.add("c");

    set2.add("c");
    set2.add("d");
    set2.add("e");

    //交集
    set1.retainAll(set2);
    System.out.println("交集是 "+set1);
  }
}
