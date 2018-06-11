package research.ocr.result;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Rectangle {
  private String words;
  private List<Point> vertexesLocation;
  
  public Rectangle(BaiduRectangle baiduRectangle) {
    this.words = baiduRectangle.getWords();
    BaiduLocation location = baiduRectangle.getLocation();
    vertexesLocation = new ArrayList<>();
    vertexesLocation.add(new Point(location.getLeft(), location.getTop()));
    vertexesLocation.add(new Point(location.getLeft() + location.getWidth(),
        location.getTop() - location.getHeight()));
  }
  public String getWords() {
    return words;
  }
  public void setWords(String words) {
    this.words = words;
  }
  public List<Point> getVertexesLocation() {
    return vertexesLocation;
  }
  public void setVertexesLocation(List<Point> vertexesLocation) {
    this.vertexesLocation = vertexesLocation;
  }
}
