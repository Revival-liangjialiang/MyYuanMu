package com.example.yuanmu.lunbo.Custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.interfaces.KeywithValueUtil;

public class SelectPicPopupWindow extends PopupWindow {

	private TextView tv_ok;
	private View mMenuView;
	private Context mcontext;
private EditText et_comment;
	@SuppressLint("InflateParams")
	public SelectPicPopupWindow(Context context, final String POP_KEY, String target) {
		super(context);
		this.mcontext = context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.layout_dialog_pic, null);
		tv_ok = (TextView) mMenuView.findViewById(R.id.tv_ok);
		et_comment = (EditText) mMenuView.findViewById(R.id.et_comment);
		// 设置按钮监听
		// pickPhotoBtn.setOnClickListener(itemsOnClick);
		// takePhotoBtn.setOnClickListener(itemsOnClick);
		// 设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.PopupAnimation);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0x88000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		et_comment.setHint("回复第" + target + "个");
		tv_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String replycontent = et_comment.getText().toString().trim();
				if(TextUtils.isEmpty(replycontent)){
					Toast.makeText(mcontext,"回复为空",Toast.LENGTH_SHORT).show();
					return;
				}else{
					KeywithValueUtil.getKeywithValueInterface().getkeywithvalue(
							POP_KEY, replycontent);
				}

			}
		});
		// mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		mMenuView.setOnTouchListener(new OnTouchListener() {

			@Override
			@SuppressLint("ClickableViewAccessibility")
			public boolean onTouch(View v, MotionEvent event) {

				int height = mMenuView.findViewById(R.id.pop_layout).getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (y < height) {
						dismiss();
					}
				}
				return true;
			}
		});

	}


}
