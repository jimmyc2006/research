package research;
/** 
* @author shuwei 
* @version 创建时间：2018年6月15日 下午5:05:16 
* 类说明 
*/
public class RegexTest {
  public static void main(String[] args) {
    String regex = "(?i).+[.](jpg|png|bmp)";
    System.out.println("1.jpg".matches(regex));
    System.out.println("1.png".matches(regex));
    System.out.println("1sddsfFFF.bmp".matches(regex));
    System.out.println("1sddsfFFF.bMp".matches(regex));
  }
}
