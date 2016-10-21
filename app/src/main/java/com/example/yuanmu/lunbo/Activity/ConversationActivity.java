package com.example.yuanmu.lunbo.Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.StatusBarColorUtil;

import io.rong.imlib.model.Conversation;

//CallKit start 1
//CallKit end 1

/**
 * Created by Bob on 15/11/3.
 * 会话页面
 * 1，设置 ActionBar title
 * 2，加载会话页面
 * 3，push 和 通知 判断
 */
public class ConversationActivity extends FragmentActivity{
	TextView mUserNickName;
	/**
	 * 目标 Id
	 */
	/**
	 * 刚刚创建完讨论组后获得讨论组的id 为targetIds，需要根据 为targetIds 获取 targetId
	 */
	private String mTargetIds;

	/**
	 * 会话类型
	 */
	private Conversation.ConversationType mConversationType;
	@Override
	@TargetApi(23)
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.conversation);
		new StatusBarColorUtil(this,R.color.StyleColor);
		mUserNickName = (TextView) findViewById(R.id.mUserNickName);
		Intent intent = getIntent();
		String title = intent.getData().getQueryParameter("title");
		mUserNickName.setText(title);
	}
}
