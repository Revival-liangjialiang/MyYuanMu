package com.example.yuanmu.lunbo.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.example.yuanmu.lunbo.Activity.LoveActivity;
import com.example.yuanmu.lunbo.Activity.Lovematch;
import com.example.yuanmu.lunbo.Activity.MainActivity;
import com.example.yuanmu.lunbo.Activity.StoryActivity;
import com.example.yuanmu.lunbo.Adapter.HomePageAdapter;
import com.example.yuanmu.lunbo.Application.MyApplication;
import com.example.yuanmu.lunbo.BmobBean.Carousel;
import com.example.yuanmu.lunbo.BmobBean.CircleComment;
import com.example.yuanmu.lunbo.BmobBean.Lifecircle;
import com.example.yuanmu.lunbo.BmobBean.Reply;
import com.example.yuanmu.lunbo.BmobBean.User;
import com.example.yuanmu.lunbo.Custom.MyView;
import com.example.yuanmu.lunbo.Custom.StoryListView;
import com.example.yuanmu.lunbo.R;
import com.example.yuanmu.lunbo.Util.MyLog;
import com.example.yuanmu.lunbo.Util.VolleyRequest;
import com.jingchen.pulltorefresh.PullToRefreshLayout;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.hintview.IconHintView;
import com.victor.loading.rotate.RotateLoading;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2016/8/25 0025.
 */
public class HomePage_Fragment extends Fragment implements View.OnClickListener {
    int o = 0;
    //加载布局
    RelativeLayout mLoading_layout;
    RotateLoading rotateLoading;
    boolean loadSwitch = true,mStartActivitySwitch = true;

    //生活圈字段
    StoryListView mHomePage_lv;
    private HomePageAdapter adapter;
    //文章集合
    private List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
    //评论集合
    private Map<String,List> mCommentListMap = new HashMap<>();
    //回复集合
    private Map<String,List> mReplyListMap = new HashMap<>();
    private Map<String, Object> map;
    private List<List> mCircleCommentList = new ArrayList<>();
    private String articleId;
    private String mQueryArticleId;
    private int mCommentValue = 0;
    private String mStartCommentId;
    private int mCommentCount = 0,mCommentCountCopy = 0;

    public int mDownload_complete_switch = 0;
    String[] mAddressTop,mAddressBottom;
    VolleyRequest mRequestTop,mRequestBottom;
    //上面轮播图
    ImageView[] mImageViewArrayTop = new ImageView[3];
    //下面轮播图
    ImageView[] mImageViewArrayBottom = new ImageView[3];
    HomePage_Fragment.ClickListenerClass mListenerClass = new HomePage_Fragment.ClickListenerClass();
    private RollPagerView mRollViewPagerTop,mRollViewPagerBottom;
    PullToRefreshLayout mPullToRefreshLayout;
    MainActivity m;
    MyView mLove_match, mLoveActivity, mStorytelling_session, mSearch_in_figure;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mLoading_layout.setVisibility(View.GONE);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.from(container.getContext()).inflate(R.layout.home_page_fragment_layout, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        m = (MainActivity) getActivity();
        mRequestTop = new VolleyRequest(getContext());
        mRequestBottom = new VolleyRequest(getContext());
        setPictureListenerTop();
        setPictureListenerBottom();
        getRequestPictureAddressTop();
        getRequestPictureAddressBottom();
        initView();
        initRollViewPager();
        initData();
        getData();
    }

    private void setPictureListenerBottom() {
        mRequestBottom.PictureRequestListener(new VolleyRequest.PictureRequestListener() {
            @Override
            public void onRessponse(Bitmap bitmap, String str) {
                m.mLruCache.put(str, bitmap);
            }

            @Override
            public void onFinish() {
                Log.i("ok", "调用完成方法！");
                requestFinishBottom(mImageViewArrayBottom);
            }
        });
    }

    private void requestFinishBottom(ImageView[] mImageViewArrayBottom) {
        if(mImageViewArrayBottom == null){
            return;
        }
        for (int a = 0; a < 3; a++) {
            if (mImageViewArrayBottom[a] == null) {
                return;
            }
            if (m.mLruCache != null && mAddressBottom != null) {
                if (m.mLruCache.get(mAddressBottom[a]) != null) {
                    if(mAddressTop!=null) {
                        mImageViewArrayBottom[a].setImageBitmap(m.mLruCache.get(mAddressTop[a]));
                    }
                } else {
                    if(mAddressBottom!=null) {
                        MyStartRequestBottom(mImageViewArrayBottom[a], mAddressBottom[a]);
                    }
                }
            } else {
            }
        }
    }

