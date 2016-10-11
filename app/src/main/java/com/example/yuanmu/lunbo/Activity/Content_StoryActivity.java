package com.example.yuanmu.lunbo.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuanmu.lunbo.Adapter._2.Comment_CircleAdapter;
import com.example.yuanmu.lunbo.Custom.Comment_CircleListView;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.ScreenUtil;
import com.example.yuanmu.lunbo.BmobBean.Story;

import org.xml.sax.XMLReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by yuanmu on 2016/9/6.
 */
public class Content_StoryActivity extends Activity {
    private Comment_CircleListView lv_comment;
    private Comment_CircleAdapter adapter;
    private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    private Map<String, Object> map;
    private TextView tv_content;
    private Context context;
    private String content, createdAt, title;
    private String mobjectId;
    private TextView tv_createdAt, tv_title;
//    private String html = "<p>中国内地女演员，1992年11月6日出生于北京市，2014年毕业于北京电影学院2010级表演系本科班。</p>" +
//            "<img src='http://e.hiphotos.baidu.com/image/h%3D360/sign=ea96ce4c0e7b020813c939e752d8f25f/14ce36d3d539b600be63e95eed50352ac75cb7ae.jpg' />" +
//            "<p style = \"line-height:1\">在aneroid中原本利用webview加载html页面,其中有用到JavaScript处理点击事件,实现用户交互。</p>" +
//            "<img src='http://b.hiphotos.baidu.com/image/h%3D360/sign=22b4112b8501a18befeb1449ae2e0761/8644ebf81a4c510f7099751d6259252dd42aa55c.jpg' />" +
//            "<p>美女，一般解释为容貌美丽的女子。中国古代关于美女的形容词和诗词歌赋众多，形成了丰富的美学资料。</p>" +
//            "<img src='http://d.hiphotos.baidu.com/image/h%3D360/sign=78a6147b41a98226a7c12d21ba83b97a/54fbb2fb43166d22dc28839a442309f79052d265.jpg'/>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_story);
        context = Content_StoryActivity.this;
        Intent intent = getIntent();
        mobjectId = intent.getStringExtra("objectId");
        Log.i("objectId", mobjectId + "");
        initView();
        initData();
        getData();
    }

    private void getData() {
        //查找Person表里面id为6b6c11c537的数据
        BmobQuery<Story> bmobQuery = new BmobQuery<Story>();
        bmobQuery.getObject(mobjectId, new QueryListener<Story>() {
            @Override
            public void done(Story story, BmobException e) {
                if (e == null) {
                    title = story.getTitle();
                    createdAt = story.getCreatedAt();
                    content = story.getContent();
                    tv_createdAt.setText(createdAt);
                    tv_title.setText(title);
                    htmlWebPic(content);
                } else {

                }
            }
        });
        map = new HashMap<String, Object>();
        map.put("txt", "不错不错，以后我会光顾你的。");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("txt", "不错不错，以后我会光顾你的。");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("txt", "不错不错，以后我会光顾你的。");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("txt", "不错不错，以后我会光顾你的。");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("txt", "不错不错，以后我会光顾你的。");
        list.add(map);
        adapter.notifyDataSetChanged();
    }

    private void initData() {
        tv_content.setMovementMethod(ScrollingMovementMethod.getInstance());// 设置可滚动
        adapter = new Comment_CircleAdapter(context, list);
        lv_comment.setAdapter(adapter);
    }

    private void initView() {
        tv_createdAt = (TextView) findViewById(R.id.tv_createdAt);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_content = (TextView) findViewById(R.id.tv_content);
        lv_comment = (Comment_CircleListView) findViewById(R.id.lv_comment);
    }

    public void htmlWebPic(final String htmlContent) {
        Thread t = new Thread(new Runnable() {
            Message msg = handler.obtainMessage();

            @Override
            public void run() {
                Html.ImageGetter imageGetter = new Html.ImageGetter() {
                    @Override
                    public Drawable getDrawable(String source) {
                        URL url = null;
                        Drawable drawable = null;
                        try {
                            url = new URL(source);
                            drawable = Drawable.createFromStream(
                                    url.openStream(), null);
                            int spe = dip2px(context, 10);
                            int width = ScreenUtil.getScreenwidth() - spe;
                            int height = width * drawable.getIntrinsicHeight() / drawable.getIntrinsicWidth();
                            drawable.setBounds(0, 0,
                                    width,
                                    height);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return drawable;
                    }
                };
                CharSequence result = Html.fromHtml(htmlContent, imageGetter, new GameTagHandler());
                msg.what = 0x12;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        });
        t.start();
    }

    public class GameTagHandler implements Html.TagHandler {

        @Override
        public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
            if (tag.toLowerCase(Locale.getDefault()).equals("img")) {
                // 获取长度
                int len = output.length();
                // 获取图片地址
                ImageSpan[] images = output.getSpans(len - 1, len, ImageSpan.class);
                String imgURL = images[0].getSource();

                // 使图片可点击并监听点击事件
                output.setSpan(new GameSpan(context, imgURL), len - 1, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    private class GameSpan extends ClickableSpan implements View.OnClickListener {
        private String url;
        private Context context;

        public GameSpan(Context context, String url) {
            this.context = context;
            this.url = url;
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, "图片点击" + url, Toast.LENGTH_SHORT).show();
            Log.i("图片", url);

        }
    }

    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            if (msg.what == 0x12) {
                tv_content.setText((CharSequence) msg.obj);
                tv_content.setMovementMethod(LinkMovementMethod.getInstance());
            }
            return false;
        }
    });

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
