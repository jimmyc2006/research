package research.image.apart.bill;

import java.awt.Point;
import java.util.List;

public class Rectangle {
  private String words;
  // @JSONField(serialize=false)
  private List<Point> vertexesLocation;
  
  public Rectangle(BaiduRectangle baiduRectangle) {
    this.words = baiduRectangle.getWords();
    vertexesLocation = baiduRectangle.getVertexes_location();
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
