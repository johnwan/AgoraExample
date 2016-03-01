package com.agora.example.longjiao.agoraexample.Model;

import com.agora.example.longjiao.agoraexample.BaseEngineHandlerActivity;

import io.agora.rtc.IRtcEngineEventHandler;

/**
 * Created by apple on 15/9/16.
 */
public class MessageHandler extends IRtcEngineEventHandler {

    private BaseEngineHandlerActivity mHandlerActivity;

    public void setActivity(BaseEngineHandlerActivity activity) {

        this.mHandlerActivity = activity;
    }

    public BaseEngineHandlerActivity getActivity(){

        return mHandlerActivity;
    }

    //返回错误
    @Override
    public void onError(int err){

        BaseEngineHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onError(err);
        }
    }

    //加入房间
    @Override
    public void onJoinChannelSuccess(String channel, int uid, int elapsed){

        BaseEngineHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onJoinChannelSuccess(channel, uid, elapsed);
        }
    }

    //显示房间内其他用户的视频
    @Override
    public void onFirstRemoteVideoDecoded(int uid, int width, int height, int elapsed) {

        BaseEngineHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onFirstRemoteVideoDecoded(uid, width, height, elapsed);
        }
    }

    //用户进入
    @Override
    public void onUserJoined(int uid, int elapsed){

        BaseEngineHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onUserJoined(uid, elapsed);
        }
    }

    //用户退出
    @Override
    public void onUserOffline(int uid, int reason) {

        BaseEngineHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onUserOffline(uid);
        }
    }

    //监听其他用户是否关闭视频
    @Override
    public void onUserMuteVideo(int uid,boolean muted){

        BaseEngineHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onUserMuteVideo(uid, muted);
        }
    }

    //监听其他用户是否关闭音频
    @Override
    public void onUserMuteAudio(int uid,boolean muted){

        BaseEngineHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onUserMuteAudio(uid, muted);
        }
    }

    //监听网络质量
    @Override
    public void onNetworkQuality(int quality){

        BaseEngineHandlerActivity activity = getActivity();

        if (activity != null){
            activity.onNetworkQuality(quality);
        }
    }

    //监听通话质量
    @Override
    public void onAudioQuality(int uid,int quality,short delay,short lost){

        BaseEngineHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onAudioQuality(uid,quality,delay,lost);
        }
    }

    //更新聊天数据
    @Override
    public void onRtcStats(RtcStats stats){

        BaseEngineHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onUpdateSessionStats(stats);
        }
    }

    //离开频道
    @Override
    public void onLeaveChannel(RtcStats stats) {

        BaseEngineHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onLeaveChannel(stats);
        }
    }


}
