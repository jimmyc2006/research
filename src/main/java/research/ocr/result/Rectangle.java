package research.ocr.result;

public class Rectangle {
  private String words;
  
  public Rectangle(BaiduRectangle baiduRectangle) {
    this.words = baiduRectangle.getWords();
  }
  public String getWords() {
    return words;
  }
  public void setWords(String words) {
    this.words = words;
  }
}
