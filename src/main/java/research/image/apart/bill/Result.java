package research.image.apart.bill;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/** 
* @author shuwei 
* @version 创建时间：2018年6月28日 下午8:40:31 
* 结果的格式
*/
public class Result {
  
  private long businessTime;
  private Collection<String> images;
  private Map<String, Object> metaData;
  private Map<String, ImageResult> ocrResult;
  
  public long getBusinessTime() {
    return businessTime;
  }
  public void setBusinessTime(long businessTime) {
    this.businessTime = businessTime;
  }
  
  public Collection<String> getImages() {
    return images;
  }
  public void setImages(Collection<String> images) {
    this.images = images;
  }
  public void setImages(List<String> images) {
    this.images = images;
  }
  public Map<String, Object> getMetaData() {
    return metaData;
  }
  public void setMetaData(Map<String, Object> metaData) {
    this.metaData = metaData;
  }
  public Map<String, ImageResult> getOcrResult() {
    return ocrResult;
  }
  public void setOcrResult(Map<String, ImageResult> ocrResult) {
    this.ocrResult = ocrResult;
  }
}
