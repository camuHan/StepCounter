package com.greenland.stepcounter;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

public class StartActivity extends FragmentActivity {
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

    private Runnable mGrantedRunnable;
    private Runnable mDeniedRunnable;

    private static int OVERLAY_PERMISSION_REQ_CODE = 1234;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        FragmentTabHost tabhost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabhost.setup(this, getSupportFragmentManager(), R.id.real_tabcontent);


        tabhost.addTab(tabhost.newTabSpec("0").setIndicator("만보기 화면"),
                StepScreen.class, null);

        tabhost.addTab(tabhost.newTabSpec("1").setIndicator("만보기 기록"),
                StepLog.class, null);

        if((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
            }
        }
    }

    public boolean checkPermission(String aPermission) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(aPermission) == PackageManager.PERMISSION_GRANTED)
                return true;
        }
        return false;
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

