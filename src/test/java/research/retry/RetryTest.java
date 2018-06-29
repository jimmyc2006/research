package research.retry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class RetryTest {
  
  private Retry testObj;
  private Stack stackMock;
  
  @Before
  public void init() {
    testObj = new Retry();
    stackMock = Mockito.mock(Stack.class);
    testObj.setStack(stackMock);
  }
  
  /**
   * 测试在try中出现异常的时候，是否能正确的进行重试
   */
  @Test
  public void testAllTryException() {
    String excEptionMessage = "try异常";
    int maxRetryTimes = 3;
    testObj.setMaxRetryTimes(maxRetryTimes);
    Mockito.when(stackMock.getConnection()).thenThrow(new RuntimeException(excEptionMessage));
    try {
      testObj.retryTest();
    } catch (Exception e) {
      Assert.assertEquals(e.getMessage(), excEptionMessage);
      Assert.assertEquals(testObj.getTime(), maxRetryTimes);
      try {
        Mockito.verify(stackMock, Mockito.times(0)).close();
      } catch (Exception e1) {
      }
    }
  }
  
  /**
   * 测试在try的时候，2次失败后，第三次成功
   */
  @Test
  public void testTryException1Times() {
    int maxRetryTimes = 3;
    testObj.setMaxRetryTimes(maxRetryTimes);
    Mockito.when(stackMock.getConnection()).thenCallRealMethod();
    Mockito.when(stackMock.executeQuery()).thenReturn(new Object());
    try {
      testObj.retryTest();
      Assert.assertTrue(true);
    } catch (Exception e) {
      Assert.assertTrue(false);
    }
  }
  
  /**
   * 从这个用例可以看出，当execute抛出异常的时候，close方法会被执行
   */
  @Test
  public void testAllExecuteException() {
    String excEptionMessage = "execute异常";
    int maxRetryTimes = 5;
    testObj.setMaxRetryTimes(maxRetryTimes);
    Mockito.when(stackMock.getConnection()).thenReturn(stackMock);
    Mockito.when(stackMock.executeQuery()).thenThrow(new RuntimeException(excEptionMessage));
    try {
      testObj.retryTest();
    } catch (Exception e) {
      Assert.assertEquals(e.getMessage(), excEptionMessage);
      Assert.assertEquals(testObj.getTime(), maxRetryTimes);
      try {
        Mockito.verify(stackMock, Mockito.times(maxRetryTimes)).close();
      } catch (Exception e1) {
      }
    }
  }
  
}
