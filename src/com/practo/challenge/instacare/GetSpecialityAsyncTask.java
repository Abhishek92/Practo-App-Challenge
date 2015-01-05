package com.practo.challenge.instacare;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class GetSpecialityAsyncTask extends AsyncTask<String,Void,String> {

	private String API_URL = "http://practo.0x10.info/api/speciality?type=json";
	private Callback callback;
	private ProgressDialog mProgressDialog;
	private Context mContext;
	
	public GetSpecialityAsyncTask(Context mContext, Callback callback) {
		this.mContext = mContext;
		this.callback = callback;
	}
	@Override
	protected void onPreExecute() {
		mProgressDialog = new ProgressDialog(mContext);
		mProgressDialog.setMessage(mContext.getString(R.string.loading));
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDialog.setCancelable(false);
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.show();
	}
	@Override
	protected String doInBackground(String... response) {
		String output = null;
		try {
			HttpPost httpPost = new HttpPost(API_URL);
			DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
			HttpResponse httpResponse = defaultHttpClient.execute(httpPost);
			if(httpResponse.getStatusLine().getStatusCode() == 200) {
				ByteArrayOutputStream outstream = new ByteArrayOutputStream();
				httpResponse.getEntity().writeTo(outstream);
				output = outstream.toString();
			}
			return output;
		} catch (IOException e) {
			return null;
		}
	}
	@Override
	protected void onPostExecute(String result) {
		if(mProgressDialog.isShowing())
		{
			mProgressDialog.cancel();
			callback.onComplete(result);
		}
	}

	public interface Callback
	{
		public void onComplete(String response);
	}
}
