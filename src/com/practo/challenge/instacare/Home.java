package com.practo.challenge.instacare;

import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class Home extends Activity implements OnClickListener,OnItemSelectedListener {

	private EditText mPateintName;
	private EditText mPhoneNumber;
	private AutoCompleteTextView mSpeciality;
	private AutoCompleteTextView mLocality;
	private Spinner mCity;
	private EditText mTime;
	private Button mInstantCare;
	private Button mScheduleAppointment;
	private Button mViewHistory;
	private ArrayList<String> specialityArray;
	private ArrayList<String> localityArray;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		//Initialize all views
		mPateintName = (EditText)findViewById(R.id.pateintname);
		mPhoneNumber = (EditText)findViewById(R.id.pateintmobile);
		mSpeciality = (AutoCompleteTextView)findViewById(R.id.specilityTextView);
		mCity = (Spinner)findViewById(R.id.selectCity);
		mLocality = (AutoCompleteTextView)findViewById(R.id.localityTextView);
		mTime = (EditText)findViewById(R.id.time);
		mInstantCare = (Button)findViewById(R.id.instnatCare);
		mScheduleAppointment = (Button)findViewById(R.id.scheduleAppontment);
		mViewHistory = (Button)findViewById(R.id.viewHistory);
		
		specialityArray = new ArrayList<String>();
		localityArray = new ArrayList<String>();
		
		mTime.setFocusable(false);
		mTime.setClickable(true);
		mTime.setOnClickListener(this);
		
		mCity.setOnItemSelectedListener(this);
		mInstantCare.setOnClickListener(this);
		mScheduleAppointment.setOnClickListener(this);
		mViewHistory.setOnClickListener(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(checkInternetConnection(getApplicationContext())){
			new GetSpecialityAsyncTask(this, new GetSpecialityAsyncTask.Callback() {
				@Override
				public void onComplete(String response) {
					if(response == null)
						 Crouton.makeText(Home.this, R.string.server_error, Style.INFO).show();
						else{
						try {
							JSONArray arr = new JSONArray(response);
							for(int i=0; i<arr.length(); i++)
								specialityArray.add(arr.getString(i));
							ArrayAdapter<String> adapter = new ArrayAdapter<String>(Home.this, android.R.layout.simple_list_item_1, specialityArray);
							mSpeciality.setAdapter(adapter);
						} catch (JSONException e) {
							e.printStackTrace();
						}
				}
				}	
			}).execute();
		}
		else
			Crouton.makeText(Home.this, R.string.check_internet, Style.INFO).show();
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.instnatCare)
		{
			if(validate()){
				viewAppointmentDetail();
				String number = "+91"+mPhoneNumber.getText().toString().trim();
				sendSms(number, getAppointmentSmsDetailBody());
			}
		}
		else if(v.getId() == R.id.scheduleAppontment)
		{
			if(validate()){
			 viewAppointmentDetail();
			 String number = "+91"+mPhoneNumber.getText().toString().trim();
			 sendSms(number, getAppointmentSmsDetailBody());
			}
		}
		else if(v.getId() == R.id.viewHistory)
			startActivity(new Intent(this, ApointmentHistory.class));
		else if(v.getId() == R.id.time)
			timeDialog();
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View view, int arg2,
			long arg3) {
		if(mCity.getSelectedItemPosition() != 0){
			String city = mCity.getSelectedItem().toString();
			if(checkInternetConnection(getApplicationContext()))
			{
				new GetLocalityAsyncTask(this, city, new GetLocalityAsyncTask.Callback() {
					@Override
					public void onComplete(String response) {
						if(response == null)
						 Crouton.makeText(Home.this, R.string.server_error, Style.INFO).show();
						else{
							try {
								JSONArray arr = new JSONArray(response);
								for(int i=0; i<arr.length(); i++)
									localityArray.add(arr.getString(i));
								ArrayAdapter<String> adapter = new ArrayAdapter<String>(Home.this, android.R.layout.simple_list_item_1, localityArray);
								mLocality.setAdapter(adapter);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}
				}).execute();
			}
			else
				Crouton.makeText(Home.this, R.string.check_internet, Style.INFO).show();
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}
	private void viewAppointmentDetail()
	{
		DatabaseManager.init(getApplicationContext());
		DatabaseManager.getInstance().addAppointmentDetail(setPatientAppointmentDetail());
		Intent intent = new Intent(this,AppointmentDetail.class);
		intent.putExtra("name", mPateintName.getText().toString().trim());
		intent.putExtra("phone",mPhoneNumber.getText().toString().trim());
		intent.putExtra("speciality",mSpeciality.getText().toString().trim());
		intent.putExtra("locality",mLocality.getText().toString().trim());
		intent.putExtra("city",mCity.getSelectedItem().toString());
		intent.putExtra("time",mTime.getText().toString().trim());
		startActivity(intent);
		
	}
	private PatientAppointmentDetail setPatientAppointmentDetail()
	{
		PatientAppointmentDetail patientDetail = new PatientAppointmentDetail();
		patientDetail.setPatientName(mPateintName.getText().toString().trim());
		patientDetail.setPatientPhone(mPhoneNumber.getText().toString().trim());
		patientDetail.setSpeciality(mSpeciality.getText().toString().trim());
		patientDetail.setLocality(mLocality.getText().toString().trim());
		patientDetail.setCity(mCity.getSelectedItem().toString());
		patientDetail.setTime(mTime.getText().toString().trim());
		
		return patientDetail;
	}
	
	private String getAppointmentSmsDetailBody()
	{
		String body = "Your Appointment details are below:\n"
				+"Patient Name: "+mPateintName.getText().toString().trim()+"\n"
				+"Phone Number: "+mPhoneNumber.getText().toString().trim()+"\n"
				+"Speciality: "+mSpeciality.getText().toString().trim()+"\n"
				+"Locality: "+mLocality.getText().toString().trim()+"\n"
				+"City: "+mCity.getSelectedItem().toString()+"\n"
				+"Time: "+mTime.getText().toString().trim();
		
		return body;
	}
	
	private boolean validate()
	{
		if(mPateintName.getText().toString().trim().length() == 0)
		{
			Crouton.makeText(Home.this, R.string.error_name, Style.ALERT).show();
			return false;
		}
		else if(mPhoneNumber.getText().toString().trim().length() < 10)
		{
			Crouton.makeText(Home.this, R.string.error_phone, Style.ALERT).show();
			return false;
		}
		else if(mSpeciality.getText().toString().trim().length() == 0)
		{
			Crouton.makeText(Home.this, R.string.error_speciality, Style.ALERT).show();
			return false;
		}
		else if(mCity.getSelectedItemPosition() == 0)
		{
			Crouton.makeText(Home.this, R.string.error_city, Style.ALERT).show();
			return false;
		}
		else if(mLocality.getText().toString().trim().length() == 0)
		{
			Crouton.makeText(Home.this, R.string.error_locality, Style.ALERT).show();
			return false;
		}
		else if(mTime.getText().toString().trim().length() == 0)
		{
			Crouton.makeText(Home.this, R.string.error_time, Style.ALERT).show();
			return false;
		}
		else
			return true;
	}
	
	/**
	 * Display a time dialog
	 */
	private void timeDialog()
	{
		final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        
		TimePickerDialog tpd = new TimePickerDialog(this,
	        new TimePickerDialog.OnTimeSetListener() {
				@Override
	            public void onTimeSet(TimePicker view, int hourOfDay,
	                    int minute) {
					String min = minute < 10 ? "0"+minute : minute+"";
					mTime.setText(hourOfDay + ":" + min);
	            }
	        }, hour, minute, false);
		tpd.show();
	}
	
	
	private boolean checkInternetConnection(Context context) {

		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm != null) {
			NetworkInfo[] networkInfo = cm.getAllNetworkInfo();
			if (networkInfo != null)
				for (int i = 0; i < networkInfo.length; i++) {
					if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
						return true;
				}
		}
		return false;
	}
	
	private void sendSms(String number, String body)
	{
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(number,null,body,null,null);
	}

}
