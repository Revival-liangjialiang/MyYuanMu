package com.example.yuanmu.lunbo.BmobBean;

import cn.bmob.v3.BmobObject;

public class Focus extends BmobObject {

	private String myusername;

	public String getMyusername() {
		return myusername;
	}

	public void setMyusername(String myusername) {
		this.myusername = myusername;
	}

	public User getTarget() {
		return target;
	}

	public void setTarget(User target) {
		this.target = target;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	private User target;
	private String remark;

}
