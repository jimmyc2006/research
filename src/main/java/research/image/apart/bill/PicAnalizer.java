package research.image.apart.bill;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.alibaba.fastjson.JSON;

import research.util.PropertyUtil;

/**
 * @author shuwei
 * @version 创建时间：2018年6月28日 下午5:02:39 类说明
 */
public class PicAnalizer {
  private int maxResultSize;
  private OcrService ocrService;

  public void analize(String dir, String resultDir) throws IOException {
    dir = dir.endsWith("/") ? dir : dir + "/";
    File fDir = new File(dir);
    String[] files = fDir.list((f, name) -> name.matches(SUPPORT));
    Map<String, ImageResult> aliMap = new HashMap<>();
    Map<String, ImageResult> wecharMap = new HashMap<>();
    for (String fileName : files) {
      ImageResult ir = ocrService.ocrAnalize(dir + fileName);
      if (isAlipay(ir)) {
        aliMap.put(fileName, ir);
        if (aliMap.size() >= maxResultSize) {
          writeResult(ALIPAY, aliMap, resultDir + "/" + fileName.substring(0, fileName.lastIndexOf('.')) + ".txt");
          aliMap.clear();
        }
      } else {
        wecharMap.put(fileName, ir);
        if (wecharMap.size() >= maxResultSize) {
          writeResult(WECHAT, wecharMap, resultDir + "/" + fileName.substring(0, fileName.lastIndexOf('.')) + ".txt");
          wecharMap.clear();
        }
      }
    }
    writeResult(ALIPAY, aliMap, resultDir + "/" + "remainAli.txt");
    writeResult(WECHAT, wecharMap, resultDir + "/" + "remainWec.txt");
  }
  
  private static final String ALIPAY = "OCR_ALIPAY";
  private static final String WECHAT = "OCR_WECHAT";
  private void writeResult(String type, Map<String, ImageResult> ocrResult, String resultAbsoluteFileName) throws IOException {
    if (ocrResult == null || ocrResult.size() == 0) {
      return;
    }
    Result r = new Result();
    r.setBusinessTime(System.currentTimeMillis());
    r.setImages(ocrResult.keySet());
    Map<String, Object> metaData = new HashMap<>();
    metaData.put("accountId", 1);
    metaData.put("taskId", "1");
    metaData.put("transSyncType", type);
    metaData.put("userId", 123);
    r.setMetaData(metaData);
    r.setOcrResult(ocrResult);
    FileWriter fw = null;
    try {
      fw = new FileWriter(resultAbsoluteFileName);
      fw.write(JSON.toJSONString(r));
    } finally {
      try {
        fw.close();
      } catch (Exception e) {
        // pass
      }
    }
  }

  public void testApart(String absoluteFileName, boolean isAlipay) {
    ImageResult ir = ocrService.ocrAnalize(absoluteFileName);
    if (isAlipay) {
      if (!this.isAlipay(ir)) {
        System.out.println(absoluteFileName + "不应该是alipay：" + JSON.toJSONString(ir));
      }
    } else {
      if (this.isAlipay(ir)) {
        System.out.println(absoluteFileName + "应该是alipay:" + JSON.toJSONString(ir));
      }
    }
  }

  public boolean isAlipay(ImageResult imageResult) {
    String regex = "[0-9]{1,2}月[0-9]{1,2}日.*";
    int matchTimes = 0;
    for (Rectangle r : imageResult.getWordsResult()) {
      String word = r.getWords();
      if (word.contains("交易记录")) {
        return false;
      }
      if (word.contains("筛选") || word.contains("金额")) {
        return true;
      }
      if (word.matches(regex)) {
        matchTimes++;
      }
    }
    if (matchTimes > 2) {
      return false;
    }
    return true;
  }

  public void setOcrService(OcrService ocrService) {
    this.ocrService = ocrService;
  }

  public void setMaxResultSize(int maxResultSize) {
    this.maxResultSize = maxResultSize;
  }

  public static void main(String[] args) throws Exception {
    // 并不需要每次都见都运行的测试，就不使用junit
    // String alipayDir = "/Users/apple/Downloads/data/image3_mine/ALIPAY/";
    // testDir(alipayDir, true);
    // String wechartDir = "/Users/apple/Downloads/data/image3_mine/WECHAT";
    // testDir(wechartDir, false);
    
    // 整个流程
    analizeFacade("/Users/apple/Downloads/data/image3_mine/aaa", "/Users/apple/Downloads/data/image3_mine/aaaResult");
  }
  
  private static String SUPPORT = "(?i).+[.](jpg|png|bmp)";
  
  private static void analizeFacade(String dir, String resultDir) throws Exception {
    dir = dir.endsWith("/") ? dir : dir + "/";
    resultDir = resultDir.endsWith("/") ? resultDir : resultDir + "/";
    PicAnalizer pa = PicAnalizer.getInstance();
    pa.analize(dir, resultDir);
  }

  private static void testDir(String dir, boolean isAlipay) throws Exception {
    dir = dir.endsWith("/") ? dir : dir + "/";
    PicAnalizer pa = PicAnalizer.getInstance();
    File fDir = new File(dir);
    String[] fileNames = fDir.list((f, n) -> n.matches(SUPPORT));
    for (String fileName : fileNames) {
      String absoluteFileName = dir + fileName;
      pa.testApart(absoluteFileName, isAlipay);
    }
  }
  private static PicAnalizer getInstance() throws Exception {
    PicAnalizer pa = new PicAnalizer();
    Properties p = PropertyUtil.load("/Users/apple/Documents/config/ocr.config");
    pa.setOcrService(new BaiduOcrServiceImpl(p.getProperty("appId"), p.getProperty("appKey"),
        p.getProperty("secretKey"), Integer.parseInt(p.getProperty("connectionTimeoutInMillis")),
        Integer.parseInt(p.getProperty("socketTimeoutInMillis"))));
    pa.setMaxResultSize(9);
    return pa;
  }
}
