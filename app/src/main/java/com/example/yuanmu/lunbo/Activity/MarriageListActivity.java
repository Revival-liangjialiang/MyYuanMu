package com.example.yuanmu.lunbo.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.yuanmu.lunbo.Adapter.MarriageListAdapter;
import com.example.yuanmu.lunbo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuanmu on 2016/9/10.
 */
public class MarriageListActivity extends Activity {
    private RecyclerView rv_1;
    private MarriageListAdapter adapter;
    private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    private Map<String, Object> map;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marriagelist);
        context = MarriageListActivity.this;
        initView();
        initData();
        getData();
    }

    private void initData() {
        adapter = new MarriageListAdapter(context, list);
        rv_1.setAdapter(adapter);
        rv_1.setLayoutManager(new
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

    private void initView() {
        rv_1 = (RecyclerView) findViewById(R.id.rv_1);
    }

    private void getData() {
        list.clear();
        map = new HashMap<String, Object>();
        map.put("img", "http://b.hiphotos.baidu.com/image/h%3D360/sign=4966caee48086e0675a8394d320a7b5a/023b5bb5c9ea15cec72cb6d6b2003af33b87b22b.jpg");
        map.put("content", "有哪位帅哥想认识我的吗，快来找我哟。");
        map.put("objectId", "1");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("img", "http://f.hiphotos.baidu.com/image/h%3D360/sign=b815d604b7b7d0a264c9029bfbee760d/b2de9c82d158ccbf79a00f8c1cd8bc3eb1354163.jpg");
        map.put("content", "哥哥，我好寂寞，你懂得，加我扣扣。");
        map.put("objectId", "2");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("img", "http://f.hiphotos.baidu.com/image/h%3D360/sign=c8c358234b540923b5696578a259d1dc/dcc451da81cb39dbf38fc156d2160924ab183066.jpg");
        map.put("content", "我也是一名老司机，快来加我为好友吧。");
        map.put("objectId", "4");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("img", "http://f.hiphotos.baidu.com/image/h%3D360/sign=093225e4a1cc7cd9e52d32df09002104/32fa828ba61ea8d358824a0d950a304e251f5812.jpg");
        map.put("content", "记住我的网址，三大不留点艾特点西欧恩。");
        map.put("objectId", "3");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("img", "http://a.hiphotos.baidu.com/image/h%3D360/sign=89a4a05d57fbb2fb2b2b5e147f4a2043/a044ad345982b2b700e891c433adcbef76099bbf.jpg");
        map.put("content", "爱我，你敢吗。");
        map.put("objectId", "5");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("img", "http://f.hiphotos.baidu.com/image/h%3D360/sign=f6007fc08301a18befeb1449ae2d0761/8644ebf81a4c510fa42d1bf66459252dd52aa575.jpg");
        map.put("content", "花儿为你而开");
        map.put("objectId", "6");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("img", "http://c.hiphotos.baidu.com/image/h%3D360/sign=21a755bfa41ea8d395227202a70b30cf/43a7d933c895d143bf16062771f082025aaf0755.jpg");
        map.put("content", "你大爷，有种单挑，不服来战。");
        map.put("objectId", "7");
        list.add(map);
        adapter.notifyDataSetChanged();
//        // User user = BmobUser.getCurrentUser(User.class);
//        BmobQuery<Cabinet> query = new BmobQuery<Cabinet>();
//        // query.addWhereEqualTo("author", user); // 查询当前用户的所有帖子
//        query.order("-updatedAt");
//        query.include("author");// 希望在查询帖子信息的同时也把发布人的信息查询出来
//        query.findObjects(new FindListener<Cabinet>() {
//
//            @Override
//            public void done(List<Cabinet> object, BmobException e) {
//                if (e == null) {
//                    for (int i = 0; i < object.size(); i++) {
////						String img = object.get(i).getAuthor().getImg();
//                        String content = object.get(i).getContent();
//                        String objectId = object.get(i).getObjectId();
////						List<String> imgarray = object.get(i).getImgarray();
//                        String item_imgarray = object.get(i).getImgarray().get(0);
////						Log.i("数组", imgarray.size() + "");
//                        map = new HashMap<String, Object>();
////						map.put("img", img);
//                        map.put("content", content);
//                        map.put("objectId", objectId);
////						map.put("imgarray", imgarray);
//                        map.put("item_imgarray", item_imgarray);
//                        list.add(map);
//                    }
//                    adapter.notifyDataSetChanged();
//                } else {
//                }
//            }

//        });
    }
}
