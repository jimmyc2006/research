package research.image.apart.bill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.aip.ocr.AipOcr;

import research.util.ImageUtil;

/** 
* @author shuwei 
* @version 创建时间：2018年6月28日 下午6:42:35 
* 类说明 
*/
public class BaiduOcrServiceImpl implements OcrService {
  private static Logger log = LoggerFactory.getLogger(BaiduOcrServiceImpl.class);
  
  private AipOcr aipOcr;
  private HashMap<String, String> options;
  private Executor executor;
  
  public BaiduOcrServiceImpl(String appId, String appKey, String secretKey,
      int connectionTimeoutInMillis, int socketTimeoutInMillis) {
    this.aipOcr = new AipOcr(appId, appKey, secretKey);
    aipOcr.setConnectionTimeoutInMillis(connectionTimeoutInMillis);
    aipOcr.setSocketTimeoutInMillis(socketTimeoutInMillis);
    options = new HashMap<String, String>();
    options.put("vertexes_location", "true");
    executor = new ThreadPoolExecutor(9, 9, 60,
        TimeUnit.SECONDS,
        new LinkedBlockingQueue<Runnable>());
  }
  
  private JSONObject call(byte[] image) {
    return this.aipOcr.accurateGeneral(image, options);
  }
  
  @Override
  public ImageResult ocrAnalize(String image) {
    // 如果图片太大，则需要切割,否则百度sdk无法识别
    List<byte[]> imageBytes;
    try {
      imageBytes = ImageUtil.readAndLeftReplaceImageFromFile(image, 0.17);
      // 对所有图片进行解析，并按照格式生成结果
      List<JSONObject> objs = new ArrayList<>();
      if (imageBytes.size() == 1) {
        objs.add(call(imageBytes.get(0)));
      } else {
        // 处理多张
        objs = callBaiduAsync(imageBytes);
      }
      return new ImageResult(objs);
    } catch (Exception e1) {
      log.error("", e1);
    }
    return null;
  }
  
  private List<JSONObject> callBaiduAsync(List<byte[]> pics) {
    int size = pics.size();
    List<JSONObject> finalResult = new ArrayList<>();
    // 多线程并发收集结果
    Map<Integer, JSONObject> indexResult = new ConcurrentHashMap<>();
    CountDownLatch cdl = new CountDownLatch(size);
    for (int i = 0; i< size; i++) {
      int index = i;
      this.executor.execute(() -> {
        indexResult.put(index, call(pics.get(index)));
        cdl.countDown();
      });
    }
    try {
      cdl.await();
      for (int i = 0; i < size; i++) {
        finalResult.add(indexResult.get(i));
      }
    } catch (InterruptedException e) {
      log.error("", e);
    }
    return finalResult;
  }
  
}
