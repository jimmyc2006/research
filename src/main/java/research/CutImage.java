package research;

import java.awt.Component;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import research.ocr.result.Duration;

// https://www.cnblogs.com/w821759016/p/6213933.html
public class CutImage {
  private static Logger log = LoggerFactory.getLogger(CutImage.class);
  private static String srcImage = "/Users/apple/Downloads/账单收集/图片2.jpg";
  private static String tarImage = "/Users/apple/Downloads/result/aaa.jpg";
  private static double cutRatio = 0.18;

  private static String DEFAULT_PREVFIX = "thumb_";
  private static Boolean DEFAULT_FORCE = false;// 建议该值为false

  public static void main0(String[] args) throws IOException { // 1080 * 2399
    File f = new File("/Users/apple/Downloads/result/pic.jpeg");
    File newfile = new File("/Users/apple/Downloads/result/aaa.jpeg");
    BufferedImage bufferedimage = ImageIO.read(f);
    int oldWidth = bufferedimage.getWidth();
    int oldHeight = bufferedimage.getHeight();
    int cutWidth = (int) (oldWidth * 0.2);
    cropImage(bufferedimage, 0 + cutWidth / 2, 0, oldWidth - cutWidth / 2, oldHeight);
    ImageIO.write(bufferedimage, "jpeg", newfile);
    printImageInfo("/Users/apple/Downloads/result/aaa.jpeg");
  }

  public static void main1(String[] args) {
    String readFormats[] = ImageIO.getReaderFormatNames();
    String writeFormats[] = ImageIO.getWriterFormatNames();
    System.out.println("Readers:" + Arrays.asList(readFormats));
    System.out.println("Writers:" + Arrays.asList(writeFormats));
  }

  private static void printImageInfo(String name) {
    File f = new File(name);
    try {
      Image img = ImageIO.read(f);
      int width = img.getWidth(null);
      int height = img.getHeight(null);
      System.out.println(width + ":" + height);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * 裁剪图片方法
   * 
   * @param bufferedImage 图像源
   * @param startX 裁剪开始x坐标
   * @param startY 裁剪开始y坐标
   * @param endX 裁剪结束x坐标
   * @param endY 裁剪结束y坐标
   * @return
   */
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

  public static void cutImage2File(ImageReader imagereader, int x, int y, int w, int h)
      throws Exception {
    System.out.println(imagereader.getWidth(0) + ":" + imagereader.getHeight(0));
    // 设置感兴趣的源区域。
    ImageReadParam param = imagereader.getDefaultReadParam();
    param.setSourceRegion(new Rectangle(x, y, w, h));
    // 从 reader得到BufferImage
    BufferedImage bi = imagereader.read(0, param);

    // 将BuffeerImage写出通过ImageIO
    ImageIO.write(bi, "jpeg", new File(tarImage));
  }

  public static byte[] cutImage2Bytes(ImageReader imagereader, int x, int y, int w, int h)
      throws Exception {
    System.out.println(imagereader.getWidth(0) + ":" + imagereader.getHeight(0));
    // 设置感兴趣的源区域。
    ImageReadParam param = imagereader.getDefaultReadParam();
    param.setSourceRegion(new Rectangle(x, y, w, h));
    // 从 reader得到BufferImage
    BufferedImage bi = imagereader.read(0, param);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    // 将BuffeerImage写出通过ImageIO
    // ImageIO.write(bi, "jpeg", new File(tarImage));
    ImageIO.write(bi, "jpeg", baos);
    return baos.toByteArray();
  }

  public static void cutImage(String srcImage) throws Exception {
    String suffix = srcImage.substring(srcImage.lastIndexOf(".") + 1);
    ImageInputStream iis = ImageIO.createImageInputStream(new FileInputStream(srcImage));
    Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(suffix);
    ImageReader imagereader = (ImageReader) it.next();
    // 再通过ImageReader绑定 InputStream
    imagereader.setInput(iis);
    int oldWidth = imagereader.getWidth(0);
    int oldHeight = imagereader.getHeight(0);
    int newLeft = (int) (oldWidth * (cutRatio));
    int newWidth = oldWidth - newLeft;
    byte[] result = cutImage2Bytes(imagereader, newLeft, 0, newWidth, oldHeight);
    System.out.println(result.length);
  }

  public static void main2(String[] args) throws Exception {
    Duration d = new Duration(System.currentTimeMillis());
    cutImage(srcImage);
    System.out.println(d.getDuration(System.currentTimeMillis()));
  }

  // 将某个目录下的图片，左边替换成白色
  public static void mainChange(String[] args) throws IOException {
    String srcDir = "/Users/apple/Downloads/pic_coll/";
    String dstDir = "/Users/apple/Downloads/pic_coll_result/";
    changeByDir(srcDir, dstDir);
  }

  public static void changeByDir(String sourceDir, String dstDir) throws IOException {
    File dir = new File(sourceDir);
    for (File f : dir.listFiles()) {
      if (!f.getName().startsWith(".")) {
        String dstFileName = dstDir + f.getName();
        featuresReplace(f, dstFileName);
      }
    }
  }

  /**
   * 使用BufferedImage遍历图片，并将行首是白色，且在左边17%的元素替换为白色
   * 
   * @throws IOException
   */
  public static void featuresReplace(File srcImageFile, String dstFileName) throws IOException {
    System.out.println(dstFileName);
    String suffix = dstFileName.substring(dstFileName.lastIndexOf(".") + 1);
    BufferedImage bufferedimage = ImageIO.read(srcImageFile);
    int width = bufferedimage.getWidth();
    int height = bufferedimage.getHeight();
    int changeWidthLimit = (int) (width * 0.17);
    System.out.println(width + ":" + height);
    for (int y = 0; y < height; y++) {
      if (bufferedimage.getRGB(0, y) == -1) {
        for (int x = 0; x < changeWidthLimit; x++) {
          bufferedimage.setRGB(x, y, -1);
        }
      }
    }
    ImageIO.write(bufferedimage, suffix, new File(dstFileName));
  }

  // 切图片，如果图片的尺寸超大，就将图片切分成多张
  public static void main(String[] args) throws IOException {
    String image = "/Users/apple/Downloads/tmp/bbbig.jpg";
    BufferedImage bufferedimage = ImageIO.read(new File(image));
    int width = bufferedimage.getWidth();
    int height = bufferedimage.getHeight();
    System.out.println(width);
    System.out.println(height);
  }
  
  public static void featuresReplaceAndCrop(File srcImageFile, String dstFileName) throws IOException {
    System.out.println(dstFileName);
    String suffix = dstFileName.substring(dstFileName.lastIndexOf(".") + 1);
    BufferedImage bufferedimage = ImageIO.read(srcImageFile);
    int width = bufferedimage.getWidth();
    int height = bufferedimage.getHeight();
    int changeWidthLimit = (int) (width * 0.17);
    System.out.println(width + ":" + height);
    for (int y = 0; y < height; y++) {
      if (bufferedimage.getRGB(0, y) == -1) {
        for (int x = 0; x < changeWidthLimit; x++) {
          bufferedimage.setRGB(x, y, -1);
        }
      }
    }
    ImageIO.write(bufferedimage, suffix, new File(dstFileName));
  }
}
