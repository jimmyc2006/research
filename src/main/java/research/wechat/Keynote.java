package research.wechat;

/**
 * Description: 模板消息json值封装 All Rights Reserved.
 * 
 * @version 1.0 2017年12月1日 下午8:29:38 by 周峰（zhoufeng@iqianjin.com）创建
 */
public class Keynote {

	private String value;

	private String color;

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getColor() {
		return this.color;
	}
}
