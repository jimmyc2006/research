package research.wechat;

import java.util.Date;

/**
 * Description: 用户微信信息对象
 * All Rights Reserved.
 * @version 1.0  2017年12月1日 下午8:20:39  by 周峰（zhoufeng@iqianjin.com）创建
 */
public class WechatInfo {
	
	private Long id;
	
	private Long userId;
	
	private String openId;
	
	private byte status;
	
	private Date createTime;
	
	private Date updateTime;
	/**真实姓名*/
	private String realName;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	
}
