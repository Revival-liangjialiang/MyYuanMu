package com.example.yuanmu.lunbo.Adapter;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.example.yuanmu.lunbo.Custom.CircleImageView;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.ImgUtil;
import com.example.yuanmu.lunbo.Util.ScreenUtil;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FriendAdapter extends BaseAdapter implements OnClickListener {
	private Context mcontext;
	private MessageFriClick friclick;
	private ArrayList<String> mpinyinList;
	private Map<String, String> usernamemap;
	private Map<String, String> imgmap;
	private Map<String, String> remarkmap;

	public FriendAdapter(Context context, ArrayList<String> pinyinList,
			Map<String, String> usernamemap, Map<String, String> imgmap,
			Map<String, String> remarkmap) {
		this.mcontext = context;
		this.mpinyinList = pinyinList;
		this.usernamemap = usernamemap;
		this.imgmap = imgmap;
		this.remarkmap = remarkmap;
	}

	@Override
	public int getCount() {
		return mpinyinList.size();
	}

	@Override
	public String getItem(int position) {
		String pinyin = mpinyinList.get(position);
		return pinyin;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		String pinyin = mpinyinList.get(position);
		final String username = usernamemap.get(pinyin);
		final String img = imgmap.get(username);
		final String remark = remarkmap.get(username);
		if (convertView == null) {
			convertView = LayoutInflater.from(mcontext).inflate(
					R.layout.message_friend_item, parent, false);
			holder = new ViewHolder();
			holder.firstCharHintTextView = (TextView) convertView
					.findViewById(R.id.text_first_char_hint);
			holder.friend_avator = (CircleImageView) convertView
					.findViewById(R.id.friend_avator);
			holder.friend_remark = (TextView) convertView
					.findViewById(R.id.friend_remark);

			holder.hs = (HorizontalScrollView) convertView
					.findViewById(R.id.hs);
			holder.bt_1 = (Button) convertView.findViewById(R.id.bt_1);
			holder.bt_2 = (Button) convertView.findViewById(R.id.bt_2);

			holder.action = convertView.findViewById(R.id.ll_action);
			holder.content = convertView.findViewById(R.id.ll_content);
			LayoutParams lp = holder.content.getLayoutParams();
			lp.width = ScreenUtil.getScreenwidth();

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (!TextUtils.isEmpty(img)) {
			ImgUtil.setImg(holder.friend_avator, img, 100, 100);
		} else {
			holder.friend_avator.setImageResource(R.mipmap.myviewtest);
		}
		holder.friend_remark.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				holder.friend_remark.setText("");
			}
		});
		holder.friend_remark.setText(remark);

		int idx = position - 1;
		char previewChar = idx >= 0 ? mpinyinList.get(idx).charAt(0) : ' '; // 鍓嶄竴涓瓧绗?
		char currentChar = mpinyinList.get(position).charAt(0); // 褰撳墠瀛楃
		if (currentChar != previewChar) { // 濡傛灉涓嶇浉绛夋椂鏄剧ず
			if (isLetter(currentChar)) {
				holder.firstCharHintTextView.setVisibility(View.VISIBLE);
				holder.firstCharHintTextView.setText(String
						.valueOf(currentChar).toUpperCase());
			} else {
				if (isLetter(previewChar)) {
					holder.firstCharHintTextView.setVisibility(View.VISIBLE);
					holder.firstCharHintTextView.setText("#");
				} else {
					holder.firstCharHintTextView.setVisibility(View.GONE);
				}
			}
		} else {
			holder.firstCharHintTextView.setVisibility(View.GONE);
		}

		holder.content.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				friclick.onfriclick(username, remark, img);
			}

		});

		holder.bt_1.setTag(pinyin);
		holder.bt_2.setTag(pinyin);

		convertView.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if (v != null) {
						ViewHolder viewholder1 = (ViewHolder) v.getTag();
						viewholder1.hs.smoothScrollTo(0, 0);
					}
					break;
				case MotionEvent.ACTION_UP:
					ViewHolder viewholder2 = (ViewHolder) v.getTag();

					int scrollx = viewholder2.hs.getScrollX();
					int widthx = viewholder2.action.getWidth();
					if (scrollx < widthx / 2) {
						viewholder2.hs.smoothScrollTo(0, 0);
					} else {
						viewholder2.hs.smoothScrollTo(widthx, 0);
					}
					return true;
				}
				return false;
			}

		});

		if (holder.hs.getScrollX() != 0) {
			holder.hs.smoothScrollTo(0, 0);
		}

		holder.bt_1.setOnClickListener(this);
		holder.bt_2.setOnClickListener(this);
		return convertView;
	}

	public interface MessageFriClick {
		public void onfriclick(String targetusername, String remark, String img);

	}

	public void setfriinterface(MessageFriClick friclick) {
		this.friclick = friclick;
	}

	class ViewHolder {
		CircleImageView friend_avator;
		TextView friend_remark;
		Button bt_1, bt_2;
		View content, action;
		HorizontalScrollView hs;
		public TextView firstCharHintTextView;
	}

	@Override
	public void onClick(View v) {
		String mpinyin = (String) v.getTag();
		String mtarget = usernamemap.get(mpinyin);
		switch (v.getId()) {
		case R.id.bt_1:
			// SetBlackMenu.PushBlackMenu(mtarget);
			// mpinyinList.remove(mpinyin);
			// notifyDataSetChanged();
			break;
		case R.id.bt_2:
			// DeleteFriend.startDelete(mtarget);
			// mpinyinList.remove(mpinyin);
			// notifyDataSetChanged();
			break;
		}

	}

	public boolean isLetter(char c) {
		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		Matcher isNum = pattern.matcher(String.valueOf(c));
		if (!isNum.matches()) {
			return false;
		} else {
			return true;
		}
	}
}
