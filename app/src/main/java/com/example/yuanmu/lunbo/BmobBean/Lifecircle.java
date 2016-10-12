package com.example.yuanmu.lunbo.BmobBean;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

public class Lifecircle extends BmobObject {
	public Lifecircle(){
		fabulous.add(" ");
	}
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User author) {
		this.user = author;
	}
	public BmobRelation getComment() {
		return comment;
	}

	public void setComment(BmobRelation comment) {
		this.comment = comment;
	}
	public List<String> getImgarray() {
		return imgarray;
	}

	public void setImgarray(List<String> imgarray) {
		this.imgarray = imgarray;
	}
	public List<String> getCommentarray() {
		return commentarray;
	}

	public void setCommentarray(List<String> commentarray) {
		this.commentarray = commentarray;
	}
	private String content;// 帖子内容
	private User user;// 帖子的发布者，这里体现的是一对一的关系，该帖子属于某个用户
	private List<String> imgarray;
	private BmobRelation comment;
	private List<String> commentarray;
	private List<String> fabulous = new ArrayList<>();
}
