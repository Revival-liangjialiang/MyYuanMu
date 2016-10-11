package com.example.yuanmu.lunbo.BmobBean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

public class CircleComment extends BmobObject{
	private String content;//评论内容

    public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private User user;//评论的用户，Pointer类型，一对一关系
    private String belong; //所评论的帖子，这里体现的是一对多的关系，一个评论只能属于一个微博
    public String getBelong() {
		return belong;
	}

	public void setBelong(String belong) {
		this.belong = belong;
	}

	public String getTargetuser() {
		return targetuser;
	}

	public void setTargetuser(String targetuser) {
		this.targetuser = targetuser;
	}

	private String targetuser;

	private BmobRelation reply;

	public BmobRelation getReply() {
		return reply;
	}

	public void setReply(BmobRelation reply) {
		this.reply = reply;
	}
}
