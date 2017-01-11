package com.greenland.stepcounter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

public class StartActivity extends FragmentActivity{
    private static int OVERLAY_PERMISSION_REQ_CODE = 1234;
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

        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (!Settings.canDrawOverlays(this)) {
                // SYSTEM_ALERT_WINDOW permission not granted...
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
