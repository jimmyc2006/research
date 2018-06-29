package research.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author shuwei
 * @version 创建时间：2018年6月29日 上午9:56:19
 * 加载配置文件，以免用户名和密码配置导代码里上传到github
 */
public class PropertyUtil {
  
  public static Properties load(String absoluteFileName) throws Exception {
    // 文件真实路径
    Properties p = new Properties();
    try (InputStream is = new FileInputStream(absoluteFileName)) {
      p.load(is);
      return p;
    }
  }
}
