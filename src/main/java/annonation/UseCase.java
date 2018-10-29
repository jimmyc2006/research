package annonation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
* @author shuwei 
* @version 创建时间：2018年9月12日 上午9:59:14 
* 类说明 
*/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UseCase {
  public String id();
  public String description() default "no description";
}
