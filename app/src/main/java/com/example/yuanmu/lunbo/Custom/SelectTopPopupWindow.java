package com.example.yuanmu.lunbo.Custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.interfaces.KeywithValueUtil;


public class SelectTopPopupWindow extends PopupWindow {

    private View mMenuView;
    private ListView lv_1;
    private String[] marray;
    private Context mcontext;
    private ArrayAdapter adapter;
    private String mcommand;
    private PercentLinearLayout ll_1;

    @SuppressLint("InflateParams")
    public SelectTopPopupWindow(Context context, String command, String[] array) {
        super(context);
        this.marray = array;
        this.mcontext = context;
        this.mcommand = command;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.dialog_one_top, null);
        lv_1 = (ListView) mMenuView.findViewById(R.id.lv_1);
        ll_1 = (PercentLinearLayout) mMenuView.findViewById(R.id.ll_1);
        adapter = new ArrayAdapter();
        lv_1.setAdapter(adapter);
        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
//		this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setTouchable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupTopAnimation);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x20000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        ll_1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                KeywithValueUtil.getKeywithValueInterface()
                        .getkeywithvalue("command", "cancel");
                return false;
            }
        });

    }

    class ArrayAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return marray.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return marray[position];
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String item_array = marray[position];
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mcontext).inflate(
                        R.layout.item_poptop_txt, parent, false);
                holder = new ViewHolder();
                holder.tv_1 = (TextView) convertView.findViewById(R.id.tv_1);
                convertView.setTag(holder);
            } else {

                holder = (ViewHolder) convertView.getTag();

            }
            holder.tv_1.setTag(item_array);
            holder.tv_1.setText(holder.tv_1.getTag() + "");
            holder.tv_1.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    String txt = (String) v.getTag();
                    KeywithValueUtil.getKeywithValueInterface()
                            .getkeywithvalue(mcommand, txt);

                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView tv_1;
        }

    }
}
