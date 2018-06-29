package research.image.apart.bill;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;

public class ImageResult {
  private String logId;
  private List<Rectangle> wordsResult;
  private int wordsResultNum;

  public ImageResult(List<JSONObject> baiduJSONObjects) throws JSONException {
    // logid取第一张的logid
    this.logId = Long.toString(baiduJSONObjects.get(0).getLong("log_id"));
    int num = 0;
    wordsResult = new ArrayList<>();
    for (JSONObject ele : baiduJSONObjects) {
      num += ele.getInt("words_result_num");
      JSONArray jsa = ele.getJSONArray("words_result");
      for (int i = 0; i < jsa.length(); i++) {
        BaiduRectangle bdRec =  JSON.parseObject(jsa.getJSONObject(i).toString(), BaiduRectangle.class);
        wordsResult.add(new Rectangle(bdRec));
      }
    }
    this.wordsResultNum = num;
  }

  public String getLogId() {
    return logId;
  }

  public void setLogId(String logId) {
    this.logId = logId;
  }

  public List<Rectangle> getWordsResult() {
    return wordsResult;
  }

  public void setWordsResult(List<Rectangle> wordsResult) {
    this.wordsResult = wordsResult;
  }

  public int getWordsResultNum() {
    return wordsResultNum;
  }

  public void setWordsResultNum(int wordsResultNum) {
    this.wordsResultNum = wordsResultNum;
  }
}
