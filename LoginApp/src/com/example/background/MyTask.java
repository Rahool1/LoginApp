package com.example.background;

import org.json.JSONObject;

import android.os.AsyncTask;

import com.example.utilities.CustomLog;

public class MyTask extends AsyncTask<RequestPackage,String,JSONObject>{
	public ReturnFromMyTask deligate = null;

	@Override
	protected JSONObject doInBackground(RequestPackage... params) {
		//String content = HttpManager.getData(params[0]);

		JSONObject response = HttpManager.getData(params[0]);

		CustomLog.d( "IN MyTask.doInBackGround() content: "+response);
		return response;
	}
	@Override
	protected void onPostExecute(JSONObject result) {

		deligate.timeToReturn(result);
		
		
	}
}
