package com.example.yuanmu.lunbo.Fragment.Personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuanmu.lunbo.Activity.DataActivity;
import com.example.yuanmu.lunbo.BmobBean.Condition;
import com.example.yuanmu.lunbo.BmobBean.User;
import com.example.yuanmu.lunbo.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class ConditionalSelectionFeagment extends Fragment {
    DataActivity mActivity;
    TextView mAge_tv,mHeight_tv,mIncome_tv,mEducation_tv,mWeight_tv,mMaritalStatus_tv,mWorkingArea_tv,mHave_no_children_tv,mWhether_or_not_to_have_children_tv,
            mShape_tv,mWhether_to_drink_tv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_conditional_selection_vp_layout,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (DataActivity) getActivity();
        mActivity.setmConditionalSelectionFeagment(this);
        initView();
        //查询数据并显示
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Condition> query = new BmobQuery<>();
        query.addWhereEqualTo("userNickName", user.getNickname());
        query.findObjects(new FindListener<Condition>() {
            @Override
            public void done(List<Condition> list, BmobException e) {
                if(e == null&&list!=null){
                    Toast.makeText(mActivity, "查询选偶条件成功!", Toast.LENGTH_SHORT).show();
                    getData(list.get(0));
                }else {
                    Toast.makeText(mActivity, "查询选偶条件有错误!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        mAge_tv = (TextView) mActivity.findViewById(R.id.mAge_tv_2);
        mHeight_tv = (TextView) mActivity.findViewById(R.id.mHeight_tv_2);
        mIncome_tv = (TextView) mActivity.findViewById(R.id.mIncome_tv_2);
        mEducation_tv = (TextView) mActivity.findViewById(R.id.mEducation_tv_2);
        mWeight_tv = (TextView) mActivity.findViewById(R.id.mWeight_tv);
        mMaritalStatus_tv = (TextView) mActivity.findViewById(R.id.mMaritalStatus_tv_2);
        mWorkingArea_tv = (TextView) mActivity.findViewById(R.id.mWorkingArea_tv_2);
        mHave_no_children_tv = (TextView) mActivity.findViewById(R.id.mHave_no_children_tv_2);
        mWhether_or_not_to_have_children_tv = (TextView) mActivity.findViewById(R.id.mWhether_or_not_to_have_children_tv_2);
        mShape_tv = (TextView) mActivity.findViewById(R.id.mShape_tv_2);
        mWhether_to_drink_tv = (TextView) mActivity.findViewById(R.id.mWhether_to_drink_tv_2);
    }
    public void getData(Condition condition) {
        mAge_tv.setText(condition.getAge());
        mHeight_tv.setText(condition.getHeight());
        mIncome_tv.setText(condition.getIncome());
        mEducation_tv.setText(condition.getEducation());
        mWeight_tv.setText(condition.getWeight());
        mMaritalStatus_tv.setText(condition.getMarital_status());
        mWorkingArea_tv.setText(condition.getWorking_area());
        mHave_no_children_tv.setText(condition.getThere_is_no_child());
        mWhether_or_not_to_have_children_tv.setText(condition.getDo_you_want_a_baby());
        mShape_tv.setText(condition.getShape());
        mWhether_to_drink_tv.setText(condition.getWhether_to_drink());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == mActivity.RESULT_OK){
            Condition condition = (Condition) data.getSerializableExtra("Condition");
            getData(condition);
        }
    }
}
