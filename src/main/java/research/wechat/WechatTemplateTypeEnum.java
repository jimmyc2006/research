package research.wechat;

/**
 * Description: 微信流水提醒模板
 * All Rights Reserved.
 * @version 1.0  2017年12月1日 下午2:12:21  by 周峰（zhoufeng@iqianjin.com）创建
 */
public enum WechatTemplateTypeEnum {
	/**
	 * 用户充值成功提醒
	 */
	RECHARGE_SUCC("TM00977", "_CX_LUarnfvlz7T0AceWYJVi8X-S_kEjvBTq-tR-Iuc","尊敬的%s先生（女士），您于%s成功充值爱钱进账户，账号：%s","当前您的账户可用余额为%s 元。有任何疑问请在“爱钱进官方服务平台”微信服务号中回复“在线客服”进行咨询。\r\n\r\n查看个人账户请点击详情。","http://m.iqianjin.com/userCenter/index?tokenVerified=false","用户充值成功提醒"),
	/**
	 * 用户充值失败提醒
	 */
	RECHARGE_FAIL("TM00978", "ARArli8zwHXvZt6pIeXruQdwZmwK-YM-mWbFIMjQLNg","","","","用户充值失败提醒"),
	/**
	 * 用户投资成功提醒
	 */
	INVEST_SUCC("OPENTM207421553", "sDCa00nl_UHpVHEkivfJO4tA6bxgIx8NjQSeRD7gwTY","尊敬的%s先生（女士），您已成功投资，账号：%s","\r\n如有任何疑问请在“爱钱进官方服务平台”微信服务号中回复“在线客服”进行咨询。\r\n\r\n点击详情查看爱钱进会员更多福利","http://m.iqianjin.com/vip/index.jsp","用户投资成功提醒"),
	/**
	 * 提现到账提示
	 */
	WITHDRAW_TO_ACC("OPENTM405644855", "yc_n9kkQ8MIsu3vGH8jwI23XbAV5ZU8xT_LYVPbeSCA","尊敬的%s先生（女士），您申请的提现金额已到账","当前您的爱钱进账户可用金额为%s 元。有任何疑问请在“爱钱进官方服务平台”微信服务号中回复“在线客服”进行咨询。  \r\n\r\n查看个人账户请点击详情。","http://m.iqianjin.com/userCenter/index?tokenVerified=false","提现到账提示"),
	/**
	 * 红包到期提示
	 */
	RED_BAG_EXPIRE("OPENTM400094219", "rvHUNSp8J4yvi_ho0lRUyC_DUJvYD4DQ_ySYVJYEZsw","尊敬的%s先生（女士），您的%s元红包即将到期，请尽快使用。","如有任何疑问请在“爱钱进官方服务平台”微信服务号中回复“在线客服”进行咨询。\r\n\r\n点击详情查看爱钱进会员更多福利","http://m.iqianjin.com/vip/index.jsp","红包到期提示"),
	/**
	 * 红包到账提醒
	 */
	RED_BAG_TO_ACC("OPENTM400265867", "ARArli8zwHXvZt6pIeXruQdwZmwK-YM-mWbFIMjQLNg","","","","红包到账提醒"),
	/**
	 * 加息券到期提示
	 */
	ADD_INTEREST_EXPIRE("OPENTM400094219", "rvHUNSp8J4yvi_ho0lRUyC_DUJvYD4DQ_ySYVJYEZsw","尊敬的%s先生（女士），您的加息券即将到期，请尽快使用。","如有任何疑问请在“爱钱进官方服务平台”微信服务号中回复“在线客服”进行咨询。\r\n\r\n点击详情查看爱钱进会员更多福利","http://m.iqianjin.com/vip/index.jsp","加息券到期提示"),
	/**
	 * 加息券到账提醒
	 */
	ADD_INTEREST_TO_ACC("OPENTM400265867", "ARArli8zwHXvZt6pIeXruQdwZmwK-YM-mWbFIMjQLNg","","","","加息券到账提醒"),
	/**
	 * 用户登录账户提示
	 */
	LOGIN("OPENTM405462916", "ARArli8zwHXvZt6pIeXruQdwZmwK-YM-mWbFIMjQLNg","尊敬的%s先生（女士），您已在%s成功登陆！","如果不是您本人操作请及时拨打：400-812-8808进行反馈。有任何疑问请在“爱钱进官方服务平台”微信服务号中回复“在线客服”进行咨询。\r\n\r\n点击详情查看爱钱进会员更多福利","http://m.iqianjin.com/vip/index.jsp","用户登录账户提示"),
	/**
	 * 用户绑定通知
	 */
	BIND_WECHAT("TM00240", "bEsbuXsZSEBB1lpM6FictjklDa4XjDl_iNBOmjuiZnA","尊敬的%s先生（女士），恭喜您账户绑定成功！","您可以使用下方微信菜单进行更多体验。如有任何疑问请在“爱钱进官方服务平台”微信服务号中回复“在线客服”进行咨询。\r\n\r\n点击详情直接在线咨询","http://www.sobot.com/chat/h5/index.html?sysNum=9e30de40563c4d95af9fe701d8e9519a&source=1","用户绑定通知"),
	
