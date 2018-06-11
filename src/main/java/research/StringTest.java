package research;

public class StringTest {
  
  public static void main(String[] args) {
    String str0 = "aaaa000";
    String str1 = "aaaa111";
    String str2 = "aaaa333";
    String str3 = "aaaa999";
    System.out.println(str1.compareTo(str2));
    System.out.println(str2.compareTo(str3));
    System.out.println(str3.compareTo(str1));
    System.out.println(str0.compareTo(str2));
  }
}
