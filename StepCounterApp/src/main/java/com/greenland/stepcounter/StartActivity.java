package com.greenland.stepcounter;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class StartActivity extends FragmentActivity implements TabHost.OnTabChangeListener {
    private final String CLIENT_ID = "b40JMlbFg5RsPGEZWjWb";

    public interface PermissionEvent{
        static final int LOCATION_FINE_PERMISSION_REQUEST = 100;
        static final int LOCATION_COARSE_PERMISSION_REQUEST 		= 200;
    }

    public enum PERMISSION_TYPE{
        LOCATION_FINE,
        LOCATION_COARSE,
        ETC
    }

    private static int          OVERLAY_PERMISSION_REQ_CODE = 1234;

    private Runnable            mGrantedRunnable;
    private Runnable            mDeniedRunnable;
    StepLogManager              mStepLogManager;

    View view1, view2;
    ImageView img1, img2;
    LinearLayout layout1, layout2;
    TextView text1, text2;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.cell1, null);
        view2 = inflater.inflate(R.layout.cell2, null);

        FragmentTabHost tabhost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabhost.setup(this, getSupportFragmentManager(), R.id.real_tabcontent);


//        tabhost.addTab(tabhost.newTabSpec("0").setIndicator("만보기 화면"),
//                StepScreen.class, null);
//
//        tabhost.addTab(tabhost.newTabSpec("1").setIndicator("만보기 기록"),
//                StepLog.class, null);

        TabHost.TabSpec fstTab = tabhost.newTabSpec("0")
                .setIndicator(view1);
        tabhost.addTab(fstTab, StepScreen.class, null);
        TabHost.TabSpec SndTab = tabhost.newTabSpec("1")
                .setIndicator(view2);
        tabhost.addTab(SndTab, StepLog.class, null);

        img1 = (ImageView) tabhost.findViewById(R.id.tabimageView1);
        layout1 = (LinearLayout) tabhost.findViewWithTag("tag1");
        text1 = (TextView) tabhost.findViewById(R.id.t1);

        img2 = (ImageView) tabhost.findViewById(R.id.tabimageView2);
        layout2 = (LinearLayout) tabhost.findViewWithTag("tag2");
        text2 = (TextView) tabhost.findViewById(R.id.t2);

        img1.setImageResource(R.drawable.list1_2);
        layout1.setBackgroundColor(0xff8B6914);
        text1.setTextColor(0xff00EE00);
        img2.setImageResource(R.drawable.write1_1);
        layout2.setBackgroundColor(Color.BLACK);
        text2.setTextColor(0xff999789);

        tabhost.setOnTabChangedListener(this);

        // initial load log list.
//        mStepLogManager.getInstance(this);
//        mStepLogManager.loadMsgList();

        if((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
            }
        }
    }

    @Override
    public void onTabChanged(String tabId) {
        img1.setImageResource(R.drawable.list1_1);
        img2.setImageResource(R.drawable.write1_1);
        layout1.setBackgroundColor(Color.BLACK);
        layout2.setBackgroundColor(Color.BLACK);
        text1.setTextColor(0xff999789);
        text2.setTextColor(0xff999789);

        // ���õ� �ǿ� ��Ŀ�� �ο�
        if (tabId.equals("0")) {
            img1.setImageResource(R.drawable.list1_2);
            layout1.setBackgroundColor(0xff8B6914);
            text1.setTextColor(0xff00EE00); // Color.WHITE
        } else if (tabId.equals("1")) {
            img2.setImageResource(R.drawable.write1_2);
            layout2.setBackgroundColor(0xff8B6914);
            text2.setTextColor(0xff00EE00); // Color.WHITE
        }
    }

    public boolean checkPermission(String aPermission) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(aPermission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    public void requestPermission(PERMISSION_TYPE type, String[] aPermission, Runnable aRunnableToExecuteOnPermissionGranted, Runnable aRunnableToExecuteOnPermissionDenied) {
        if((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
            mGrantedRunnable = aRunnableToExecuteOnPermissionGranted;
            mDeniedRunnable = aRunnableToExecuteOnPermissionDenied;
            if (type.equals(PERMISSION_TYPE.LOCATION_FINE)) {
                requestPermissions(aPermission, PermissionEvent.LOCATION_FINE_PERMISSION_REQUEST);
            } else if (type.equals(PERMISSION_TYPE.LOCATION_COARSE)) {
                requestPermissions(aPermission, PermissionEvent.LOCATION_COARSE_PERMISSION_REQUEST);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionEvent.LOCATION_FINE_PERMISSION_REQUEST:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    mGrantedRunnable.run();
                }else{
                    mDeniedRunnable.run();
                }
                break;
            case PermissionEvent.LOCATION_COARSE_PERMISSION_REQUEST:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    mGrantedRunnable.run();
                }else{
                    mDeniedRunnable.run();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)){
            if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
                if (!Settings.canDrawOverlays(this)) {
                    // SYSTEM_ALERT_WINDOW permission not granted...
                }
            }
        }
    }

//    @Override
//    protected void onResume() {
//        // TODO Auto-generated method stub
//        super.onResume();
//        stopService(new Intent(this, MiniTopService.class));
//    }
//
//    @Override
//    protected void onUserLeaveHint() {
//        // TODO Auto-generated method stub
//        super.onUserLeaveHint();
//        startService(new Intent(this, MiniTopService.class));
//    }
}

