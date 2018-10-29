package research.wechat;

/**
 * Description: 微信提醒数据实体
 * All Rights Reserved.
 * @version 1.0  2017年12月1日 下午8:28:28  by 周峰（zhoufeng@iqianjin.com）创建
 */
public class InviteSuccTemplateData extends TemplateData{

	private Keynote keyword1;
	private Keynote keyword2;
	private Keynote keyword3;
	public Keynote getKeyword1() {
		return keyword1;
	}
	public void setKeyword1(Keynote keyword1) {
		this.keyword1 = keyword1;
	}
	public Keynote getKeyword2() {
		return keyword2;
	}
	public void setKeyword2(Keynote keyword2) {
		this.keyword2 = keyword2;
	}
	public Keynote getKeyword3() {
		return keyword3;
	}
	public void setKeyword3(Keynote keyword3) {
		this.keyword3 = keyword3;
	}
	
}
