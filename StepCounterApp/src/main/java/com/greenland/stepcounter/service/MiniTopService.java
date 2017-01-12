package com.greenland.stepcounter.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.TextView;

import com.greenland.stepcounter.R;
import com.greenland.stepcounter.StartActivity;

public class MiniTopService extends Service {

//	private TextView mPopupView; // 항상 보이게 할 뷰
	private WindowManager.LayoutParams mParams; // layout params 객체. 뷰의 위치 및 크기
	private WindowManager mWindowManager; // 윈도우 매니저
	private View m_View;

	TextView mTxtCount;
	TextView mTxtDistance;
	TextView mTxtDate;

	public MiniTopService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		// top_view 레이아웃을 생성하여 뷰 출력.
		 LayoutInflater mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 m_View = mInflater.inflate(R.layout.mini_mode, null);
		 m_View.setOnTouchListener(onTouchListener);

//		mTxtCount = (TextView) m_View.findViewById(R.id.txtCount);
//		mTxtDistance = (TextView) m_View.findViewById(R.id.txtDistance);
//		mTxtDate = (TextView) m_View.findViewById(R.id.txtDate);
//
//		mTxtCount.setText(Values.Step);
//		mTxtDistance.setText(Values.Distance);


		m_View.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("HSH", "HSH onClick");
				Intent intent = new Intent(getApplicationContext(),
						StartActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});
		m_View.setOnTouchListener(onTouchListener);

		mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE); // 윈도우
		// 매니

		DisplayMetrics displayMetrics = new DisplayMetrics();

		mWindowManager.getDefaultDisplay().getMetrics(displayMetrics);

//--- displayMetrics.density : density / 160, 0.75 (ldpi), 1.0 (mdpi), 1.5 (hdpi)
		int dipWidth  = (int) (displayMetrics.widthPixels  / 2);
		int dipHeight = (int) (displayMetrics.heightPixels / 4);



		출처: http://duzi077.tistory.com/4 [개발하는 두더지]

		// 최상위 윈도우에 넣기 위한 설정
		mParams = new WindowManager.LayoutParams(
				dipWidth,
				dipHeight,
				WindowManager.LayoutParams.TYPE_PHONE,// 항상 최 상위. 터치 이벤트 받을 수
														// 있음.
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, // 포커스를 가지지 않음
				PixelFormat.TRANSLUCENT); // 투명
		// mParams.gravity = Gravity.LEFT | Gravity.TOP; // 왼쪽 상단에 위치하게 함.


		mWindowManager.addView(m_View, mParams); // 윈도우에 뷰 넣기. permission
														// 필요.

	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		// 생성 된 레이아웃 제거.
		mWindowManager.removeView(m_View);
		mWindowManager = null;
	}

	private float mTouchX, mTouchY;
	private int mViewX, mViewY;
	final static int MAX_TOUCH_AREA_VALUE = 10;

	// 터치 이벤트.
	private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mTouchX = event.getRawX();
				mTouchY = event.getRawY();
				mViewX = mParams.x;
				mViewY = mParams.y;
				Log.e("HSH", "HSH down");
				break;

			case MotionEvent.ACTION_MOVE:
				int x = (int) (event.getRawX() - mTouchX);
				int y = (int) (event.getRawY() - mTouchY);

				mParams.x = mViewX + x;
				mParams.y = mViewY + y;
				Log.e("HSH", "HSH touch move x = " + mParams.x + " y "
						+ mParams.y);
				mWindowManager.updateViewLayout(m_View, mParams);
				Log.e("HSH", "HSH move");
				break;

			case MotionEvent.ACTION_UP:
				int moveX = (int) (event.getRawX() - mTouchX);
				int moveY = (int) (event.getRawY() - mTouchY);
				if(Math.abs(moveX) < MAX_TOUCH_AREA_VALUE && Math.abs(moveY) < MAX_TOUCH_AREA_VALUE ){
					Intent intent = new Intent(getApplicationContext(), StartActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
				}
					
				Log.e("HSH", "HSH up");
				break;
			}

			return true;
		}
	};

	// 터치 동작 확인을 위해 버튼 터치 시 메인 액티비티 호출.
	public void onBtnTest(View v) {
		Intent intent = new Intent(getApplicationContext(), StartActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

}
