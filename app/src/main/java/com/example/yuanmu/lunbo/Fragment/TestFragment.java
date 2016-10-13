package com.example.yuanmu.lunbo.Fragment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuanmu.lunbo.Activity.Lovematch;
import com.example.yuanmu.lunbo.Activity.UserDataActivity;
import com.example.yuanmu.lunbo.BmobBean.User;
import com.example.yuanmu.lunbo.Custom.Frame_MyView;
import com.example.yuanmu.lunbo.Custom.PercentRelativeLayout;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.ImgUtil;

public class TestFragment extends Fragment implements OnClickListener{
	PercentRelativeLayout mPercentLayout;
	private User user;
	//头像
	public ImageView iv;
	//独白
	public TextView monologue_tv,userName,age_tv,constellation_tv;
	public Frame_MyView mCollection_view;
	Lovematch m;
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.frag_layout, container, false);
		m = (Lovematch) getActivity();
		initUI(rootView);
		return rootView;
	}
	private void initUI(View root){
		mPercentLayout = (PercentRelativeLayout) root.findViewById(R.id.percentLayout);
		constellation_tv = (TextView) root.findViewById(R.id.constellation_tv);
		age_tv = (TextView) root.findViewById(R.id.age_tv);
		userName = (TextView) root.findViewById(R.id.userName);
		monologue_tv = (TextView) root.findViewById(R.id.monologue_tv);
		iv = (ImageView) root.findViewById(R.id.imageView);
		mCollection_view = (Frame_MyView)root.findViewById(R.id.collection_view);
		mCollection_view.bitmap = BitmapFactory.decodeResource(m.getResources(),R.mipmap.oo);
		mCollection_view.postInvalidate();
		mCollection_view.setOnClickListener(this);
		mPercentLayout.setOnClickListener(this);
		//iv.setImageResource(res);
		monologue_tv.setText(user.getMonologue());
		userName.setText(user.getNickname());
		constellation_tv.setText(user.getConstellation());
		String str = user.getBirthday();
		if(str.equals("未知")){
			age_tv.setText(str);
		}else {
			str = str.substring(0, str.indexOf("."));
			int value = 2016 - Integer.valueOf(str);
			age_tv.setText(value + "");
		}
		ImgUtil.setImg(iv,user.getImg(),500,500);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.percentLayout:
				Intent intent = new Intent(getContext(),UserDataActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("user",user);
				intent.putExtras(bundle);
				startActivity(intent);
				Toast.makeText(getContext(), "您点击了第"+m.Indexes+"个MM", Toast.LENGTH_SHORT).show();
				break;
			case R.id.collection_view:
				Toast.makeText(getContext(), "您收藏了第"+m.Indexes+"个MM", Toast.LENGTH_SHORT).show();
				break;
			default:break;
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
