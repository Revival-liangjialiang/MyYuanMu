package com.example.yuanmu.lunbo.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.yuanmu.lunbo.Fragment.HomePage_Fragment;
import com.example.yuanmu.lunbo.Fragment.Lifecircle_Fragment;
import com.example.yuanmu.lunbo.Fragment.Nearby_Fragment;
import com.example.yuanmu.lunbo.Fragment.News_Fragment;
import com.example.yuanmu.lunbo.Fragment.Personal.Personal_Fragment;

/**
 * Created by Administrator on 2016/8/25 0025.
 */
public class Main_ViewPager_Adapter extends FragmentPagerAdapter{
    public Main_ViewPager_Adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomePage_Fragment();
            case 1:
                return new Lifecircle_Fragment();
            case 2:
                return new Nearby_Fragment();
            case 3:
                return new News_Fragment();
            case 4:
                return new Personal_Fragment();
            default:break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
