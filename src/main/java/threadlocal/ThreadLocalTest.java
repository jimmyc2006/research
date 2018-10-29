package threadlocal;

import java.util.Random;

/** 
* @author shuwei 
* @version 创建时间：2018年9月29日 下午4:05:24 
* ThreadLocal是多线程之间公用一个静态变量 
*/
public class ThreadLocalTest extends Thread {
  public static ThreadLocal<String> TLS = new ThreadLocal<>();

  private String name;
  
  public ThreadLocalTest(String name) {
    this.name = name;
  }

  @Override
  public void run() {
    TLS.set(this.name);
    ThreadDurationTool.setTime();
    try {
      Random r = new Random();
      Thread.sleep(r.nextInt(3000) + 1000);
    } catch (InterruptedException e) {
    }
    System.out.println(Thread.currentThread() + "耗时:" + ThreadDurationTool.getDuration());
    ThreadDurationTool.setTime();
    System.out.println(Thread.currentThread() + "获得到:" + TLS.get());
  }
  
  public static void main(String[] args) {
    for (int i = 0; i < 10; i++) {
      new ThreadLocalTest("T" + i).start();
    }
  }
  
}
