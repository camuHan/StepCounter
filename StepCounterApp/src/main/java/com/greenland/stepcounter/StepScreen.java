package com.greenland.stepcounter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StepScreen extends Fragment implements OnClickListener{

	// Step Counter Start/Stop button
	Button mBtnStopStepService;

	Intent mIntentStepService;
	BroadcastReceiver mStepReceiver;

	Toast mToast;
	TextView mTxtCount;
	boolean mStartFlag = true;

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
		mBtnStopStepService = (Button) getActivity().findViewById(R.id.btnStopService);
		mBtnStopStepService.setOnClickListener(this);
	}

	public class StepReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String serviceData;
			serviceData = intent.getStringExtra("serviceData");
			mTxtCount.setText(serviceData);
			Toast.makeText(getActivity(), "Walking!", 1).show();
		}
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btnStopService){
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
}

