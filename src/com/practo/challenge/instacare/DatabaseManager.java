package com.practo.challenge.instacare;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

public class DatabaseManager {
	private static DatabaseManager mInstance;
	private DatabaseHelper mDatabaseHelper;

	public DatabaseManager(Context mContext) {
		mDatabaseHelper = new DatabaseHelper(mContext);
	}

	public static DatabaseManager getInstance() {
		return mInstance;
	}

	public static void init(Context mContext) {
		if (mInstance == null)
			mInstance = new DatabaseManager(mContext);
	}

	public DatabaseHelper getHelper() {
		return mDatabaseHelper;
	}
	
	public List<PatientAppointmentDetail> getAllAppointmentDetail() {
		List<PatientAppointmentDetail> mAppointmentList = null;
		try {
			mAppointmentList = getHelper().getAppointmentDetail().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mAppointmentList;
	}
	
	
	public PatientAppointmentDetail getAppointmentById(int id) {
		PatientAppointmentDetail mAppointment = null;
		try {
			mAppointment = getHelper().getAppointmentDetail().queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mAppointment;
	}
	
	
	public void addAppointmentDetail(PatientAppointmentDetail mAppointment) {
		try {
			getHelper().getAppointmentDetail().create(mAppointment);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
