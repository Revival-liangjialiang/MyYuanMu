package com.example.yuanmu.lunbo.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yuanmu.lunbo.Application.MyApplication;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.PrefUtils;
import com.example.yuanmu.lunbo.Util.SharedUtil;
import com.example.yuanmu.lunbo.interfaces.LoginInterface;
import com.example.yuanmu.lunbo.interfaces.LoginUtil;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.BmobInstallation;

public class SplashActivity extends Activity {
	ImageView rlRoot;
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			// 透明状态栏
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
		setContentView(R.layout.activity_splash);
		context = SplashActivity.this;
		BmobConfig config = new BmobConfig.Builder(this)
		// 设置appkey
				.setApplicationId(MyApplication.APPID)
				// 请求超时时间（单位为秒）：默认15s
				.setConnectTimeout(30)
				// 文件分片上传时每片的大小（单位字节），默认512*1024
				.setUploadBlockSize(1024 * 1024)
				// 文件的过期时间(单位为秒)：默认1800s
				.setFileExpiration(2500).build();
		Bmob.initialize(config);
		Bmob.initialize(this, "d6aba0f132596eb927dcde680f807bec");
		// 使用推送服务时的初始化操作
		BmobInstallation.getCurrentInstallation().save();
		// 启动推送服务
		BmobPush.startWork(this);
		rlRoot = (ImageView) findViewById(R.id.rl_root);
		startAnim();
	}

	/**
	 * 开启动画
	 */
	private void startAnim() {
		// 动画集合
		AnimationSet set = new AnimationSet(false);

		// 旋转动画
		RotateAnimation rotate = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		rotate.setDuration(1000);// 动画时间
		rotate.setFillAfter(true);// 保持动画状态

		// 缩放动画
		ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scale.setDuration(1000);// 动画时间
		scale.setFillAfter(true);// 保持动画状态

		// 渐变动画
		AlphaAnimation alpha = new AlphaAnimation(0, 1);
		alpha.setDuration(1000);// 动画时间
		alpha.setFillAfter(true);// 保持动画状态

		set.addAnimation(rotate);
		set.addAnimation(scale);
		set.addAnimation(alpha);

		// 设置动画监听
		set.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			// 动画执行结束
			@Override
			public void onAnimationEnd(Animation animation) {
				String username = SharedUtil.getUserName(context);
				String password = SharedUtil.getPassword(context);
				Log.i(username, password);
				if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
					jumpNextPage();
					return;
				}
				Log.i("有帐号", "有密码");

				LoginUtil.login(SplashActivity.this, username, password,
						new LoginInterface() {

							@Override
							public void LoginResult(String result) {
								if (result.equals("success")) {
									Toast.makeText(SplashActivity.this, "登录!", Toast.LENGTH_SHORT).show();
									Intent intent = new Intent(
											SplashActivity.this,
											MainActivity.class);
									startActivity(intent);
									finish();
								} else {
									jumpNextPage();
								}
							}

						});
			}
		});

		rlRoot.startAnimation(set);
	}

	/**
	 * 跳转下一个页面
	 */
	private void jumpNextPage() {
		// 判断之前有没有显示过新手引导
		boolean userGuide = PrefUtils.getBoolean(this, "is_user_guide_showed",
				false);

		if (!userGuide) {
			// 跳转到新手引导页
			startActivity(new Intent(SplashActivity.this, GuideActivity.class));
		} else {
			startActivity(new Intent(SplashActivity.this, MainActivity.class));
		}

		finish();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (isTaskRoot()) {
			return;
		} // 如果有面板存在则关闭当前的面板 ，由于腾讯信鸽接收通知点击时会重启app，此举可以免去点击后跳转到页面返回后重新启动的情况。
		finish();
	}
}
