package research.wechat;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * @author shuwei
 * @version 创建时间：2018年8月22日 下午4:31:35 类说明
 */
public class Call {
//  public void sendKpiEarlyWarning(String context, List<Map<String, Object>> weChatList) {
//    // 组装template
//    InviteSuccTemplateData istd = new InviteSuccTemplateData();
//    // 声明和设置keyword1
//    Keynote keyword1 = new Keynote();
//    keyword1.setValue(DateUtils.getString(DateUtils.getLastDay(new Date())));
//    // 声明和设置remark
//    Keynote remark = new Keynote();
//    remark.setValue(context.toString());
//    // 设置templateData
//    istd.setKeyword1(keyword1);
//    istd.setRemark(remark);
//    // 微信发送的http内容对象
//    TemplateMsg templateMsg = new TemplateMsg();
//    templateMsg.setTemplate_id(WechatTemplateTypeEnum.EARLY_WARNING_SUCC.getTemplateId());
//    templateMsg.setData(istd);
//    // 循环发送微信
//    for (Map<String, Object> wechatInfo : weChatList) {
//      if (wechatInfo.get("openId") != null) {
//        templateMsg.setTouser(wechatInfo.get("openId").toString());
//        String jsonContent = JSONObject.toJSONString(templateMsg);
//        HttpUtils.sendPostMethodJson(WechatConstants.WX_TEMPLATE_MSG_URL, jsonContent);
//      }
//    }
//  }
}
