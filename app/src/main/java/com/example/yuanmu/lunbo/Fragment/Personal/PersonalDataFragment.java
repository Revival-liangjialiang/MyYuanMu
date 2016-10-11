package com.example.yuanmu.lunbo.Fragment.Personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuanmu.lunbo.Activity.ConditionalSelectionActivity;
import com.example.yuanmu.lunbo.Activity.DataActivity;
import com.example.yuanmu.lunbo.Activity.EditPersonalDataActivity;
import com.example.yuanmu.lunbo.Activity.PersonalInputPopupActivity;
import com.example.yuanmu.lunbo.Adapter.GiftRecyclerViewAdapter;
import com.example.yuanmu.lunbo.Adapter.PersonalDataReAdapter;
import com.example.yuanmu.lunbo.BmobBean.User;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.MyLog;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class PersonalDataFragment extends Fragment{
    TextView mPersonalEdit_tv,mEditMonologue_tv,mMonologueContent_tv;
    TextView mId_tv,mNickName_tv,mWorkingArea_tv,mAge_tv,mIncome_tv,mEducation_tv,mMaritalStatus_tv,mHousingSituation_tv,mBuying_a_car_tv,
            mOccupation_tv,mConstellation_tv,mNation_tv,mPlace_of_origin_tv,mWeight_tv,mShape_tv,mWhen_to_get_married_tv,mWhether_or_not_to_have_children_tv,mHeight_tv
            ,mHave_no_children_tv;
    RecyclerView mAlbumRe,mGiftRe;
    DataActivity mActivity;
    private void getData(User object) {
        mId_tv.setText(object.getUsername());
        mNickName_tv.setText(object.getNickname());
        mWorkingArea_tv.setText(object.getWorking_area());
        //通过生日计算年龄
        String str = object.getBirthday();
        if(str.equals("未知")){
            mAge_tv.setText(str);
        }else {
            str = str.substring(0, str.indexOf("."));
            int value = 2016 - Integer.valueOf(str);
            mAge_tv.setText(value + "");
        }
        if(!object.getMonologue().equals("")) {
            mMonologueContent_tv.setText(object.getMonologue());
        }else{
            mMonologueContent_tv.setText("这家伙很懒，什么也没留下!");
        }
        mHeight_tv.setText(object.getHeight());
        mIncome_tv.setText(object.getIncome());
        mEducation_tv.setText(object.getEducation());
        mMaritalStatus_tv.setText(object.getMarital_status());
        mHousingSituation_tv.setText(object.getHousing_situation());
        mBuying_a_car_tv.setText(object.getBuying_a_car());
        mOccupation_tv.setText(object.getOccupation());
        mConstellation_tv.setText(object.getConstellation());
        mNation_tv.setText(object.getNation());
        mPlace_of_origin_tv.setText(object.getPlace_of_origin());
        mWeight_tv.setText(object.getWeight());
        mShape_tv.setText(object.getShape());
        mWhen_to_get_married_tv.setText(object.getWhen_to_get_married());
        mWhether_or_not_to_have_children_tv.setText(object.getDo_you_want_a_baby());
        mHave_no_children_tv.setText(object.getThere_is_no_child());
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_personal_data_vp_layout,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (DataActivity) getActivity();
        initView();
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<User> query = new BmobQuery<>();
        query.getObject(user.getObjectId(), new QueryListener<User>() {
            @Override
            public void done(User object, BmobException e) {
                if(e==null){
                    getData(object);
                }else{
                    MyLog.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }


    private void initView() {
        mHave_no_children_tv = (TextView) mActivity.findViewById(R.id.mHave_no_children_tv);
        mMonologueContent_tv = (TextView) mActivity.findViewById(R.id.mMonologueContent_tv);
        mEditMonologue_tv = (TextView) mActivity.findViewById(R.id.mEditMonologue_tv);
        mPersonalEdit_tv = (TextView) mActivity.findViewById(R.id.mPersonalEdit_tv);
        //UserDataTextView
        mHeight_tv = (TextView) mActivity.findViewById(R.id.mHeight_tv);
        mWhether_or_not_to_have_children_tv = (TextView) mActivity.findViewById(R.id.mWhether_or_not_to_have_children_tv);
        mWhen_to_get_married_tv = (TextView) mActivity.findViewById(R.id.mWhen_to_get_married_tv);
        mShape_tv = (TextView) mActivity.findViewById(R.id.mShape_tv);
        mWeight_tv = (TextView) mActivity.findViewById(R.id.mWeight_tv);
        mPlace_of_origin_tv = (TextView) mActivity.findViewById(R.id.mPlace_of_origin_tv);
        mNation_tv = (TextView) mActivity.findViewById(R.id.mNation_tv);
        mConstellation_tv = (TextView) mActivity.findViewById(R.id.mConstellation_tv);
        mOccupation_tv = (TextView) mActivity.findViewById(R.id.mOccupation_tv);
        mBuying_a_car_tv = (TextView) mActivity.findViewById(R.id.mBuying_a_car_tv);
        mHousingSituation_tv = (TextView) mActivity.findViewById(R.id.mHousingSituation_tv);
        mMaritalStatus_tv = (TextView) mActivity.findViewById(R.id.mMaritalStatus_tv);
        mEducation_tv = (TextView) mActivity.findViewById(R.id.mEducation_tv);
        mIncome_tv = (TextView) mActivity.findViewById(R.id.mIncome_tv);
        mAge_tv = (TextView) mActivity.findViewById(R.id.mAge_tv);
        mWorkingArea_tv = (TextView) mActivity.findViewById(R.id.mWorkingArea_tv);
        mNickName_tv = (TextView) mActivity.findViewById(R.id.mNickName_tv);
        mId_tv = (TextView) mActivity.findViewById(R.id.mId_tv);
        //初始化相册Re
        mAlbumRe = (RecyclerView) mActivity.findViewById(R.id.personal_re);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mAlbumRe.setLayoutManager(layoutManager);
        mAlbumRe.setAdapter(new PersonalDataReAdapter());
        //初始化礼物Re
        mGiftRe = (RecyclerView) mActivity.findViewById(R.id.gift_RecyclerView);
        LinearLayoutManager giftLayoutManager = new LinearLayoutManager(mActivity);
        giftLayoutManager.setOrientation(giftLayoutManager.HORIZONTAL);
        mGiftRe.setLayoutManager(giftLayoutManager);
        mGiftRe.setAdapter(new GiftRecyclerViewAdapter());
        //注册编辑TextView事件
        mPersonalEdit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mActivity, EditPersonalDataActivity.class));
                mActivity.mPopup_layout.setVisibility(View.GONE);
            }
        });
        mEditMonologue_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.setmPersonalDataFragment(PersonalDataFragment.this);
                Intent intent = new Intent(mActivity, PersonalInputPopupActivity.class);
                intent.putExtra(ConditionalSelectionActivity.TITLE,"输入内心独白");
                mActivity.startActivityForResult(intent,0);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
            if (resultCode == mActivity.RESULT_OK) {
            String content = data.getStringExtra(ConditionalSelectionActivity.VALUE);
                mMonologueContent_tv.setText(content);
                com.example.yuanmu.lunbo.BmobBean._2.SaveMonologue.User user = BmobUser.getCurrentUser(com.example.yuanmu.lunbo.BmobBean._2.SaveMonologue.User.class);
                user.setMonologue(content);
                user.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                       if(e == null){
                           Toast.makeText(mActivity, "保存成功!", Toast.LENGTH_SHORT).show();
                       }else {
                           Toast.makeText(mActivity, "保存失敗!", Toast.LENGTH_SHORT).show();
                           MyLog.i("bbb","e = "+e);
                       }
                    }
                });
            }
                break;
            case 1:

                break;
            default:break;
        }
    }
}
