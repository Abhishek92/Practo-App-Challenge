package com.practo.challenge.instacare;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AppointmentHistoryAdapter extends ArrayAdapter<AppointmentHistoryRowData> {
	private List<AppointmentHistoryRowData> dataList;
	private LayoutInflater mInflater;
	private Context context;
	
	public AppointmentHistoryAdapter(Context context, int resource, List<AppointmentHistoryRowData> objects) {
		super(context, resource, objects);
		this.context = context;
		dataList = objects;
		mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final AppointmentHistoryRowData rowData = getItem(position);
		if (convertView == null)
			convertView = mInflater.inflate(R.layout.history_list_item, null);
		
		TextView locality = (TextView) convertView.findViewById(R.id.locality);
		TextView city = (TextView) convertView.findViewById(R.id.city);
		TextView time = (TextView) convertView.findViewById(R.id.time);
		
		locality.setText(rowData.locality);
		city.setText(rowData.city);
		time.setText(rowData.time);
		return convertView;
	}
	
	
}
