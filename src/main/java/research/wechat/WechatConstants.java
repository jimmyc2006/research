package research.wechat;

/**
 * Description: 微信相关常量
 * All Rights Reserved.
 * @version 1.0  2017年12月1日 下午8:23:13  by 周峰（zhoufeng@iqianjin.com）创建
 */
public class WechatConstants {

    /**
     * 爱钱进微信服务器发模板消息地址
     */
	public static final String WX_TEMPLATE_MSG_URL = "http://wx2.iqianjin.com/iqjwxservice/SendTemplateMsgHandle";
    
    /**
     * 腾讯微信服务器接收回调地址
     */
    public static String USERINFO_OAUTH_URL="https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=CALL_URL&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
    
    /**
	 * 用作生成签名（该Token会和接口URL中包含的Token进行比对，从而验证安全性）
	 */
	public static final String ACCOUNT_TYPE_TOKEN = "iqjwxs";
	
	/**
	 * 应用ID
	 */
	public static final String ACCOUNT_TYPE_APPID = "wxa62965a49f8f715e";
	/**
	 * 应用密钥
	 */
	public static final String ACCOUNT_TYPE_APPSECRET="682a77c9dedc9d19aa87604605918559";
	/**
	 * 用作消息体加解密密钥。
	 */
	public static final String ACCOUNT_TYPE_ENCODINGAESKEY = "2HhXVwfBMvBSkEi8GNH3RX5GLLXgBYOhrvBtp3MFYUZ";
	
	/**
    * 爱钱进微信服务器发模板消息地址
    */
    public static final String WX_GET_TOKEN_URL = "http://wx2.iqianjin.com/iqjwxservice/GetAccessTokenHandle";
}