    private void initRollViewPager() {
        //初始化顶部轮播图
        mRollViewPagerTop = (RollPagerView) m.findViewById(R.id.roll_view_pager_top);
        mRollViewPagerTop.setAnimationDurtion(500);
        mRollViewPagerTop.setAdapter(new TestLoopAdapter_top(mRollViewPagerTop));
        mRollViewPagerTop.setHintView(new IconHintView(m, R.drawable.point_focus, R.drawable.point_normal));
        //初始化中部轮播图
        mRollViewPagerBottom = (RollPagerView) m.findViewById(R.id.roll_view_pager_bottom);
        mRollViewPagerBottom.setAnimationDurtion(200);
        mRollViewPagerBottom.setPlayDelay(1000);
        mRollViewPagerBottom.setAdapter(new TestLoopAdapter_bottom(mRollViewPagerBottom));
        mRollViewPagerBottom.setHintView(new IconHintView(m, R.drawable.point_focus, R.drawable.point_normal));
    }

    private void setPictureListenerTop() {
        mRequestTop.PictureRequestListener(new VolleyRequest.PictureRequestListener() {
            @Override
            public void onRessponse(Bitmap bitmap, String str) {
                m.mLruCache.put(str, bitmap);
            }

            @Override
            public void onFinish() {
                Log.i("ok", "调用完成方法！");
                requestFinishTop(mImageViewArrayTop);
            }
        });
    }


    private void getRequestPictureAddressTop() {
        BmobQuery<Carousel> query = new BmobQuery<>();
        query.findObjects(new FindListener<Carousel>() {
            @Override
            public void done(List<Carousel> list, BmobException e) {
                if (e == null) {
                    Log.i("ok", "请求Carousel队列成功！长度等于 = " + list.size());
                    mDownload_complete_switch = list.size();
                    mAddressTop = new String[mDownload_complete_switch];
                    for (int a = 0; a < list.size(); a++) {
                        Carousel c = list.get(a);
                        mAddressTop[a] = c.getImg_address();
                        Log.i("ok", "遍历address" + mAddressTop[a]);
                    }
                    mRequestTop.startRequest(mAddressTop);
                } else {
                    Log.i("ok", "请求Carousel队列失败！");
                }
            }
        });
    }


