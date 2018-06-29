package research.image.apart.bill;

import java.awt.Point;
import java.util.List;

public class BaiduRectangle {
  private String words;
  private BaiduLocation location;
  private List<Point> vertexes_location;
  
  public String getWords() {
    return words;
  }
  public void setWords(String words) {
    this.words = words;
  }
  public BaiduLocation getLocation() {
    return location;
  }
  public void setLocation(BaiduLocation location) {
    this.location = location;
  }
  public List<Point> getVertexes_location() {
    return vertexes_location;
  }
  public void setVertexes_location(List<Point> vertexes_location) {
    this.vertexes_location = vertexes_location;
  }
}
