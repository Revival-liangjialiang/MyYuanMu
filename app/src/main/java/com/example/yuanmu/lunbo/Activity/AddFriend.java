package com.example.yuanmu.lunbo.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yuanmu.lunbo.Adapter.AddFriendAdapter;
import com.example.yuanmu.lunbo.BmobBean.Focus;
import com.example.yuanmu.lunbo.BmobBean.User;
import com.example.yuanmu.lunbo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class AddFriend extends Activity implements AddFriendAdapter.FindResultFriClick {
	private EditText et_username;
	private Button bt_search;
	private String username;
	private Context context;
	private ListView lv_findresult;
	private AddFriendAdapter adapter;
	private List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
	private Map<String, Object> map;
	private User target;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addfriend);
		context = AddFriend.this;
		initView();
		initData();
		initEvent();

	}

	private void initData() {
		adapter = new AddFriendAdapter(context, lists);
		adapter.setfriinterface(this);
		lv_findresult.setAdapter(adapter);
	}

	private void initEvent() {
		bt_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				lists.clear();
				username = et_username.getText().toString().trim();
				if (TextUtils.isEmpty(username)) {
					return;
				}
				BmobQuery<User> querysum = new BmobQuery<User>();
				querysum.addWhereEqualTo("username", username);
				querysum.findObjects(new FindListener<User>() {

					@Override
					public void done(List<User> list, BmobException e) {
						if (e == null) {
							target = list.get(0);
							if (list.size() == 0) {
								Toast.makeText(context, "不存在此用户",
										Toast.LENGTH_SHORT).show();
							} else {
								map = new HashMap<String, Object>();
								map.put("username", list.get(0).getUsername());
								map.put("nickname", list.get(0).getNickname());
								map.put("img", list.get(0).getImg());
								lists.add(map);
								adapter.notifyDataSetChanged();
							}
						}

					}
				});

			}
		});
	}

	private void initView() {
		et_username = (EditText) findViewById(R.id.et_username);
		bt_search = (Button) findViewById(R.id.bt_search);
		lv_findresult = (ListView) findViewById(R.id.lv_findresult);
	}

	@Override
	public void onfriclick(String targetusername) {
		Focus one = new Focus();
		one.setMyusername(User.getCurrentUser().getUsername());
		one.setTarget(target);
		//设置备注
		one.setRemark(lists.get(0).get("nickname") + "");


		one.save(new SaveListener<String>() {
			@Override
			public void done(String objectId, BmobException e) {
				if (e == null) {
					Toast.makeText(context, "关注成功，返回objectId为：" + objectId,
							Toast.LENGTH_SHORT).show();
					Log.i("关注成功",objectId);
				} else {
					Toast.makeText(context, "关注失败：" + e.getMessage(),
							Toast.LENGTH_SHORT).show();
				}
			}
		});

	}
}
