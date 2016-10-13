package com.example.yuanmu.lunbo.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.yuanmu.lunbo.BmobBean.User;
import com.example.yuanmu.lunbo.Fragment.TestFragment;
import com.example.yuanmu.lunbo.Util.MyLog;

import java.util.List;

public class TestFragPagerAdapter extends FragmentPagerAdapter {
	List<User> users;
	public TestFragPagerAdapter(FragmentManager fm,List<User> users) {
		super(fm);
		this.users = users;
	}

	@Override
	public Fragment getItem(int arg0) {
		TestFragment frag = new TestFragment();
		frag.setUser(users.get(arg0));
		return frag;
	}

	@Override
	public int getCount() {
		MyLog.i("qq",""+users.size());
		return users.size();
	}
}
