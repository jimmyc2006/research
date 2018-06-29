package research.retry;

public class Stack implements AutoCloseable{
  private boolean isClose = false;
  private int connectionTimes = 0;
  
  // 为了测试前2次抛出异常，第3次成功
  public Stack getConnection() {
    connectionTimes++;
    System.out.println(connectionTimes);
    if (connectionTimes > 2) {
      return this;
    } else {
      throw new RuntimeException();
    }
  }
  
  public Object executeQuery() {
    return null;
  }

  @Override
  public void close() throws Exception {
    this.isClose = true;
  }

  public boolean isClose() {
    return isClose;
  }

  public void setClose(boolean isClose) {
    this.isClose = isClose;
  }
}
