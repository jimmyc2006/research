package research.ocr.result;

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

  public ImageResult(JSONObject baiduJSONObject) {
    try {
      this.logId = Long.toString(baiduJSONObject.getLong("log_id"));
      this.wordsResultNum = baiduJSONObject.getInt("words_result_num");
      wordsResult = new ArrayList<>();
      JSONArray jsa = baiduJSONObject.getJSONArray("words_result");
      for (int i = 0; i < jsa.length(); i++) {
        BaiduRectangle bdRec =  JSON.parseObject(jsa.getJSONObject(i).toString(), BaiduRectangle.class);
        wordsResult.add(new Rectangle(bdRec));
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
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
