package com.greenland.stepcounter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.greenland.stepcounter.data.StepLogMessage;

public class StepLog extends ListFragment implements OnClickListener{
	//HSHTEST
	int num;

	final static String mClsName = "StepLog";
	final static String mTag = "Life";

	StepLogManager mStepLogManager;
	ArrayAdapter<StepLogMessage> mStepLogMsgAdapter;

	Button mBtnNewLog;
	public int clickItemId;

	public static final int CORRECT_REQ = 0;
	public static final int ETC_REQ = 10;

	static class LogListHolder {
		TextView mTxtDate, mTxtCount, mTxtDistance;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mStepLogManager = StepLogManager.getInstance(getActivity());

		mStepLogMsgAdapter = new RsvMsgAdapter(getActivity());
		setListAdapter(mStepLogMsgAdapter);
		mStepLogMsgAdapter.setNotifyOnChange(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.log_list, null);
		mBtnNewLog = (Button)view.findViewById(R.id.btnNewMsg);
		mBtnNewLog.setOnClickListener(this);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		registerForContextMenu(getListView());
		setHasOptionsMenu(true);
	}

	@Override
	public void onResume() {
		super.onResume();
		mStepLogManager.loadMsgList();
		mStepLogMsgAdapter.notifyDataSetChanged();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		clickItemId = position;
		new AlertDialog.Builder(getActivity()).setTitle("삭제").setMessage("삭제할랭?")
				.setPositiveButton("엉", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
//					Bundle data = new Bundle();
//					data.putInt(ShareDataKeyString.FRAG_MSGID, clickItemId);
//					moveTabFocus(FragTagString.FRAG_ROOT_WRITE, data);
					}
				}).setNegativeButton("아닝", null).show();

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		menu.add(0,1,0,"전체 삭제");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Test3
		if(item.getItemId() == 1){
			new AlertDialog.Builder(getActivity()).setTitle("전체 삭제").setMessage("진짜 삭제할랭?")
					.setPositiveButton("엉", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							mStepLogManager.deleteAll();
							mStepLogMsgAdapter.notifyDataSetChanged();
						}
					}).setNegativeButton("아닝", null).show();
		}
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
									ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("삭제");
		menu.add(0, 1, 0, "삭제");
//			menu.add(0, 2, 0, "");

		clickItemId = ((AdapterView.AdapterContextMenuInfo)menuInfo).position;
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
			case 1:
				mStepLogManager.removeMessage((int)clickItemId);
				mStepLogMsgAdapter.notifyDataSetChanged();
				return true;
//			case 2:
//				return true;
		}
		return false;
	}

	class RsvMsgAdapter extends ArrayAdapter<StepLogMessage> {

		Activity mAct;

		public RsvMsgAdapter(Activity act) {
			super(act, R.layout.log_list_item, mStepLogManager.getMsgList());
			this.mAct = act;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {


			LogListHolder holder;
			//mTxtDate, mTxtCount, mTxtDistance
			if(convertView == null){
				LayoutInflater msgInf = mAct.getLayoutInflater();
				convertView = msgInf.inflate(R.layout.log_list_item, null);

				holder = new LogListHolder();
				holder.mTxtDate = (TextView) convertView.findViewById(R.id.txtDate);
				holder.mTxtCount = (TextView) convertView.findViewById(R.id.txtCount);
				holder.mTxtDistance = (TextView) convertView.findViewById(R.id.txtDistance);

				convertView.setTag(holder);
			}else{
//					LogManager.getInstance().log(LogManager.INFO, "HSHTEMP - getView() not null", mClsName);
				holder = (LogListHolder)convertView.getTag();
			}

			StepLogMessage viewMsg = mStepLogManager.getMsgList().get(position);
			// ��¥, �ð� ǥ��
			holder.mTxtDate.setText(
					viewMsg.getCal().getYear()
							+ "/"
							+ (viewMsg.getCal().getMonth() + 1)
							+ "/"
							+ viewMsg.getCal().getDay()
//						+ " "
//						+ viewMsg.getCal().getHour()
//						+ viewMsg.getCal().getMin()
			);
			// ���� ǥ��
			holder.mTxtCount.setText("걸음수: " + viewMsg.getCount());
			// ���� ǥ��
//				LogManager.getInstance().log(LogManager.INFO, "HSHTEMP - getView() not null", mClsName);
			holder.mTxtDistance.setText("거리 : " + viewMsg.getDistance());

			return convertView;
		}
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		setHasOptionsMenu(false);
		System.gc();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		mStepLogManager.setCalendar();
		mStepLogManager.saveMessage("11", "msg", "32.33");
	}
}

