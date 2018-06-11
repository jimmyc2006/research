package research;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.baidu.aip.ocr.AipOcr;

import research.ocr.result.ImageResult;

public class Sample {
  // 设置APPID/AK/SK
  public static final String APP_ID = "11293793";
  public static final String API_KEY = "cpo3bc0IZmVzCE6VzrqA5xSL";
  public static final String SECRET_KEY = "Ow0MGqY2sb9GuQi5oGCoDThhtU1EFbCt";

  public static void main(String[] args) throws IOException {
    // 初始化一个AipOcr
    AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

    // 可选：设置网络连接参数
    client.setConnectionTimeoutInMillis(2000);
    client.setSocketTimeoutInMillis(60000);

    // sample(client);
    // 可选：设置代理服务器地址, http和socketØ二选一，或者均不设置
    // client.setHttpProxy("proxy_host", proxy_port); // 设置http代理
    // client.setSocketProxy("proxy_host", proxy_port); // 设置socket代理

    // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
    // 也可以直接通过jvm启动参数设置此环境变量
    // System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");
    // BufferedWriter resultWriter =
    // new BufferedWriter(new FileWriter("/Users/apple/Downloads/result.txt"));
    String baseResuleDir = "/Users/apple/Downloads/pic_result/";
    String dir = "/Users/apple/Downloads/账单收集";
    File fDir = new File(dir);
    String[] filesName = fDir.list();
    System.out.println("文件数:" + filesName.length);
    // JSONObject res2 = client.basicGeneral(dir + "/" + "图片31_-13.50.jpg", new HashMap<String,
    // String>());
    // System.out.println(res2.toString(2));
    // parse(res2);
    // if (true) {
    // return;
    // }
    int i = 0;
    try {
      for (i = 0; i < filesName.length; i++) {
        if (".DS_Store".equalsIgnoreCase(filesName[i])) {
          continue;
        }
        System.out.println("开始分析" + i + "个文件:" + filesName[i]);
        BufferedWriter resultWriter = new BufferedWriter(new FileWriter(
            baseResuleDir + filesName[i].substring(0, filesName[i].lastIndexOf(".")) + ".txt"));
        long startTime = System.currentTimeMillis();
        JSONObject res = receipt(client, dir + "/" + filesName[i]);
            // client.basicGeneral(dir + "/" + filesName[i], new HashMap<String, String>());
        System.out.println("第" + i + "次调用耗时" + (System.currentTimeMillis() - startTime));
        // JSONObject res = client.webImageUrl(
        // "https://picabstract-preview-ftn.weiyun.com:8443/ftn_pic_abs_v2/36161691b940b41c8cc46ce5cf72eeb399a557d580aa86f62c0fdfef4d2aa4b838f2ce67aeb8c76cb9c9d0eea3c164b8?pictype=scale&from=30113&version=2.0.0.2&uin=67011332&fname=0dee86d7-7a19-4f5b-896d-dc2fe5309182.jpg&size=1024",
        // new HashMap<String, String>());
        // JSONObject res = sample(client, dir + "/" + filesName[i]);
        // System.out.println(res.toString(2));
        /*
         * resultWriter.write(INCLUD + filesName[i] + INCLUD + SPACE + INCLUD + parse(res) + INCLUD
         * + SPACE + INCLUD + getResultFromFileName(filesName[i]) + INCLUD);
         */
        // resultWriter.write("---------" + filesName[i] + "---------");
        ImageResult ir = new ImageResult(res);
        resultWriter.write(JSON.toJSONString(ir));
        resultWriter.close();
        Thread.sleep(500);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static String getResultFromFileName(String fileName) {
    return fileName.substring(fileName.indexOf("_") + 1, fileName.lastIndexOf("."));
  }

  public static void main1(String[] args) {
    // 初始化一个AipOcr
    AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

    // 可选：设置网络连接参数
    client.setConnectionTimeoutInMillis(2000);
    client.setSocketTimeoutInMillis(60000);

    String fileName = "/Users/apple/Downloads/pic/WechatIMG1 1.jpeg";
    System.out.println(general(client, fileName).toString(2));
  }

  // 测试同一个appid并发的情况
  public static void testConcurrent() {
    AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
    // 可选：设置网络连接参数
    client.setConnectionTimeoutInMillis(2000);
    client.setSocketTimeoutInMillis(60000);
    String fileName = "/Users/apple/Downloads/pic/WechatIMG1 1.jpeg";
    System.out.println(general(client, fileName));
    /*
    while (!Thread.interrupted()) {
      System.out.println(Thread.currentThread() + ":::" + general(client, fileName));
      try {
        Thread.sleep(300);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    */
  }

  // 测试一下同一个账号
  public static void mainCon(String[] args) {
    new Thread(() -> testConcurrent()).start();
    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    new Thread(() -> testConcurrent()).start();
  }

  private static final String INCLUD = "\"";
  private static final String SPACE = ",";

  public static JSONObject sample(AipOcr client, String image) {
    HashMap<String, String> options = new HashMap<String, String>();
    options.put("recognize_granularity", "big");
    options.put("detect_direction", "true");
    options.put("vertexes_location", "true");
    options.put("probability", "true");
    // 参数为本地图片路径
    // return client.webImageUrl("12", options);//.webImage(image, options);
    return client.accurateGeneral(image, options);
  }

  // 通用文字识别
  public static JSONObject basicGeneral(AipOcr client, String image) {
    HashMap<String, String> options = new HashMap<String, String>();
    options.put("language_type", "CHN_ENG");
    options.put("detect_direction", "true");
    options.put("detect_language", "true");
    options.put("probability", "true");
    return client.basicGeneral(image, options);
  }

  // 通用文字识别（高精度版）
  public static JSONObject basicAccurateGeneral(AipOcr client, String image) {
    HashMap<String, String> options = new HashMap<String, String>();
    options.put("detect_direction", "true");
    options.put("probability", "true");
    return client.basicAccurateGeneral(image, options);
  }

  // 通用文字识别（含位置信息版)
  public static JSONObject general(AipOcr client, String image) {
    HashMap<String, String> options = new HashMap<String, String>();
    options.put("recognize_granularity", "big");
    options.put("language_type", "CHN_ENG");
    options.put("detect_direction", "true");
    options.put("detect_language", "true");
    options.put("vertexes_location", "true");
    options.put("probability", "true");
    return client.general(image, options);
  }

  // 通用文字识别（含位置高精度版）
  public static JSONObject accurateGeneral(AipOcr client, String image) {
    HashMap<String, String> options = new HashMap<String, String>();
    options.put("recognize_granularity", "big");
    options.put("language_type", "CHN_ENG");
    options.put("detect_direction", "true");
    options.put("detect_language", "true");
    options.put("vertexes_location", "true");
    options.put("probability", "true");
    return client.accurateGeneral(image, options);
  }

  // 通用文字识别（含生僻字版)
  public static JSONObject enhancedGeneral(AipOcr client, String image) {
    // 传入可选参数调用接口
    HashMap<String, String> options = new HashMap<String, String>();
    options.put("language_type", "CHN_ENG");
    options.put("detect_direction", "true");
    options.put("detect_language", "true");
    options.put("probability", "true");
    return client.enhancedGeneral(image, options);
  }

  // 通用票据识别
  public static JSONObject receipt(AipOcr client, String image) {
    // 传入可选参数调用接口d
    HashMap<String, String> options = new HashMap<String, String>();
    options.put("recognize_granularity", "big");
    options.put("probability", "true");
    options.put("accuracy", "normal");
    options.put("detect_direction", "true");
    return client.receipt(image, options);
  }

  private static final String key1 = "words_result";
  private static final String key2 = "words";

  public static String parse(JSONObject res) {
    StringBuilder sb = new StringBuilder();
    JSONArray wordResults = res.getJSONArray(key1);
    for (int i = 0; i < wordResults.length(); i++) {
      JSONObject pAndw = (JSONObject) wordResults.get(i);
      String words = pAndw.getString(key2);
      sb.append(words);
    }
    return sb.toString();
  }

  public static String parseForMutiLine(JSONObject res) {
    StringBuilder sb = new StringBuilder();
    JSONArray wordResults = res.getJSONArray(key1);
    for (int i = 0; i < wordResults.length(); i++) {
      JSONObject pAndw = (JSONObject) wordResults.get(i);
      String words = pAndw.getString(key2);
      sb.append(words + "\n");
    }
    return sb.toString();
  }


}
