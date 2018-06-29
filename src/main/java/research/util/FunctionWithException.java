package research.util;
/** 
* @author shuwei 
* @version 创建时间：2018年6月28日 下午7:00:26 
* 类说明 
*/
@FunctionalInterface
public interface FunctionWithException<T, R> {
  R apply(T t) throws Exception;
}
