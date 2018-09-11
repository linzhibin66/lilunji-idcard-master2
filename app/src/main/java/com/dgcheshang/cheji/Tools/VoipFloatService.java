package com.dgcheshang.cheji.Tools;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import com.dgcheshang.cheji.R;


/**
 * Created by Administrator on 2018/6/4 0004.
 */

public class VoipFloatService extends Service {
    private static final String TAG="TAG";
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;

    View layout_showphoto;

/**
 * float的布局view
 */

    private View mFloatView;

    private GLSurfaceView glSurfaceView;

    private int mFloatWinWidth,mFloatWinHeight;//悬浮窗的宽高

    private int mFloatWinMarginTop,mFloatWinMarginRight;

    private int mLastX=0,mLastY=0;

    private int mStartX=0,mStartY=0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String data = intent.getStringExtra("type");
        Log.e("TAG","service="+data);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override

    public void onCreate() {

        super.onCreate();

        Log.e(TAG,"onCreate: ");

        createWindowManager();

        createFloatView();

    }

    @Override

    public void onDestroy() {
        super.onDestroy();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createWindowManager() {

        Log.e(TAG,"createWindowManager: ");

// 取得系统窗体

        mWindowManager= (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

//计算得出悬浮窗口的宽高

        DisplayMetrics metric =new DisplayMetrics();

        mWindowManager.getDefaultDisplay().getMetrics(metric);

        int screenWidth = metric.widthPixels;
        int heightPixels = metric.heightPixels;

        mFloatWinWidth = (int) (screenWidth );

        mFloatWinHeight=heightPixels;

//        mFloatWinMarginTop= (int)this.getResources().getDimension(R.dimen.rkcloud_av_floatwin_margintop);
//
//        mFloatWinMarginRight= (int)this.getResources().getDimension(R.dimen.rkcloud_av_floatwin_marginright);
//        mFloatWinWidth = 1;
//
//        mFloatWinHeight=1;
//
//        mFloatWinMarginTop=10;
//        mFloatWinMarginRight=10;

// 窗体的布局样式

// 获取LayoutParams对象

        mLayoutParams=new WindowManager.LayoutParams();

// 确定爱悬浮窗类型，表示在所有应用程序之上，但在状态栏之下

//TODO? 在android2.3以上可以使用TYPE_TOAST规避权限问题

        mLayoutParams.type= WindowManager.LayoutParams.TYPE_TOAST;//TYPE_PHONE

        mLayoutParams.format= PixelFormat.RGBA_8888;

        mLayoutParams.flags= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

// 悬浮窗的对齐方式

        mLayoutParams.gravity= Gravity.RIGHT| Gravity.TOP;

// 悬浮窗的位置

        mLayoutParams.x=mFloatWinMarginRight;

        mLayoutParams.y=mFloatWinMarginTop;

        mLayoutParams.width=mFloatWinWidth;

        mLayoutParams.height=mFloatWinHeight;

    }

    /**

     * 创建悬浮窗

     */

    private void createFloatView() {

        Log.e(TAG,"createFloatView: ");

        LayoutInflater inflater = LayoutInflater.from(VoipFloatService.this);

        mFloatView= inflater.inflate(R.layout.voipfloat_layout, null);
        layout_showphoto = mFloatView.findViewById(R.id.layout_showphoto);



        mWindowManager.addView(mFloatView,mLayoutParams);

    }


    private void removeFloatView() {

        Log.e(TAG,"removeFloatView: ");

        if(mFloatView!=null&&mWindowManager!=null) {

            mWindowManager.removeView(mFloatView);

        }

    }



}
