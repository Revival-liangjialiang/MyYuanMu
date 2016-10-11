package com.example.yuanmu.lunbo.Adapter;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.CommenUtil;
import com.example.yuanmu.lunbo.Util.LocalImageBean;

import java.util.List;


public class GridImageAdapter extends BaseAdapter {
	private List mlist;
	private Context mcontext;

	public GridImageAdapter(Context context, List maplist) {
		this.mcontext = context;
		this.mlist = maplist;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mlist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mcontext).inflate(
					R.layout.griditem_addpic, parent, false);
			holder = new ViewHolder();
			holder.imageView1 = (ImageView) convertView
					.findViewById(R.id.imageView1);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Bitmap addbmps = CommenUtil.getLoacalBitmap(LocalImageBean.list.get(position) + "");
		holder.imageView1.setImageBitmap(addbmps);
		convertView.setOnLongClickListener(new OnLongClickListener(){

			@Override
			public boolean onLongClick(View v) {
				dialog(position);
				return false;
			}});
		return convertView;
	}
	/*
	 * Dialog对话框提示用户删除操作 position为删除图片位置
	 */
	protected void dialog(final int position) {
		Builder builder = new Builder(mcontext);
		builder.setMessage("确认移除已添加图片吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				LocalImageBean.mSelectMap.remove(LocalImageBean.list.get(position));
				LocalImageBean.list.remove(position);
				LocalImageBean.Count = LocalImageBean.list.size();
				notifyDataSetChanged();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
	

	class ViewHolder {
		ImageView imageView1;
	}
}
