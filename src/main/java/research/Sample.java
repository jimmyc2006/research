package research;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.baidu.aip.ocr.AipOcr;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import research.bean.Mask;
import research.bean.Rect;
import research.ocr.result.ImageResult;
import research.ocr.result.Rectangle;

public class Sample {
  // 设置APPID/AK/SK
  public static final String APP_ID = "11333409";
  public static final String API_KEY = "ia4ewQD1V4vEf30tEQQy7SW0";
  public static final String SECRET_KEY = "rZm6KimnowEefp9tK7ooPODtxsc5gV0n";

  public static void main0(String[] args) throws IOException {
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

  public static void mainSingleTest(String[] args) {
    // 初始化一个AipOcr
    AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

    // 可选：设置网络连接参数
    client.setConnectionTimeoutInMillis(2000);
    client.setSocketTimeoutInMillis(60000);

    String fileName = "/Users/apple/Downloads/tmp/image3/20180612_157/01.png";
    JSONObject res = accurateGeneral(client, fileName);
    System.out.println(res.toString(2));
    // System.out.println(JSON.toJSONString(resultChange(res)));
  }

  public static Map<String, Mask> resultChange(JSONObject jSONObjectBD) {
    List<Mask> result = new ArrayList<>();
    JSONArray wordsResult = jSONObjectBD.getJSONArray("words_result");
    for (int i = 0; i < wordsResult.length(); i++) {
      JSONObject o = (JSONObject) wordsResult.get(i);
      String text = o.getString("words");
      if (text != null && text.trim().length() > 0) {
        JSONObject location = o.getJSONObject("location");
        Rect rect = new Rect(location.getInt("left"), location.getInt("top"),
            location.getInt("width"), location.getInt("height"));
        Mask m = new Mask();
        m.setRect(rect);
        m.setText(text);
        JSONArray points = o.getJSONArray("vertexes_location");
        for (int j = 0; j < points.length(); j++) {
          JSONObject point = points.getJSONObject(j);
          switch(j) {
            case 0:
              m.setPoint1(new Point(point.getInt("x"), point.getInt("y")));
              break;
            case 1:
              m.setPoint2(new Point(point.getInt("x"), point.getInt("y")));
              break;
            case 2:
              m.setPoint3(new Point(point.getInt("x"), point.getInt("y")));
              break;
            case 3:
              m.setPoint4(new Point(point.getInt("x"), point.getInt("y")));
              break;
            default :
              System.out.println("出现错误索引" + j);
          }
        }
        result.add(m);
      }
    }
    Map<String, Mask> finalResult = new TreeMap<>();
    for (int i = 0; i < result.size(); i++) {
      finalResult.put("mask_" + (i + 1), result.get(i));
    }
    return finalResult;
  }
  // 测试同一个appid并发的情况
  public static void testConcurrent(String image) {
    AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
    // 可选：设置网络连接参数
    client.setConnectionTimeoutInMillis(10000);
    client.setSocketTimeoutInMillis(60000);
    System.out.println(basicAccurateGeneral(client, image));
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
    return client.basicAccurateGeneral(image, options);
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

  // 区分阿里和微信的图片
  public static void mainApart(String[] args) {
//    LoggerContext loggerContext= (LoggerContext) LoggerFactory.getILoggerFactory();
//    ch.qos.logback.classic.Logger logger=loggerContext.getLogger("root");  
//    logger.setLevel(Level.toLevel("WARN"));
    String dir = "/Users/apple/Downloads/tmp/image3/20180612_253/";
    apartWechatAli(dir);
  }
  
  public static void apartWechatAli(String dir) {
    List<String> wechat = new ArrayList<>();
    List<String> ali = new ArrayList<>();
    File fDir = new File(dir);
    String[] fileNames = fDir.list();
    AipOcr ao = getAipOcr();
    for (int i = 0; i < fileNames.length; i++) {
      String name = fileNames[i];
      if (name.startsWith(".")) {
        continue;
      }
      System.out.println(i + "/" +  fileNames.length + " : " + name);
      JSONObject j = basicAccurateGeneral(ao, dir + name);
      ImageResult ir = new ImageResult(j);
      boolean we = false;
      for (Rectangle r : ir.getWordsResult()) {
        if(r.getWords().contains("交易记录")) {
          we = true;
          break;
        }
        if (r.getWords().contains("筛选")  || r.getWords().contains("金额")) {
          we = false;
          break;
        }
      }
      if (!we) {
        ali.add(name);
        System.out.println("ali: " + name);
      } else {
        wechat.add(name);
        System.out.println("we: " + name);
      }
    }
    StringBuilder sb = new StringBuilder();
    for (String n : ali) {
      sb.append("\"" + n + "\",");
    }
    System.out.println("ali: " + sb.toString());
    sb = new StringBuilder();
    for (String n : wechat) {
      sb.append("\"" + n + "\",");
    }
    System.out.println("wec: " + sb.toString());
  }
  
  private static AipOcr getAipOcr(){
    AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
    // 可选：设置网络连接参数
    client.setConnectionTimeoutInMillis(2000);
    client.setSocketTimeoutInMillis(60000);
    return client;
  }
  
  public static void main(String[] args) {
    String dir = "/Users/apple/Downloads/tmp/image3_mine/WECHAT";
    File fileDir = new File(dir);
    String[] fileNames = fileDir.list();
    Arrays.sort(fileNames);
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < fileNames.length; i++) {
      if (fileNames[i].startsWith(".")) {
        continue;
      }
      sb.append("\"" + fileNames[i] + "\",");
      if((i + 1) % 6 == 0) {
        System.out.println(sb);
        sb = new StringBuilder();
      }
    }
    System.out.println(sb.toString());
  }
  
}
