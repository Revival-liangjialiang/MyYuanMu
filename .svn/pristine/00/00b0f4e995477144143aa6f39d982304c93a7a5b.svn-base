package com.example.yuanmu.lunbo.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.yuanmu.lunbo.Fragment.Personal.ConditionalSelectionFeagment;
import com.example.yuanmu.lunbo.Fragment.Personal.PersonalDataFragment;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class DataViewPagerAdapter extends FragmentPagerAdapter{
    public DataViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new PersonalDataFragment();
            case 1:
                return new ConditionalSelectionFeagment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
