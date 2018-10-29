package annonation;

/**
 * @author shuwei
 * @version 创建时间：2018年9月19日 下午5:33:45 类说明
 */
public class Hero {
  
  @Deprecated
  public void say() {
    System.out.println("Noting has to say!");
  }

  public void speak() {
    System.out.println("I have a dream!");
  }

}
