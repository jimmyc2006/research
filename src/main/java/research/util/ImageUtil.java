package research.util;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageUtil {

  private static final Logger log = LoggerFactory.getLogger(ImageUtil.class);

  /**
   * 切割图片
   * 
   * @param suffix 图片格式
   * @param inputStream 图片输入流
   * @param cutRatio 切图比例 80%为0.8
   * @return
   * @throws Exception
   */
  private static byte[] cutImage(String suffix, InputStream inputStream, double cutRatio)
      throws Exception {
    // 根据后缀名获取图片格式
    ImageInputStream iis = ImageIO.createImageInputStream(inputStream);
    Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(suffix);
    ImageReader imagereader = (ImageReader) it.next();
    // 再通过ImageReader绑定 InputStream
    imagereader.setInput(iis);
    int oldWidth = imagereader.getWidth(0);
    int oldHeight = imagereader.getHeight(0);
    int newLeft = (int) (oldWidth * (cutRatio));
    int newWidth = oldWidth - newLeft;
    return cutImage2Bytes(imagereader, newLeft, 0, newWidth, oldHeight);
  }

  private static byte[] cutImage2Bytes(ImageReader imagereader, int x, int y, int w, int h)
      throws Exception {
    System.out.println(imagereader.getWidth(0) + ":" + imagereader.getHeight(0));
    // 设置感兴趣的源区域。
    ImageReadParam param = imagereader.getDefaultReadParam();
    param.setSourceRegion(new Rectangle(x, y, w, h));
    // 从 reader得到BufferImage
    BufferedImage bi = imagereader.read(0, param);
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      // 将BuffeerImage写出通过ImageIO
      // ImageIO.write(bi, "jpeg", new File(tarImage));
      ImageIO.write(bi, "jpeg", baos);
      return baos.toByteArray();
    }
  }

  // 最多重试1次
  private static final int maxAttemp = 3;

  public static byte[] readImageFromUrl(String picUrl) throws Exception {
    int time = 0;
    while (true) {
      time++;
      try (InputStream is = readImageFromUrl0(picUrl)) {
        return readInputStream(is);
      } catch (Exception e) {
        log.warn("图片读取错误,次数: " + time);
        if (time >= maxAttemp) {
          throw e;
        }
      }
    }
  }

  private static InputStream readImageFromUrl0(String imageUrl) throws IOException {
    URL url = new URL(imageUrl);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    // 超时响应时间为5秒
    conn.setConnectTimeout(5 * 1000);
    // 通过输入流获取图片数据
    return conn.getInputStream();
  }
  
  /**
   * 从url中获取图片，X轴左边切割，获取切割后的内容
   * 
   * @param imgUrl 图片url
   * @param ratio 左侧切割比率，如0.8表示切割横轴从坐标切割20%
   * @return 图片处理后的内容
   * @throws Exception
   */
  public static byte[] readAndLeftCutImageFromUrl(String imgUrl, double ratio) throws Exception {
    String suffix = imgUrl.substring(imgUrl.lastIndexOf(".") + 1);
    int time = 0;
    while (true) {
      time++;
      try (InputStream is = readImageFromUrl0(imgUrl)) {
        return cutImage(suffix, is, ratio);
      } catch (Exception e) {
        if (time >= maxAttemp) {
          throw e;
        }
      }
    }
  }

  private static byte[] readInputStream(InputStream inStream) throws Exception {
    try (ByteArrayOutputStream outStream = new ByteArrayOutputStream()) {
      // 创建一个Buffer字符串
      byte[] buffer = new byte[1024];
      // 每次读取的字符串长度，如果为-1，代表全部读取完毕
      int len = 0;
      // 使用一个输入流从buffer里把数据读取出来
      while ((len = inStream.read(buffer)) != -1) {
        // 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
        outStream.write(buffer, 0, len);
      }
      // 把outStream里的数据写入内存
      return outStream.toByteArray();
    }
  }

  // 需要切分的图片的最小高度
  public static final int needCutHeight = 5000;
  // 切分后的图片高度
  public static final int maxHeightPerImage = 2500;
  
  public static void main(String[] args) throws Exception {
    String image = "http://10.10.204.90:8080/pic/test_20180627/bbbig.jpg";
    List<byte[]> imageBytes = readAndLeftReplaceImageFromUrl(image, 0.17);
    for (int i = 0; i < imageBytes.size(); i++) {
      File f = new File("/Users/apple/Downloads/tmp/ttt_" + i + ".jpg");
      FileOutputStream fos = new FileOutputStream(f);
      fos.write(imageBytes.get(i));
      fos.close();
    }
  }

  private static boolean isEqualDifference(List<Integer> src) {
    if (src == null || src.size() < 3) {
      return false;
    }
    int cha = src.get(1) - src.get(0);
    if (cha == 1) {
      return false;
    }
    for (int index = 2; index < src.size(); index++) {
      if (src.get(index) - src.get(index - 1) != cha) {
        return false;
      }
    }
    return true;
  }

  public static List<byte[]> readAndLeftReplaceImageFromFile(String imgUrl, double ratio) throws Exception {
    return readAndLeftReplaceImageFromUrl(imgUrl, FileInputStream::new, ratio);
  }
  
  public static List<byte[]> readAndLeftReplaceImageFromUrl(String imgUrl, double ratio)
      throws Exception {
    return readAndLeftReplaceImageFromUrl(imgUrl, url -> readImageFromUrl0(url), ratio);
  }
  
  public static List<byte[]> readAndLeftReplaceImageFromUrl(String imgUrl, FunctionWithException<String, InputStream> func, double ratio)
      throws Exception {
    // 先使用文件名结尾作为图片类型，如果出问题，就自己识别一下文件类型
    String suffix = imgUrl.substring(imgUrl.lastIndexOf(".") + 1);
    int time = 0;
    while (true) {
      time++;
      try (InputStream is = func.apply(imgUrl)) {
        List<byte[]> result = featuresReplace(is, suffix, 0.17);
        if (result.size() == 0) {
          // 图片格式不对
          try (InputStream is2 = func.apply(imgUrl)) {
            log.warn("图片格式错误:" + suffix);
            suffix = getImageFormat(is2);
            log.warn("正确的格式是:" + suffix);
          }
          throw new RuntimeException();
        }
        return result;
      } catch (Exception e) {
        if (time >= maxAttemp) {
          throw e;
        }
      }
    }
  }
  
  /**
   * 
   * @param is 输入流
   * @param suffix 文件格式
   * @param ratio 图标替换比例
   * @param maxHeight 最大高度，超过这个高度将被切割
   * @return 图片列表，如果没有切割，那么只会有一个
   * @throws IOException
   */
  private static List<byte[]> featuresReplace(InputStream is, String suffix, double replaceRatio)
      throws IOException {
    BufferedImage bufferedimage = ImageIO.read(is);
    return cropAndReplace(bufferedimage, replaceRatio, suffix);
  }

  // 将一张图片分割，并且替换白色开头的前changeWidthLimit像素为白色
  private static List<byte[]> cropAndReplace(BufferedImage bufferedimage,
      double replaceRatio, String suffix) throws IOException {
    int width = bufferedimage.getWidth();
    int height = bufferedimage.getHeight();
    int changeWidthLimit = (int) (width * replaceRatio);
    if (needCutHeight > height) {
      return replace(bufferedimage, height, changeWidthLimit, suffix);
    }
    log.info("图片高度" + height + ", 切分高度" + maxHeightPerImage);
    List<byte[]> result = new ArrayList<>();
    // 先找出所有账单分割线的y坐标，并替换所有白色开头的像素行的前changeWidthLimit为白色
    // key是行的所有像素的和，value是行数列表
    Map<Long, List<Integer>> collectMap = new HashMap<>();
    for (int y = 0; y < height; y++) {
      long rowRGBSum = 0;
      for (int x = 0; x < width; x++) {
        rowRGBSum += bufferedimage.getRGB(x, y);
        // 替换
        if ((bufferedimage.getRGB(0, y) == -1) && x < changeWidthLimit) {
          bufferedimage.setRGB(x, y, -1);
        }
      }
      // 去掉全白色的行
      if (rowRGBSum != -1 * width) {
        List<Integer> lineNumbers = collectMap.get(rowRGBSum);
        if (lineNumbers == null) {
          lineNumbers = new ArrayList<>();
          collectMap.put(rowRGBSum, lineNumbers);
        }
        lineNumbers.add(y);
      }
    }
    // 保存分割线的y坐标
    List<Integer> delimiterYs = findDelimiters(collectMap);
    int lastY = 0;
    // 寻找切割线
    for (int index = 0; index < delimiterYs.size(); index++) {
      // 如果超过高度
      if (delimiterYs.get(index) - lastY > maxHeightPerImage) {
        BufferedImage subPic =
            cropImage(bufferedimage, 0, lastY, width, delimiterYs.get(index - 1));
        result.add(getBytesFromBufferedimage(subPic, suffix));
        lastY = delimiterYs.get(index - 1);
      }
    }
    // 处理最后一部分
    if (lastY != height) {
      BufferedImage subPic = cropImage(bufferedimage, 0, lastY, width, height);
      result.add(getBytesFromBufferedimage(subPic, suffix));
    }
    log.info("切分后的图片个数:" + result.size());
    return result;
  }
  
  private static List<Integer> findDelimiters(Map<Long, List<Integer>> collectMap) {
 // 正确的分割行的总和
    Long maxLengthSum = 0L;
    // 最大的list成员数
    int maxMembers = 0;
    for (Long key : collectMap.keySet()) {
      List<Integer> lineNumbers = collectMap.get(key);
      // 找长度最大的等差数列
      if (isEqualDifference(lineNumbers)) {
        if (lineNumbers.size() > maxMembers) {
          maxMembers = lineNumbers.size();
          maxLengthSum = key;
        }
      }
    }
    return collectMap.get(maxLengthSum);
  }

  // 替换图片
  private static List<byte[]> replace(BufferedImage bufferedimage, int height, int changeWidthLimit,
      String suffix) throws IOException {
    List<byte[]> result = new ArrayList<>();
    // 替换
    for (int y = 0; y < height; y++) {
      if (bufferedimage.getRGB(0, y) == -1) {
        for (int x = 0; x < changeWidthLimit; x++) {
          bufferedimage.setRGB(x, y, -1);
        }
      }
    }
    result.add(getBytesFromBufferedimage(bufferedimage, suffix));
    return result;
  }

  private static byte[] getBytesFromBufferedimage(BufferedImage bufferedimage, String suffix)
      throws IOException {
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      // 将BuffeerImage写出通过ImageIO
      ImageIO.write(bufferedimage, suffix, baos);
      return baos.toByteArray();
    }
  }

  public static String getImageFormat(Object obj) {
    try {
      ImageInputStream iis = ImageIO.createImageInputStream(obj);
      Iterator<ImageReader> iterator = ImageIO.getImageReaders(iis);
      while (iterator.hasNext()) {
        ImageReader reader = (ImageReader) iterator.next();
        return reader.getFormatName();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static BufferedImage cropImage(BufferedImage bufferedImage, int startX, int startY,
      int endX, int endY) {
    int width = bufferedImage.getWidth();
    int height = bufferedImage.getHeight();
    if (startX == -1) {
      startX = 0;
    }
    if (startY == -1) {
      startY = 0;
    }
    if (endX == -1) {
      endX = width - 1;
    }
    if (endY == -1) {
      endY = height - 1;
    }
    BufferedImage result = new BufferedImage(endX - startX, endY - startY, 4);
    for (int x = startX; x < endX; ++x) {
      for (int y = startY; y < endY; ++y) {
        int rgb = bufferedImage.getRGB(x, y);
        result.setRGB(x - startX, y - startY, rgb);
      }
    }
    return result;
  }
}
