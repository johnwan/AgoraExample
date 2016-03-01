package com.agora.example.longjiao.agoraexample;

import android.app.AlertDialog;
import android.app.assist.AssistStructure;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;

public class ChannelActivity extends BaseEngineHandlerActivity {
    public final static String VENDER_KEY = "Put your vender key here";
    private RtcEngine rtcEngine;
    private SurfaceView mLocalView;
    private String mChannelName;
    private boolean mIsAdmin;
    private int userId=new Random().nextInt(Math.abs((int) System.currentTimeMillis()));
    private String callId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent data = this.getIntent();
        mChannelName = data.getStringExtra(MainActivity.CHANNEL_NAME);
        mIsAdmin = data.getBooleanExtra(MainActivity.IS_ADMIN,false);

        setContentView(R.layout.activity_channel);

        setupRtcEngine();

        initValues();

        ensureLocalViewIsCreated();

        rtcEngine.enableVideo();
        rtcEngine.muteLocalVideoStream(false);
        rtcEngine.muteLocalAudioStream(false);
        rtcEngine.muteAllRemoteVideoStreams(false);

        setupChannel();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rtcEngine.leaveChannel();
            }
        });
    }

    private void setupRtcEngine(){

        ((AgoraApplication) getApplication()).setRtcEngine(VENDER_KEY);


        rtcEngine=((AgoraApplication) getApplication()).getRtcEngine();

        ((AgoraApplication) getApplication()).setEngineHandlerActivity(this);

        rtcEngine.setLogFile(((AgoraApplication) getApplication()).getPath() + "/" + Integer.toString(Math.abs((int) System.currentTimeMillis())) + ".txt");
    }

    private void setupChannel(){

        rtcEngine.joinChannel(((AgoraApplication) getApplication()).getVendorKey(), mChannelName, "", userId);
    }

    public void onJoinChannelSuccess(String channel,final int uid,int elapsed){

        userId=uid;

        ((AgoraApplication)getApplication()).setIsInChannel(true);

        callId=rtcEngine.getCallId();

        ((AgoraApplication)getApplication()).setCallId(callId);
    }

    //Show Local
    private void ensureLocalViewIsCreated() {

        // local view has not been added before
        FrameLayout localViewContainer = (FrameLayout) findViewById(R.id.user_local_view);
        SurfaceView localView = rtcEngine.CreateRendererView(getApplicationContext());
        this.mLocalView = localView;
        localViewContainer.addView(localView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        rtcEngine.enableVideo();
        rtcEngine.setupLocalVideo(new VideoCanvas(this.mLocalView));
    }

    public void onLeaveChannel(final IRtcEngineEventHandler.RtcStats stats){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                ((AgoraApplication) getApplication()).setIsInChannel(false);


                finish();

                Intent i = new Intent(ChannelActivity.this, MainActivity.class);
                startActivity(i);

            }
        });
    }

    public synchronized void onFirstRemoteVideoDecoded(final int uid, int width, int height, final int elapsed) {
        if(!mIsAdmin) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    FrameLayout localViewContainer = (FrameLayout) findViewById(R.id.user_local_view);
                    final SurfaceView remoteView = RtcEngine.CreateRendererView(getApplicationContext());
                    localViewContainer.removeAllViews();
                    localViewContainer.addView(remoteView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

                    rtcEngine.enableVideo();
                    int successCode = rtcEngine.setupRemoteVideo(new VideoCanvas(remoteView, VideoCanvas.RENDER_MODE_ADAPTIVE, uid));

                    if (successCode < 0) {
                        new android.os.Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                rtcEngine.setupRemoteVideo(new VideoCanvas(remoteView, VideoCanvas.RENDER_MODE_ADAPTIVE, uid));
                                mLocalView.invalidate();
                            }
                        }, 1000);
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed(){

        rtcEngine.leaveChannel();

        // keep screen on - turned off
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void initValues(){
        ((AgoraApplication) getApplication()).setResolution(2);
        ((AgoraApplication) getApplication()).setRate(2);
        ((AgoraApplication) getApplication()).setFrame(2);
        ((AgoraApplication) getApplication()).setVolume(2);
        ((AgoraApplication)getApplication()).setTape(false);
        ((AgoraApplication)getApplication()).setFloat(false);
    }

}
