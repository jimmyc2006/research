package annonation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author shuwei
 * @version 创建时间：2018年9月19日 下午5:21:23 类说明
 */
@TestAnnotation
public class Test {
  
  @Check(value = "hi")
  int a;

  @Perform
  public void testMethod() {}

  @SuppressWarnings("deprecation")
  public void test1() {
    Hero hero = new Hero();
    hero.say();
    hero.speak();
  }

  public static void main(String[] args) {
    boolean hasAnnotation = Test.class.isAnnotationPresent(TestAnnotation.class);
    if (hasAnnotation) {
      TestAnnotation testAnnotation = Test.class.getAnnotation(TestAnnotation.class);
      System.out.println("id:" + testAnnotation.id());
      System.out.println("msg:" + testAnnotation.msg());
    }

    try {
      Field a = Test.class.getDeclaredField("a");
      a.setAccessible(true);
      // 成员变量上的注解
      Check check = a.getAnnotation(Check.class);
      if (check != null) {
        System.out.println("check value:" + check.value());
      }
      Method testMethod = Test.class.getDeclaredMethod("testMethod");
      if (testMethod != null) {
        // 获取方法中的注解
        Annotation[] ans = testMethod.getAnnotations();
        for (int i = 0; i < ans.length; i++) {
          System.out
              .println("method testMethod annotation:" + ans[i].annotationType().getSimpleName());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
