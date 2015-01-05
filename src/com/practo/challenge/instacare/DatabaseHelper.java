package com.practo.challenge.instacare;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	// name of the database file for the application
	private static String DATABASE_NAME = "instaCare.sqlite";

	// Version of the databaser. It may you need to change the database tables
	// then you have to increase this version
	private static int DATABASE_VERSION = 1;

	// TAG of the class
	private static String TAG = "DatabaseHelper";

	// DAO Object we use to access the table
	private Dao<PatientAppointmentDetail, Integer> mAppointmentDao = null;
	
	public DatabaseHelper(Context mContext) {
		super(mContext, DATABASE_NAME, null, DATABASE_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
		try {
			TableUtils.createTable(connectionSource, PatientAppointmentDetail.class);
		} catch (SQLException e) {
			Log.e(TAG, "Can't create database", e);
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}

	}
	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub

	}
	
	public Dao<PatientAppointmentDetail, Integer> getAppointmentDetail() {
		if (mAppointmentDao == null) {
			try {
				mAppointmentDao = getDao(PatientAppointmentDetail.class);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return mAppointmentDao;
	}

}
