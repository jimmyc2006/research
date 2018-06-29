package research.retry;

/**
 * 此类主要用于测试try resource中的重试机制
 * 可能抛出异常的点，try中的表达式和try后面的语句块
 * 运行过程见相应的单元测试
 * @author shuwei
 *
 */
public class Retry {
  private Stack stack;
  private int maxRetryTimes = 3;
  private int time = 0;
  
  public String retryTest() throws Exception {
    while(true) {
      time++;
      try (Stack t = stack.getConnection()) {
        Object o = t.executeQuery();
        return o.toString();
      } catch (Exception e1) {
        if (time >= maxRetryTimes) {
          throw e1;
        }
      }
    }
  }
  
  public void setStack(Stack stack) {
    this.stack = stack;
  }

  public int getTime() {
    return time;
  }

  public int getMaxRetryTimes() {
    return maxRetryTimes;
  }

  public void setMaxRetryTimes(int maxRetryTimes) {
    this.maxRetryTimes = maxRetryTimes;
  }
}
