package com.example.yuanmu.lunbo.Other;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.yuanmu.lunbo.Activity.AMAPLocationActivity;
import com.example.yuanmu.lunbo.BmobBean.User;

import cn.bmob.v3.BmobQuery;
import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.widget.provider.ImageInputProvider;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imkit.widget.provider.LocationInputProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.GroupNotificationMessage;
import io.rong.message.ImageMessage;
import io.rong.message.LocationMessage;

/**
 * Created by yuanmu on 2016/10/7.
 */
public class SealAppContext implements RongIM.LocationProvider,RongIMClient.OnReceiveMessageListener, RongIM.ConversationBehaviorListener{
    private static SealAppContext mRongCloudInstance;
    private LocationCallback mLastLocationCallback;
    private Context mContext;
    public SealAppContext(Context mContext) {
        this.mContext = mContext;
        initListener();
    }
    /**
     * 初始化 RongCloud.
     *
     * @param context 上下文。
     */
    public static void init(Context context) {

        if (mRongCloudInstance == null) {

            synchronized (SealAppContext.class) {

                if (mRongCloudInstance == null) {
                    mRongCloudInstance = new SealAppContext(context);
                }
            }
        }

    }
    /**
     * 获取RongCloud 实例。
     *
     * @return RongCloud。
     */
    public static SealAppContext getInstance() {
        return mRongCloudInstance;
    }
    @Override
    public void onStartLocation(Context context, LocationCallback locationCallback) {
        SealAppContext.getInstance().setLastLocationCallback(locationCallback);
        Intent intent = new Intent(context, AMAPLocationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * init 后就能设置的监听
     */
    private void initListener() {
        RongIM.setConversationBehaviorListener(this);//设置会话界面操作的监听器。
        RongIM.setLocationProvider(this);//设置地理位置提供者,不用位置的同学可以注掉此行代码
        setInputProvider();
        setReadReceiptConversationType();
    }
    private void setReadReceiptConversationType() {
        Conversation.ConversationType[] types = new Conversation.ConversationType[] {
                Conversation.ConversationType.PRIVATE,
                Conversation.ConversationType.GROUP,
                Conversation.ConversationType.DISCUSSION
        };
        RongIM.getInstance().setReadReceiptConversationTypeList(types);
    }

    private void setInputProvider() {

        RongIM.setOnReceiveMessageListener(this);
//        RongIM.setConnectionStatusListener(this);

        InputProvider.ExtendProvider[] singleProvider =  {
                new ImageInputProvider(RongContext.getInstance()),
                new RealTimeLocationInputProvider(RongContext.getInstance()), //带位置共享的地理位置
//                new FileInputProvider(RongContext.getInstance())//文件消息
        };

        InputProvider.ExtendProvider[] muiltiProvider = {
                new ImageInputProvider(RongContext.getInstance()),
                new LocationInputProvider(RongContext.getInstance()),//地理位置
//                new FileInputProvider(RongContext.getInstance())//文件消息
        };

        RongIM.resetInputExtensionProvider(Conversation.ConversationType.PRIVATE, singleProvider);
        RongIM.resetInputExtensionProvider(Conversation.ConversationType.DISCUSSION, muiltiProvider);
        RongIM.resetInputExtensionProvider(Conversation.ConversationType.CUSTOMER_SERVICE, muiltiProvider);
        RongIM.resetInputExtensionProvider(Conversation.ConversationType.GROUP, muiltiProvider);
        RongIM.resetInputExtensionProvider(Conversation.ConversationType.CHATROOM, muiltiProvider);
    }
    public LocationCallback getLastLocationCallback() {
        return mLastLocationCallback;
    }

    public void setLastLocationCallback(LocationCallback lastLocationCallback) {
        this.mLastLocationCallback = lastLocationCallback;
    }

    @Override
    public boolean onReceived(Message message, int i) {
        MessageContent messageContent = message.getContent();
       /* if (messageContent instanceof ContactNotificationMessage) {
            ContactNotificationMessage contactNotificationMessage = (ContactNotificationMessage) messageContent;
            if (contactNotificationMessage.getOperation().equals("Request")) {
                //对方发来好友邀请
                BroadcastManager.getInstance(mContext).sendBroadcast(SealAppContext.UPDATE_RED_DOT);
            } else if (contactNotificationMessage.getOperation().equals("AcceptResponse")) {
                //对方同意我的好友请求
                ContactNotificationMessageData c = null;
                try {
                    c = JsonMananger.jsonToBean(contactNotificationMessage.getExtra(), ContactNotificationMessageData.class);
                } catch (HttpException e) {
                    e.printStackTrace();
                }
                if (c != null) {
                    DBManager.getInstance(mContext).getDaoSession().getFriendDao().insertOrReplace(new Friend(contactNotificationMessage.getSourceUserId(), c.getSourceUserNickname(), null, null, null, null));
                }
                BroadcastManager.getInstance(mContext).sendBroadcast(UPDATE_FRIEND);
                BroadcastManager.getInstance(mContext).sendBroadcast(SealAppContext.UPDATE_RED_DOT);
            }
//                // 发广播通知更新好友列表
//            BroadcastManager.getInstance(mContext).sendBroadcast(UPDATE_RED_DOT);
//            }
        } else*/ if (messageContent instanceof GroupNotificationMessage) {
            GroupNotificationMessage groupNotificationMessage = (GroupNotificationMessage) messageContent;
            if (groupNotificationMessage.getOperation().equals("Kicked")) {
            } else if (groupNotificationMessage.getOperation().equals("Add")) {
            } else if (groupNotificationMessage.getOperation().equals("Quit")) {
            } else if (groupNotificationMessage.getOperation().equals("Rename")) {
            }

        } else if (messageContent instanceof ImageMessage) {
            ImageMessage imageMessage = (ImageMessage) messageContent;
            Log.e("imageMessage", imageMessage.getRemoteUri().toString());
        }
        return false;
    }

    @Override
    public boolean onUserPortraitClick(final Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
        if (userInfo != null) {
            BmobQuery<User> query = new BmobQuery<>();
            query.addWhereEqualTo("","");
        /*    query.getObject(userInfo.getUserId(), new QueryListener<User>() {
                @Override
                public void done(User user, BmobException e) {
               if(e == null){
                   //对聊的时候，点击头像打开用户资料活动
                   Intent intent = new Intent(mContext, UserDataActivity.class);
                   intent.putExtra(UserDataActivity.USER_NAME,user.getNickname());
                   intent.putExtra(UserDataActivity.HEAD_PORTRAIT_ADDRESS,user.getImg());
                   mContext.startActivity(intent);
               }else{
                   MyLog.i("ljl","e = "+e);
               }
                }
            });*/
        }
        return true;
    }

    @Override
    public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
        return false;
    }

    @Override
    public boolean onMessageClick(final Context context, final View view, final Message message) {

        //real-time location message begin
//        if (message.getContent() instanceof RealTimeLocationStartMessage) {
//            RealTimeLocationConstant.RealTimeLocationStatus status = RongIMClient.getInstance().getRealTimeLocationCurrentState(message.getConversationType(), message.getTargetId());
//
////            if (status == RealTimeLocationConstant.RealTimeLocationStatus.RC_REAL_TIME_LOCATION_STATUS_IDLE) {
////                startRealTimeLocation(context, message.getConversationType(), message.getTargetId());
////            } else
//            if (status == RealTimeLocationConstant.RealTimeLocationStatus.RC_REAL_TIME_LOCATION_STATUS_INCOMING) {
//
//
//                final AlterDialogFragment alterDialogFragment = AlterDialogFragment.newInstance("", "加入位置共享", "取消", "加入");
//                alterDialogFragment.setOnAlterDialogBtnListener(new AlterDialogFragment.AlterDialogBtnListener() {
//
//                    @Override
//                    public void onDialogPositiveClick(AlterDialogFragment dialog) {
//                        RealTimeLocationConstant.RealTimeLocationStatus status = RongIMClient.getInstance().getRealTimeLocationCurrentState(message.getConversationType(), message.getTargetId());
//
//                        if (status == null || status == RealTimeLocationConstant.RealTimeLocationStatus.RC_REAL_TIME_LOCATION_STATUS_IDLE) {
//                            startRealTimeLocation(context, message.getConversationType(), message.getTargetId());
//                        } else {
//                            joinRealTimeLocation(context, message.getConversationType(), message.getTargetId());
//                        }
//
//                    }
//
//                    @Override
//                    public void onDialogNegativeClick(AlterDialogFragment dialog) {
//                        alterDialogFragment.dismiss();
//                    }
//                });
//
//                alterDialogFragment.show(((FragmentActivity) context).getSupportFragmentManager());
//            } else {
//
//                if (status != null && (status == RealTimeLocationConstant.RealTimeLocationStatus.RC_REAL_TIME_LOCATION_STATUS_OUTGOING || status == RealTimeLocationConstant.RealTimeLocationStatus.RC_REAL_TIME_LOCATION_STATUS_CONNECTED)) {
//
//                    Intent intent = new Intent(((FragmentActivity) context), RealTimeLocationActivity.class);
//                    intent.putExtra("conversationType", message.getConversationType().getValue());
//                    intent.putExtra("targetId", message.getTargetId());
//                    context.startActivity(intent);
//                }
//            }
//            return true;
//        }

        //real-time location message end
        /**
         * demo 代码  开发者需替换成自己的代码。
         */
        if (message.getContent() instanceof LocationMessage) {
            Intent intent = new Intent(context, AMAPLocationActivity.class);
            intent.putExtra("location", message.getContent());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else if (message.getContent() instanceof ImageMessage) {
//            Intent intent = new Intent(context, PhotoActivity.class);
//            intent.putExtra("message", message);
//            context.startActivity(intent);
        }

        return false;
    }

    @Override
    public boolean onMessageLinkClick(Context context, String s) {
        return false;
    }

    @Override
    public boolean onMessageLongClick(Context context, View view, Message message) {
        return false;
    }
}
