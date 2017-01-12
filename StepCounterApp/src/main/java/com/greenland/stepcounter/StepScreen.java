package com.greenland.stepcounter;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.greenland.stepcounter.stepvalue.Values;

import static android.content.Context.LOCATION_SERVICE;

public class StepScreen extends Fragment implements OnClickListener {

    // Step Counter Start/Stop button
    Button mBtnStopStepService;

    Intent mIntentStepService;
    BroadcastReceiver mStepReceiver;

    StepLocationManager mMbLocMgr;

    Toast mToast;
    TextView mTxtCount;
    TextView mTxtAddress;
    boolean mStartFlag = true;

    public class UIHANDLER_RESULT
    {
        static public final int UPDATEADREES		= 1;
    }

    Handler mUIHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UIHANDLER_RESULT.UPDATEADREES:
                    mTxtAddress.setText(Values.address);
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.main_stepcounter, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        // Step Service Intent create
        mIntentStepService = new Intent(getActivity(),
                com.greenland.stepcounter.service.StepCounterService.class);
        // Step Receiver create
        mStepReceiver = new StepReceiver();

        mTxtCount = (TextView) getActivity().findViewById(R.id.walk_count);
        mTxtAddress = (TextView) getActivity().findViewById(R.id.walk_address);
        mBtnStopStepService = (Button) getActivity().findViewById(R.id.btnStopService);
        mBtnStopStepService.setOnClickListener(this);

        mMbLocMgr = StepLocationManager.getInstance(getActivity());
        mMbLocMgr.setHandler(mUIHandler);
        mMbLocMgr.getMyLocation();
    }

    public class StepReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String serviceData;
            serviceData = intent.getStringExtra("serviceData");
            mTxtCount.setText(serviceData);
            Toast.makeText(getActivity(), "Walking!", 1).show();
            TextView test = (TextView) getActivity().findViewById(R.id.walk_address);
        }
    }

    @Override
    public void onClick(View v) {
        if (!checkGPS())
            return;

        if (v.getId() == R.id.btnStopService) {
            if (mStartFlag) {
                mBtnStopStepService.setText("STOP");
                try {
                    IntentFilter mainFilter = new IntentFilter(
                            "com.greenland.stepcount.serv");
                    // start Step service
                    getActivity().registerReceiver(mStepReceiver, mainFilter);
                    getActivity().startService(mIntentStepService);
                    Toast.makeText(getActivity(), "서비스 시작", 1).show();
                } catch (Exception e) {
                    // TODO: handle exception
                    Toast.makeText(getActivity(), e.getMessage(), 1).show();
                }
            } else {

                mBtnStopStepService.setText("START");
                try {
                    // stop Step service
                    getActivity().unregisterReceiver(mStepReceiver);
                    getActivity().stopService(mIntentStepService);

                    Toast.makeText(getActivity(), "서비스 중지", 1).show();
                } catch (Exception e) {
                    // TODO: handle exception
                    Toast.makeText(getActivity(), e.getMessage(), 1).show();
                }
            }
            mStartFlag = !mStartFlag;
        }
    }

//    // 최소 GPS 정보 업데이트 거리 10미터
//    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
//
//    // 최소 GPS 정보 업데이트 시간 밀리세컨이므로 1분
//    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

    private boolean checkGPS() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
//		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,10,this);
        boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return false;
//        }
//
//        locationManager.requestLocationUpdates(
//                LocationManager.GPS_PROVIDER,
//                MIN_DISTANCE_CHANGE_FOR_UPDATES,
//                MIN_TIME_BW_UPDATES, (LocationListener) getActivity());

		if(!gpsEnabled){
            AlertDialog.Builder gpsDialog = new AlertDialog.Builder(getActivity());
			gpsDialog.setTitle("위치 서비스 설정");
			gpsDialog.setMessage("무선 네트워크 사용, GPS 위성 사용을 모두 체크하셔야 정확한 위치 서비스가 가능합니다.\n위치 서비스 기능을 설정하시겠습니까?");
			gpsDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					intent.addCategory(Intent.CATEGORY_DEFAULT);
					startActivity(intent);
				}
			})
					.setNegativeButton("NO", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							AlertDialog.Builder notUseMsg = new AlertDialog.Builder(getActivity());
							notUseMsg.setMessage("만보기를 사용 하실 수 없습니다.");
							notUseMsg.setPositiveButton("확인", null);
							notUseMsg.setTitle("사용 불가").create().show();
						}
					}).create().show();
			return false;
		}
		return true;
	}
}