    private void initView() {
        rotateLoading = (RotateLoading) m.findViewById(R.id.rotateloading);
        mLoading_layout = (RelativeLayout) m.findViewById(R.id.loading_layout);
        mHomePage_lv = (StoryListView) m.findViewById(R.id.mHomePage_lv);
        mLove_match = (MyView) m.findViewById(R.id.love_match);
        mLoveActivity = (MyView) m.findViewById(R.id.love_activity);
        mStorytelling_session = (MyView) m.findViewById(R.id.storytelling_session);
        mSearch_in_figure = (MyView) m.findViewById(R.id.search_in_figure);
        mLove_match.bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.myviewtest);
        mLoveActivity.bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.myviewtest);
        mStorytelling_session.bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.myviewtest);
        mSearch_in_figure.bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.myviewtest);
        mLove_match.postInvalidate();
        mLoveActivity.postInvalidate();
        mStorytelling_session.postInvalidate();
        mSearch_in_figure.postInvalidate();
        mLove_match.setOnClickListener(this);
        mLoveActivity.setOnClickListener(this);
        mStorytelling_session.setOnClickListener(this);
        mSearch_in_figure.setOnClickListener(this);
        mLoading_layout.setOnClickListener(this);
        //初始化下拉控件
        mPullToRefreshLayout = ((PullToRefreshLayout) m.findViewById(R.id.refresh_view));
        mPullToRefreshLayout.setOnPullListener(new MyPullListener());
        //加载更多的监听
        mPullToRefreshLayout.setOnLoadmoreProcessListener(new MyOnPullProcessListener_Top());
        //下拉刷新的监听
        mPullToRefreshLayout.setOnRefreshProcessListener(new MyOnPullProcessListener_Bottom());
        Log.i("初始化View完成！", "");
    }

    public void getRequestPictureAddressBottom() {
        BmobQuery<Carousel> query = new BmobQuery<>();
        query.findObjects(new FindListener<Carousel>() {
            @Override
            public void done(List<Carousel> list, BmobException e) {
                if (e == null) {
                    Log.i("ok", "下面请求Carousel队列成功！长度等于 = " + list.size());
                    mDownload_complete_switch = list.size();
                    mAddressBottom = new String[mDownload_complete_switch];
                    for (int a = 0; a < list.size(); a++) {
                        Carousel c = list.get(a);
                        mAddressBottom[a] = c.getImg_address();
                        Log.i("ok", "遍历address" + mAddressBottom[a]);
                    }
                } else {
                    Log.i("ok", "请求Carousel队列失败！");
                }
                Log.i("ok", "开始请求图片！address_top = " + mAddressBottom);
                mRequestBottom.startRequest(mAddressBottom);
            }
        });

    }

    private class TestLoopAdapter_top extends LoopPagerAdapter {

        public TestLoopAdapter_top(RollPagerView viewPager) {
            super(viewPager);
        }

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            mImageViewArrayTop[position] = view;
            view.setId(position);
            view.setOnClickListener(HomePage_Fragment.this);
            if (m.mLruCache != null && mAddressTop != null) {
                if (m.mLruCache.get(mAddressTop[position]) != null) {
                    view.setImageBitmap(m.mLruCache.get(mAddressTop[position]));
                } else {
                    MyStartRequestTop(view, mAddressTop[position]);
                }
            } else {
                view.setImageResource(R.mipmap.null_picture);
            }
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getRealCount() {
            return 3;
        }

    }

    private class TestLoopAdapter_bottom extends LoopPagerAdapter {


        public TestLoopAdapter_bottom(RollPagerView viewPager) {
            super(viewPager);
        }

        @Override
        public View getView(ViewGroup container, int position) {
            Log.i("ok", "加载下面轮播图！position = " + position);
            ImageView view = new ImageView(container.getContext());
            mImageViewArrayBottom[position] = view;
            view.setId(position);
            view.setOnClickListener(mListenerClass);
            if (m.mLruCache != null && mAddressBottom != null) {
                if (m.mLruCache.get(mAddressBottom[position]) != null) {
                    view.setImageBitmap(m.mLruCache.get(mAddressBottom[position]));
                } else {
                    MyStartRequestBottom(view, mAddressBottom[position]);
                }
            } else {
                view.setImageResource(R.mipmap.null_picture_bottom);
            }
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            Log.i("ok", "加载下面轮播图完成！ position = " + position);
            return view;
        }

        @Override
        public int getRealCount() {
            return 3;
        }

    }

    //图片点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case 0:
                Toast.makeText(getContext(), "1", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(getContext(), "2", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(getContext(), "3", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(getContext(), "4", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(getContext(), "5", Toast.LENGTH_SHORT).show();
                break;
            //爱匹配点击
            case R.id.love_match:
                mStartActivitySwitch = true;
                if(loadSwitch) {
                    loadSwitch = false;
                    rotateLoading.start();
                    mLoading_layout.setVisibility(View.VISIBLE);
                    BmobQuery<User> query = new BmobQuery<User>();
                    query.addWhereEqualTo("city", "广州市");
                    query.setLimit(20);
                    query.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> object, BmobException e) {
                            if (e == null) {
                                if(mStartActivitySwitch) {
                                    // TODO: 2016/10/13 0013
                                    loadSwitch = true;
                                    Intent love_match_intent = new Intent(m, Lovematch.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("list", (Serializable) object);
                                    love_match_intent.putExtras(bundle);
                                    startActivity(love_match_intent);
                                    rotateLoading.stop();
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                Thread.sleep(700);
                                                handler.sendMessage(new Message());
                                            } catch (InterruptedException e1) {
                                                e1.printStackTrace();
                                            }
                                        }
                                    }).start();
                                }
                            } else {
                                Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                            }
                        }
                    });
                }
                break;
            //爱生活点击
            case R.id.love_activity:
                Intent love_activity_intent = new Intent(m, LoveActivity.class);
                startActivity(love_activity_intent);
                break;
            //故事会
            case R.id.storytelling_session:
                Intent storytelling_session_intent = new Intent(m, StoryActivity.class);
                startActivity(storytelling_session_intent);
                break;
            //图中寻
            case R.id.search_in_figure:
                break;
            case R.id.loading_layout:
               // 先关加载器，一秒后再关布局
                rotateLoading.stop();
                loadSwitch = true;
                mStartActivitySwitch = false;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(700);
                            Message message = new Message();
                            handler.sendMessage(message);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            default:
                break;
        }
    }

    //上拉
    public class MyOnPullProcessListener_Top implements PullToRefreshLayout.OnPullProcessListener {
        //一下拉第一个调用的方法调用一次
        @Override
        public void onPrepare(View v, int which) {
        }

        //当触发了刷新操作就会被调用一次
        @Override
        public void onStart(View v, int which) {
        }

        //当下拉刷新已经被触发后，在放开的时候就会被调用一次
        @Override
        public void onHandling(View v, int which) {

        }

        //刷新完成的时候调用一次
        @Override
        public void onFinish(View v, int which) {

            Toast.makeText(getContext(), "上拉刷新完成！", Toast.LENGTH_SHORT).show();
        }

        //一直往下拉就会不断连续的调用
        @Override
        public void onPull(View v, float pullDistance, int which) {

        }

    }

    //下拉监听
    public class MyOnPullProcessListener_Bottom implements PullToRefreshLayout.OnPullProcessListener {
        //一下拉第一个调用的方法调用一次
        @Override
        public void onPrepare(View v, int which) {

        }

        //当触发了刷新操作就会被调用一次
        @Override
        public void onStart(View v, int which) {

        }

        //当下拉刷新已经被触发后，在放开的时候就会被调用一次
        @Override
        public void onHandling(View v, int which) {

        }

        //刷新完成的时候调用一次
        @Override
        public void onFinish(View v, int which) {

            Toast.makeText(getContext(), "下拉刷新完成！", Toast.LENGTH_SHORT).show();
        }

        //一直往下拉就会不断连续的调用
        @Override
        public void onPull(View v, float pullDistance, int which) {

        }

    }

    class MyPullListener implements PullToRefreshLayout.OnPullListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            Log.i("ok", "下拉手已松开！");
            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // 千万别忘了告诉控件刷新完毕了哦！
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 1500);
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            Log.i("ok", ">>>>>>>>>>>>>>>>>>>>>>>上拉手已松开！");
            // 加载操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 1500);
        }

    }

    //中部轮播图的点击类
    class ClickListenerClass implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case 0:
                    Toast.makeText(getContext(), "中部轮播图1", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(getContext(), "中部轮播图2", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(getContext(), "中部轮播图3", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(getContext(), "中部轮播图4", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(getContext(), "中部轮播图5", Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    Toast.makeText(getContext(), "中部轮播图6", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }

    void requestFinishTop(ImageView[] img_array) {
        for (int a = 0; a < 3; a++) {
            if (img_array[a] == null) {
                return;
            }
            if (m.mLruCache != null && mAddressTop != null) {
                if (m.mLruCache.get(mAddressTop[a]) != null) {
                    img_array[a].setImageBitmap(m.mLruCache.get(mAddressTop[a]));
                } else {
                    MyStartRequestTop(img_array[a], mAddressTop[a]);
                }
            } else {
            }
        }
    }
    private void MyStartRequestTop(final ImageView view, final String address) {
        mRequestTop.mRequestQueue.add(new ImageRequest(address, new Response.Listener<Bitmap>() {
            String str = address;
            @Override
            public void onResponse(Bitmap bitmap) {
                view.setImageBitmap(bitmap);
                m.mLruCache.put(str, bitmap);
            }
        }, 720, 0, ImageView.ScaleType.CENTER_CROP, null, null));
    }
    private void MyStartRequestBottom(final ImageView view, final String address) {
        mRequestBottom.mRequestQueue.add(new ImageRequest(address, new Response.Listener<Bitmap>() {
            String str = address;
            @Override
            public void onResponse(Bitmap bitmap) {
                view.setImageBitmap(bitmap);
                m.mLruCache.put(str, bitmap);
            }
        }, 720, 0, ImageView.ScaleType.CENTER_CROP, null, null));
    }
    private void initData() {
        adapter = new HomePageAdapter(getActivity(), mList,mCommentListMap,mReplyListMap);
        mHomePage_lv.setAdapter(adapter);
    }
    public void getData() {
        mCircleCommentList.clear();
        mReplyListMap.clear();
        mCommentListMap.clear();
        mList.clear();
        //查询文章
        BmobQuery<Lifecircle> query = new BmobQuery<Lifecircle>();
        query.order("-createdAt");
        query.setLimit(10);
        query.include("user");// 希望在查询帖子信息的同时也把发布人的信息查询出来
        query.findObjects(new FindListener<Lifecircle>() {
            @Override
            public void done(List<Lifecircle> object, BmobException e) {
                //2
                if (e == null) {
                    Log.i("主页", object.size() + "");
                    for (int i = 0; i < object.size(); i++) {
                        //获取文章ID
                        articleId = object.get(i).getObjectId();
                        String nickname = object.get(i).getUser()
                                .getNickname();
                        String img = object.get(i).getUser().getImg();
                        String content = object.get(i).getContent();
                        String objectId = object.get(i).getObjectId();
                        String createdAt = object.get(i).getCreatedAt();
                        Date date = null;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            date = sdf.parse(createdAt);
                            MyLog.i("value","date = "+date);
                        } catch (ParseException el) {
                            el.printStackTrace();
                        }
                        List<String> imgarray = object.get(i).getImgarray();
                        List<String> commentarray = object.get(i)
                                .getCommentarray();
                        map = new HashMap<String, Object>();
                        map.put("id",articleId);
                        map.put("nickname", nickname);
                        map.put("img", img);
                        map.put("content", content);
                        map.put("objectId", objectId);
                        map.put("createdAt", createdAt);
                        map.put("imgarray", imgarray);
                        map.put("commentarray", commentarray);
                        Toast.makeText(MyApplication.getContext(), "加载文章成功!", Toast.LENGTH_SHORT).show();
                        mList.add(map);
                    }
                    //查询文章的评论
                    startCommentQuery();
                    Log.i("主页", mList.size() + "");
                    adapter.notifyDataSetChanged();
                } else {
                    Log.i("问题", e + "");
                }
            }

        });
    }
    public void startCommentQuery(){
        adapter.notifyDataSetChanged();
        Toast.makeText(MyApplication.getContext(), "启动文章评论请求!", Toast.LENGTH_SHORT).show();
        //查询文章的评论
        for(int a = 0;a<mList.size();a++) {
            mQueryArticleId = (String) mList.get(a).get("id");
            BmobQuery<CircleComment> query = new BmobQuery<CircleComment>();
            query.include("user");
            query.addWhereEqualTo("belong", mQueryArticleId);
            query.findObjects(new FindListener<CircleComment>() {
                //文章ID
                String articleIdCopy = mQueryArticleId;
                @Override
                public void done(final List<CircleComment> list, BmobException e) {
                    //有多少文章就会加多少次
                    if (e == null) {
                        mCommentValue++;
                        //将一篇文章的所有的评论放进去，待请求回复用
                        mCircleCommentList.add(list);
                        //存放文章的评论,索引是文章的objectId
                        MyLog.i("value","存放评论信息数量为："+list.size());
                        mCommentListMap.put(articleIdCopy, list);
                        if(mCommentValue == mList.size()){
                            startReplyQuery();
                        }
                    } else {
                        Log.i("ppp","请求文章错误信息为:"+e);
                    }
                }
            });
        }
        //------------------
    }
    public void startReplyQuery(){
        //继续进行查询评论回复
        for(int a = 0;a<mCircleCommentList.size();a++) {
            //取出某个文章的评论进行查询
            List<CircleComment> list = mCircleCommentList.get(a);
            for(int b = 0;b<list.size();b++) {
                mStartCommentId = list.get(b).getObjectId();
                BmobQuery<Reply> query = new BmobQuery<>();
                query.addWhereEqualTo("belong",mStartCommentId);
                query.findObjects(new FindListener<Reply>() {
                    String id = mStartCommentId;
                    @Override
                    public void done(List<Reply> list, BmobException e) {
                        if(e == null) {
                            mCommentCountCopy++;
                            mReplyListMap.put(id, list);
                            //当相等时，可以进行适配器刷新操作!
                            if(mCommentCount==mCommentCountCopy){
                                adapter.notifyDataSetChanged();
                                mCommentCountCopy = 0;
                                mCommentCount = 0;
                                mCommentValue = 0;
                            }
                        }else{
                            Toast.makeText(MyApplication.getContext(), "QueryReplyFail!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mCommentCount++;
            }
        }
        //----------------------
    }
}