	/**
	 * 奖券发送提示
	 */
	COUPON_SEND("OPENTM207362815", "irip5_5MW85d_FFTtXAXGxPpKe-2Ujvs5T671V6vPlE","尊敬的%s先生（女士），恭喜您获得爱钱进奖券！","请及时使用以免过期\r\n\r\n点击详情查看爱钱进会员更多福利","http://m.iqianjin.com/userCenter/index","奖券发放提醒"),
	
	/**
	 * 活动时间提醒
	 */
	ACTIVITY_TIP_SEND("OPENTM207273205", "jhuPtYacQjvnIp6H3vYA4BxiWhtQZ_86ZTx3NKFTTAM","尊敬的%s先生（女士），活动即将开始！","请及时关注以免过期\r\n\r\n点击详情查看爱钱进活动详情","http://m.iqianjin.com/","活动提醒"),
	
	/**
	 * 活动参与成功通知
	 */
	ACTIVITY_PARTAKE_SUCC("OPENTM411651133", "y8WAM99EugFZT86H8NrzRRbvAExnX3Vr2nIz1wDQhYk","尊敬的%s先生（女士），活动参与成功！","请及时关注以免过期\r\n\r\n点击详情查看爱钱进活动详情","http://m.iqianjin.com/","活动参与成功提醒"),

	/**
	 * 邀请好友成功提醒
	 */
	INVITE_SUCC("OPENTM401095581", "XRSLHgau64na28sI9Iyayt_bK5vsh6nseU2TTi8n_60","尊敬的%s先生（女士），有用户通过您的邀请成功注册！","点击详情，查看邀请记录","http://m.iqianjin.com/invite/share.jsp","邀请好友成功提醒"),
	
	/**
	 * 警情信息提醒
	 */
	EARLY_WARNING_SUCC("OPENTM204992837", "bFe88JvWroSNTdzateftRgkTzHMUkPVz-i5uYeaxpEY","","","","警情信息提醒");
	
	/**
	 * 模板编号
	 */
	private final String templateNo;
	/**
	 * 描述
	 */
	private final String desc;
	
	/**
	 * 模板ID
	 */
	private final String templateId;
	
	private final String first;
	
	private final String remark;
	
	private final String url;

	WechatTemplateTypeEnum(String templateNo,String templateId,String first,String remark,String url,String desc) {
		this.templateNo = templateNo;
		this.templateId=templateId;
		this.first=first;
		this.remark=remark;
		this.url=url;
		this.desc = desc;
	}

	public String getTemplateNo() {
		return templateNo;
	}


	public String getDesc() {
		return desc;
	}

	public String getTemplateId() {
		return templateId;
	}

	public String getFirst() {
		return first;
	}

	public String getRemark() {
		return remark;
	}

	public String getUrl() {
		return url;
	}
	
}
