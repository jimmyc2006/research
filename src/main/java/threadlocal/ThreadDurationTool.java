package threadlocal;
/** 
* @author shuwei 
* @version 创建时间：2018年9月29日 下午4:22:35 
* 类说明 
*/
public class ThreadDurationTool {
  private static ThreadLocal<Long> time = new ThreadLocal<>();
  
  public static void setTime() {
    time.set(System.currentTimeMillis());
  }
  
  public static long getDuration() {
    return System.currentTimeMillis() - time.get();
  }
}
