package com.practo.challenge.instacare;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ApointmentHistory extends ListActivity {

	private List<PatientAppointmentDetail> mAppointmentDetailList;
	private List<AppointmentHistoryRowData> rowDataList;
	AppointmentHistoryRowData rowData;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		DatabaseManager.init(getApplicationContext());
		mAppointmentDetailList = DatabaseManager.getInstance().getAllAppointmentDetail();
		rowDataList = new ArrayList<AppointmentHistoryRowData>(); 
		for(int i=0; i<mAppointmentDetailList.size(); i++)
		{
			int id = mAppointmentDetailList.get(i).getId();
			String city = mAppointmentDetailList.get(i).getCity();
			String locality = mAppointmentDetailList.get(i).getLocality();
			String time = mAppointmentDetailList.get(i).getTime();
			rowData = new AppointmentHistoryRowData(id, locality, city, time);
			rowDataList.add(rowData);
		}
		AppointmentHistoryAdapter adapter = new AppointmentHistoryAdapter(this, R.layout.history_list_item, rowDataList);
		setListAdapter((ListAdapter) adapter);
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		int itemId = rowDataList.get(position).id;
		Intent intent = new Intent(this,AppointmentDetail.class);
		intent.putExtra("id", itemId);
		startActivity(intent);
	}
}
