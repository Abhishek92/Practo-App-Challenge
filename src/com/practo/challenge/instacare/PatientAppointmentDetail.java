package com.practo.challenge.instacare;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class PatientAppointmentDetail {

	@DatabaseField(generatedId = true, columnName = "id")
	public int id;
	@DatabaseField(columnName = "name", dataType = DataType.STRING)
	public String patientName;
	@DatabaseField(columnName = "phone", dataType = DataType.STRING)
	public String patientPhone;
	@DatabaseField(columnName = "speciality", dataType = DataType.STRING)
	public String speciality;
	@DatabaseField(columnName = "locality", dataType = DataType.STRING)
	public String locality;
	@DatabaseField(columnName = "city", dataType = DataType.STRING)
	public String city;
	@DatabaseField(columnName = "time", dataType = DataType.STRING)
	public String time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getPatientPhone() {
		return patientPhone;
	}
	public void setPatientPhone(String patientPhone) {
		this.patientPhone = patientPhone;
	}
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
