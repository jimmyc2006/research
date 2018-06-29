package research.bean;

import java.awt.Point;

/** 
* @author shuwei 
* @version 创建时间：2018年6月25日 下午5:34:43 
* 类说明 
*/
public class Mask {
  private Rect rect;
  private Point point1;
  private Point point2;
  private Point point3;
  private Point point4;
  private String text;
  
  public Point getPoint1() {
    return point1;
  }
  public void setPoint1(Point point1) {
    this.point1 = point1;
  }
  public Point getPoint2() {
    return point2;
  }
  public void setPoint2(Point point2) {
    this.point2 = point2;
  }
  public Point getPoint3() {
    return point3;
  }
  public void setPoint3(Point point3) {
    this.point3 = point3;
  }
  public Point getPoint4() {
    return point4;
  }
  public void setPoint4(Point point4) {
    this.point4 = point4;
  }
  public String getText() {
    return text;
  }
  public void setText(String text) {
    this.text = text;
  }
  public Rect getRect() {
    return rect;
  }
  public void setRect(Rect rect) {
    this.rect = rect;
  }
}
