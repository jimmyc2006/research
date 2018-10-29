package mdc;

import org.apache.log4j.MDC;

/** 
* @author shuwei 
* @version 创建时间：2018年9月29日 下午2:27:23 
* 类说明 
*/
public class MdcTest extends Thread {
  private int i;
  public MdcTest() {
  }
  
  public MdcTest(int i) {
    this.i = i;
  }
  
  public void run1() {
    System.out.println(++i);
    MDC.put("username", i);
    for (int j = 0; j < 100; j++) {
      System.out.println("aaa" + i);
      if (j == 10) {
        try {
          this.sleep(1000);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    System.out.println("run:" + i + "     " + MDC.get("username"));
  }
  
  public void run() {
    ThreadLocal<Long> tl = new ThreadLocal<>();
    tl.set(1000L);
    ThreadLocal<Long> t2 = new ThreadLocal<>();
    System.out.println(t2.get());
  }
  
  public static void main(String[] args) {
    MdcTest t1 = new MdcTest(1);
    t1.start();
//    ThreadTest t2 = new ThreadTest(2);
//    t2.start();
  }
}
