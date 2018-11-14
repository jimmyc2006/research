package research.util.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author shuwei
 * @version 创建时间：2018年11月8日 上午11:01:33 类说明
 */
public class HttpUtil {

  private static final Logger _log = LoggerFactory.getLogger(HttpUtil.class);

  public static <T> T get(String url, Class<T> clazz, int maxRetrytimes, Object... uriVariables) {
    if (maxRetrytimes < 0) {
      maxRetrytimes = 0;
    }
    int times = 0;
    while (true) {
      RestTemplate restTemplate = new RestTemplate();
      try {
        return restTemplate.getForObject(url, clazz, uriVariables);
      } catch (Exception e) {
        times++;
        if (times <= maxRetrytimes) {
          _log.error("调用登录服务器时候报错", e);
          _log.info("重试[" + url + "], params [" + uriVariables + "]");
        } else {
          throw new RuntimeException("调用登录服务器出错");
        }
      }
    }
  }

  public static <T> T post(String url, Class<T> resultClazz, int maxRetrytimes,
      LinkedMultiValueMap<String, ?> variables) {
    if (maxRetrytimes < 0) {
      maxRetrytimes = 0;
    }
    int times = 0;
    while (true) {
      RestTemplate restTemplate = new RestTemplate();
      try {
        return restTemplate.postForObject(url, variables, resultClazz);
      } catch (Exception e) {
        times++;
        if (times <= maxRetrytimes) {
          _log.error("调用告警接口错误", e);
          _log.info("重试[" + url + "], params [" + variables + "]");
        } else {
          throw new RuntimeException("调用登录服务器出错");
        }
      }
    }
  }

  public static void main(String[] args) {
    LinkedMultiValueMap<String, Object> parmas = new LinkedMultiValueMap<>();
    parmas.add("content", "ddddddd 哈哈哈");
    parmas.add("mobile", ""); // 必传，但是
    parmas.add("type", 0);
    parmas.add("ucode", "dispatch");
    parmas.add("mobiles", "15110286332");
    _log.info("开始调用告警系统" + parmas);
    try {
      String res =
          HttpUtil.post("http://10.10.204.90:8380/sundry/sms/send", String.class, 3, parmas);
      _log.info("调用告警系统结果" + res);
    } catch (Exception e) {
      // 调用告警接口错误,不影响流程,不特殊处理
      _log.error("", e);
    }
  }
}
