package com.example.loginapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.background.MyTask;
import com.example.background.RequestPackage;
import com.example.background.ReturnFromMyTask;
import com.example.utilities.CheckNetworkConnection;
import com.example.utilities.CustomLog;

public class SignUpActivity extends Activity implements ReturnFromMyTask{

	EditText userName, password, confirmPassword, email;
	Button signUp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		// Show the Up button in the action bar.
		setupActionBar();
		
		signUp = (Button) findViewById(R.id.signup);
		userName = (EditText) findViewById(R.id.user_name);
		password = (EditText) findViewById(R.id.user_password);
		confirmPassword = (EditText) findViewById(R.id.confirm_password);
		email = (EditText) findViewById(R.id.user_email);
		
		
		signUp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(CheckNetworkConnection.isOnline(SignUpActivity.this)){
					CustomLog.d("In LoginActivity about send request on: ");
					sendRegisterRequest("http://192.168.0.71:1992/user/register/");

				}
				else{
					Toast.makeText(SignUpActivity.this, "Not Connected to Network.", Toast.LENGTH_LONG).show();
				} 
			}
		});
		
	}
	
	 public void sendRegisterRequest(String uri) {

			RequestPackage p = new RequestPackage();
			p.setMethod("POST");
			p.setUri(uri);
			p.setBr_request_type("user_register");
			Log.d("send request: ", " " + userName.getText().toString());
			Log.d("send request: ", " " + password.getText().toString());
			Log.d("send request: ", " "+confirmPassword.getText().toString());
			Log.d("send request: ", " "+email.getText().toString());
			p.setParam("username",userName.getText().toString());
			p.setParam("password",password.getText().toString());
			p.setParam("password1",confirmPassword.getText().toString());
			p.setParam("email",email.getText().toString());

			MyTask task = new MyTask();
			task.deligate = SignUpActivity.this;
			task.execute(p);

		}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
//			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void timeToReturn(JSONObject response) {
		// TODO Auto-generated method stub
		Log.d("sign up: ",""+response);
		try {
			JSONObject responseObject = (JSONObject) ((JSONArray)response.get("body")).get(0);
			boolean status =  (Boolean) responseObject.get("status");
			String validationMsg =  responseObject.getString("validation");
			Log.d("status", " "+status);
			
			if(status){
				//welcome screen (activity)
				
				Toast.makeText(SignUpActivity.this, "Register Sucessfully", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
				startActivity(intent);
				
			}else{
				Toast.makeText(SignUpActivity.this,""+validationMsg, Toast.LENGTH_LONG).show();	
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
