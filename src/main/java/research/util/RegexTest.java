package research.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/** 
* @author shuwei 
* @version 创建时间：2018年10月29日 上午11:06:41 
* 类说明 
*/
public class RegexTest {
  
  public static String replace(String content, Map<String, String> varReplaceMap) {
    StringBuffer sb = new StringBuffer();
    Pattern pattern = Pattern.compile("\\$\\{([^\\}]+)\\}");
    Matcher matcher = pattern.matcher(content); 
    while(matcher.find()) {
      String key = matcher.group(1);
        String value = varReplaceMap.get(key);
        if (!StringUtils.isEmpty(value)) {
            matcher.appendReplacement(sb, value);
        } else {
            matcher.appendReplacement(sb, "null");
        }
    }
    matcher.appendTail(sb);
    return sb.toString();
  }
  public static void main(String[] args) {
    String content = "sh ${aaa} ${bbb} ${ccc}dsfsdfsdfsdf";
    Map<String, String> vs = new HashMap<>();
    vs.put("aaa", "AAA");
    // vs.put("bbb", "BBB");
    vs.put("ccc", "CCC");
    System.out.println(replace(content, vs));
  }
}
