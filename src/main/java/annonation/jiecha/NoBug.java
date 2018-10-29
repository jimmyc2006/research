package annonation.jiecha;
/** 
* @author shuwei 
* @version 创建时间：2018年9月19日 下午5:59:26 
* 类说明 
*/
public class NoBug {
  @Jiecha
  public void suanShu() {
    System.out.println("1234567890");
  }
  @Jiecha
  public void jiafa() {
    System.out.println("1+1=" + 1 + 1);
  }
  @Jiecha
  public void jiecha() {
    System.out.println("1-1=" + (1 - 1));
  }
  @Jiecha
  public void chengfa() {
    System.out.println("3 x 5 =" + 3 * 5);
  }
  @Jiecha
  public void chufa() {
    System.out.println("6 / 0 = " + 6 / 0);
  }
  
  public void ziwojieshao() {
    System.out.println("我写的程序没有 bug!");
  }
}
