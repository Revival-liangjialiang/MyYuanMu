package com.example.yuanmu.lunbo.interfaces;

import android.content.Context;
import android.widget.Toast;

import com.example.yuanmu.lunbo.Application.MyApplication;
import com.example.yuanmu.lunbo.BmobBean.User;
import com.example.yuanmu.lunbo.Util.MyLog;
import com.example.yuanmu.lunbo.Util.SharedUtil;

import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class LoginUtil {
	public static void login(final Context context, final String username,
			final String password, final LoginInterface loginInterface) {
		final BmobUser user = new BmobUser();// 创建对象，这个为Bmob创建的类
		/*BmobUser.logOut();*/
		// 与数据库中的信息进行比对，成功返回成功，失败返回失败的信息
		user.setUsername(username);
		user.setPassword(password);
		/*
		 * 用户登录
		 */
		user.login(new SaveListener<BmobUser>() {
		    @Override
		    public void done(BmobUser bmobUser, BmobException e) {
		        if(e==null){
					Toast.makeText(MyApplication.getContext(), "登录！", Toast.LENGTH_SHORT).show();
					MyApplication.isLogin = true;
		        	User user = BmobUser.getCurrentUser(User.class);
					//设置手机识别码，然后更新上去
				user.setInstallationId(BmobInstallation.getInstallationId(MyApplication.getContext()));
				user.update(new UpdateListener() {
						    @Override
						    public void done(BmobException e) {
								if(e == null) {
									MyLog.i("test", "手机识别码更新Success!");
								}else{
									MyLog.i("test", "手机识别码更新Fail!");
								}
						    }
					    }
				);
		        	User u = new User();
		        	u.setUsername(user.getUsername());
		        	u.setToken(user.getToken());
		        	u.setImg(user.getImg());
		        	u.setNickname(user.getNickname());
		        	MyApplication.isLogin = true;
		        	MyApplication.token = user.getToken();
		        	MyApplication.username = user.getUsername();
					SharedUtil.setUserinfo(context, username,
							password);
					loginInterface.LoginResult("success");
		            //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(context,MyUser.class)获取自定义用户信息
		        }else{
//		            loge(e);
		        	MyLog.i("登录失败", e + "");
					loginInterface.LoginResult("error");
		        }
		    }
		});

	}

}
