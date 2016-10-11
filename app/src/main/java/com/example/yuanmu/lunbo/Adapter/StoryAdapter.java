package com.example.yuanmu.lunbo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.yuanmu.lunbo.Activity.Content_StoryActivity;
import com.example.yuanmu.lunbo.Custom.CircleImageView;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.ImgUtil;

import java.util.List;
import java.util.Map;

public class StoryAdapter extends BaseAdapter {
	private Context mcontext;
	private List<Map<String, Object>> mlist;

	public StoryAdapter(Context context, List<Map<String, Object>> list) {
		this.mcontext = context;
		this.mlist = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mlist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public int getItemViewType(int position) {
		@SuppressWarnings("unchecked")
		List<String> imgarray = (List<String>) mlist.get(position).get(
				"imgarray");
		if (imgarray != null) {
			if (imgarray.size() == 0) {
				return 0;
			}
			if (imgarray.size() == 1 || imgarray.size() == 2) {
				return 1;
			} else {
				if (imgarray.size() >= 3) {
					return 2;
				}
			}
		}
		return 0;

	}

	@Override
	public int getViewTypeCount() {
		return 3;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder1 holder1 = null;
		ViewHolder2 holder2 = null;
		ViewHolder3 holder3 = null;
		final String title = (String) mlist.get(position).get("title");
		final String nickname = (String) mlist.get(position).get("nickname");
		final String img = (String) mlist.get(position).get("img");
		final String createdAt = (String) mlist.get(position).get("createdAt");
		final String objectId = (String) mlist.get(position).get("objectId");
		final List<String> imgarray = (List<String>) mlist.get(position).get(
				"imgarray");
		int type = getItemViewType(position);
		if (convertView == null) {
			switch (type) {
			case 0:// 无图
				convertView = LayoutInflater.from(mcontext).inflate(
						R.layout.item_story_1, parent, false);
				holder1 = new ViewHolder1();
				holder1.civ_img = (CircleImageView) convertView
						.findViewById(R.id.civ_img);
				holder1.tv_nickname = (TextView) convertView
						.findViewById(R.id.tv_nickname);
				holder1.tv_title = (TextView) convertView
						.findViewById(R.id.tv_title);
				holder1.tv_createdAt = (TextView) convertView
						.findViewById(R.id.tv_createdAt);
				holder1.tv_commentcount = (TextView) convertView
						.findViewById(R.id.tv_commentcount);
				convertView.setTag(holder1);
				break;
			case 1:// 一幅图
				convertView = LayoutInflater.from(mcontext).inflate(
						R.layout.item_story_2, parent, false);
				holder2 = new ViewHolder2();
				holder2.civ_img = (CircleImageView) convertView
						.findViewById(R.id.civ_img);
				holder2.tv_nickname = (TextView) convertView
						.findViewById(R.id.tv_nickname);
				holder2.tv_title = (TextView) convertView
						.findViewById(R.id.tv_title);
		 		holder2.tv_createdAt = (TextView) convertView
						.findViewById(R.id.tv_createdAt);
				holder2.tv_commentcount = (TextView) convertView
						.findViewById(R.id.tv_commentcount);
				holder2.iv_img_1 = (NetworkImageView) convertView
						.findViewById(R.id.iv_img_1);
				convertView.setTag(holder2);
				break;
			case 2:// 三幅图及以上
				convertView = LayoutInflater.from(mcontext).inflate(
						R.layout.item_story_3, parent, false);
				holder3 = new ViewHolder3();
				holder3.civ_img = (CircleImageView) convertView
						.findViewById(R.id.civ_img);
				holder3.tv_nickname = (TextView) convertView
						.findViewById(R.id.tv_nickname);
				holder3.tv_title = (TextView) convertView
						.findViewById(R.id.tv_title);
				holder3.tv_createdAt = (TextView) convertView
						.findViewById(R.id.tv_createdAt);
				holder3.tv_commentcount = (TextView) convertView
						.findViewById(R.id.tv_commentcount);
				holder3.iv_img_1 = (NetworkImageView) convertView
						.findViewById(R.id.iv_img_1);
				holder3.iv_img_2 = (NetworkImageView) convertView
						.findViewById(R.id.iv_img_2);
				holder3.iv_img_3 = (NetworkImageView) convertView
						.findViewById(R.id.iv_img_3);
				convertView.setTag(holder3);
				break;
			}
		} else {
			switch (type) {
			case 0:
				holder1 = (ViewHolder1) convertView.getTag();
				break;
			case 1:
				holder2 = (ViewHolder2) convertView.getTag();
				break;
			case 2:
				holder3 = (ViewHolder3) convertView.getTag();
				break;
			}
		}
		switch (type) {
		case 0:
			ImgUtil.setImg(holder1.civ_img, img, 150, 150);
			holder1.tv_title.setText(title);
			holder1.tv_nickname.setText(nickname);
			holder1.tv_createdAt.setText(createdAt);
//			holder2.tv_commentcount.setText(commentcount);
			break;
		case 1:
			ImgUtil.setImg(holder2.civ_img, img, 150, 150);
			holder2.tv_title.setText(title);
			holder2.tv_nickname.setText(nickname);
			holder2.tv_createdAt.setText(createdAt);
//			holder2.tv_commentcount.setText(commentcount);
			ImgUtil.setImg(holder2.iv_img_1, imgarray.get(0) + "");
			break;
		case 2:
			ImgUtil.setImg(holder3.civ_img, img, 150, 150);
			holder3.tv_title.setText(title);
			holder3.tv_nickname.setText(nickname);
			holder3.tv_createdAt.setText(createdAt);
//			holder4.tv_commentcount.setText(commentcount);
			ImgUtil.setImg(holder3.iv_img_1, imgarray.get(0) + "");
			ImgUtil.setImg(holder3.iv_img_2, imgarray.get(1) + "");
			ImgUtil.setImg(holder3.iv_img_3, imgarray.get(2) + "");
			break;
		}
		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(mcontext, Content_StoryActivity.class);
				intent.putExtra("objectId",objectId);
				mcontext.startActivity(intent);
			}
		});
		return convertView;
	}

	class ViewHolder1 {
		CircleImageView civ_img;
		TextView tv_nickname;
		TextView tv_title;
		TextView tv_createdAt;
		TextView tv_commentcount;
	}

	class ViewHolder2 {
		CircleImageView civ_img;
		TextView tv_nickname;
		TextView tv_title;
		TextView tv_createdAt;
		TextView tv_commentcount;
		NetworkImageView iv_img_1;
	}

	class ViewHolder3 {
		CircleImageView civ_img;
		TextView tv_nickname;
		TextView tv_title;
		TextView tv_createdAt;
		TextView tv_commentcount;
		NetworkImageView iv_img_1;
		NetworkImageView iv_img_2;
		NetworkImageView iv_img_3;
	}
}
