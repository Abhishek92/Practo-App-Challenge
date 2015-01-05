package com.practo.challenge.instacare;

public class AppointmentHistoryRowData {

	public int id;
	public String locality;
	public String city;
	public String time;
	
	public AppointmentHistoryRowData(int id, String locality, String city, String time){
		this.id = id;
		this.locality = locality;
		this.city = city;
		this.time = time;
		
	}
}
