package com.practo.challenge.instacare;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AppointmentDetail extends Activity {

	private PatientAppointmentDetail mAppointmentDetail;
	private TextView mPateintName;
	private TextView mPhoneNumber;
	private TextView mSpeciality;
	private TextView mLocality;
	private TextView mCity;
	private TextView mTime;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apointment_detail);
		int id = getIntent().getIntExtra("id", 0);
		DatabaseManager.init(getApplicationContext());
		mAppointmentDetail = DatabaseManager.getInstance().getAppointmentById(id);
		
		mPateintName = (TextView)findViewById(R.id.name);
		mPhoneNumber = (TextView)findViewById(R.id.phone);
		mSpeciality = (TextView)findViewById(R.id.speciality);
		mCity = (TextView)findViewById(R.id.place);
		mLocality = (TextView)findViewById(R.id.local);
		mTime = (TextView)findViewById(R.id.time);
		
		if(id != 0)
		{
			mPateintName.setText(mAppointmentDetail.getPatientName());
			mPhoneNumber.setText(mAppointmentDetail.getPatientPhone());
			mSpeciality.setText(mAppointmentDetail.getSpeciality());
			mCity.setText(mAppointmentDetail.getCity());
			mLocality.setText(mAppointmentDetail.getLocality());
			mTime.setText(mAppointmentDetail.getTime());
		}
		else
		{
			mPateintName.setText(getIntent().getStringExtra("name"));
			mPhoneNumber.setText(getIntent().getStringExtra("phone"));
			mSpeciality.setText(getIntent().getStringExtra("speciality"));
			mCity.setText(getIntent().getStringExtra("locality"));
			mLocality.setText(getIntent().getStringExtra("city"));
			mTime.setText(getIntent().getStringExtra("time"));
		}
		
	}
}
