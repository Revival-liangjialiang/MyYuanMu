package com.example.yuanmu.lunbo.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yuanmu.lunbo.Activity.AddFriend;
import com.example.yuanmu.lunbo.Activity.FriendActivity;
import com.example.yuanmu.lunbo.R;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * Created by Administrator on 2016/8/25 0025.
 */
public class News_Fragment extends Fragment implements View.OnClickListener {
    private ViewGroup view;
    RelativeLayout mSearch_layout;
    TextView mFriends_TV;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (ViewGroup) inflater.from(container.getContext()).inflate(R.layout.news_fragment_layout, container, false);
        mFriends_TV = (TextView) view.findViewById(R.id.m_friends_TV);
        mFriends_TV.setOnClickListener(this);
        mSearch_layout = (RelativeLayout) view.findViewById(R.id.m_search_layout);
        mSearch_layout.setOnClickListener(this);
        init();
        return view;
    }

    private void init() {
        ConversationListFragment fragment = new ConversationListFragment();
        Uri uri = Uri
                .parse("rong://"
                        + getActivity().getApplicationInfo().packageName)
                .buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(
                        Conversation.ConversationType.PRIVATE.getName(),
                        "false") // 设置私聊会话非聚合显示
                .appendQueryParameter(
                        Conversation.ConversationType.GROUP.getName(), "true")// 设置群组会话聚合显示
                .appendQueryParameter(
                        Conversation.ConversationType.DISCUSSION.getName(),
                        "false")// 设置讨论组会话非聚合显示
                .appendQueryParameter(
                        Conversation.ConversationType.SYSTEM.getName(), "false")// 设置系统会话非聚合显示
                .build();
        fragment.setUri(uri);

        FragmentTransaction transaction = getActivity()
                .getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.conversationlist, fragment);
        transaction.commit();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.m_search_layout:
                startActivity(new Intent(getActivity(), AddFriend.class));
                break;
            case R.id.m_friends_TV:
                startActivity(new Intent(getActivity(), FriendActivity.class));
                break;
        }
    }
}
