package research.ocr.result;

public class Duration {
  private long start;

  public Duration(long start) {
    this.start = start;
  }

  public void setStart(long start) {
    this.start = start;
  }

  public long getDuration(long end) {
    long du = end - start;
    start = end;
    return du;
  }
}
