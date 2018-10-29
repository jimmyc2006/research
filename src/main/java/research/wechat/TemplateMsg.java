package research.wechat;

import java.io.Serializable;

/**
 * Description: 微信发送的http内容对象
 * All Rights Reserved.
 * @version 1.0  2017年12月1日 下午8:26:29  by 周峰（zhoufeng@iqianjin.com）创建
 */
public class TemplateMsg implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String touser;
	//为符合微信接口文档规范使用"_"
    private String template_id;

    private String url;

    private TemplateData data;

    public void setTouser(String touser){
        this.touser = touser;
    }
    public String getTouser(){
        return this.touser;
    }
   
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return this.url;
    }
    public void setData(TemplateData data){
        this.data = data;
    }
    public TemplateData getData(){
        return this.data;
    }
}
