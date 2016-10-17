package com.example.yuanmu.lunbo.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yuanmu.lunbo.Custom.CircleImageView;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.ImgUtil;

import java.util.List;
import java.util.Map;

public class AddFriendAdapter extends BaseAdapter{
	private Context mcontext;
	private List<Map<String, Object>> mlist;
	private FindResultFriClick friclick;
	public AddFriendAdapter(Context context, List<Map<String, Object>> list) {
		this.mcontext = context;
		this.mlist = list;

	}

	@Override
	public int getCount() {
		return mlist.size();
	}

	@Override
	public Map<String, Object> getItem(int position) {
		return mlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;
		String nickname = (String) getItem(position).get("nickname");
		final String username = (String) getItem(position).get("username");
		final String img = (String) getItem(position).get("img");
		if (convertView == null) {
			convertView = LayoutInflater.from(mcontext).inflate(
					R.layout.item_findresult, parent, false);
			holder = new ViewHolder();
			holder.friend_username = (TextView) convertView
					.findViewById(R.id.friend_username);
			holder.friend_avator = (CircleImageView) convertView
					.findViewById(R.id.friend_avator);
			holder.friend_nickname = (TextView) convertView
					.findViewById(R.id.friend_nickname);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (!TextUtils.isEmpty(img)) {
			ImgUtil.setImg(holder.friend_avator, img, 100, 100);
		}else{
			holder.friend_avator.setImageResource(R.mipmap.myviewtest);
		}
		holder.friend_username.setText(username);
		holder.friend_nickname.setText(nickname);


		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				friclick.onfriclick(username);
			}

		});

	
		return convertView;
	}


	public interface FindResultFriClick {
		public void onfriclick(String targetusername);

	}

	public void setfriinterface(FindResultFriClick friclick) {
		this.friclick = friclick;
	}

	class ViewHolder {
		CircleImageView friend_avator;
		TextView friend_username;
		TextView friend_nickname;
	}

}
