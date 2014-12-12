package com.example.loginapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.background.MyTask;
import com.example.background.RequestPackage;
import com.example.background.ReturnFromMyTask;
import com.example.utilities.CheckNetworkConnection;
import com.example.utilities.CustomLog;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements ReturnFromMyTask{

	Button login;
	EditText user_name,user_password;
	TextView signUp;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        login = (Button) findViewById(R.id.login);
        user_name = (EditText) findViewById(R.id.username);
        user_password = (EditText) findViewById(R.id.password);
        signUp = (TextView) findViewById(R.id.signup);

        signUp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				signUpActivity();
			}
		});
        
        login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(CheckNetworkConnection.isOnline(LoginActivity.this)){
					CustomLog.d("In LoginActivity about send request on: ");
					sendRequest("http://192.168.0.71:1992/auth/");

				}
				else{
					Toast.makeText(LoginActivity.this, "Not Connected to Network.", Toast.LENGTH_LONG).show();
				} 
			}
		});
        
    }

    public void signUpActivity(){
    	Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
    	startActivity(intent);
    }	

    private void sendRequest(String uri) {

		RequestPackage p = new RequestPackage();
		p.setMethod("POST");
		p.setUri(uri);
		p.setBr_request_type("user_login");
		p.setParam("username",user_name.getText().toString());
		p.setParam("password",user_password.getText().toString());

		CustomLog.d("USER NAME : "+p.getParamValue("userName"));
		CustomLog.d("PASSWORD  : "+p.getParamValue("password"));

		MyTask task = new MyTask();
		task.deligate = LoginActivity.this;
		task.execute(p);

	}
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

	@Override
	public void timeToReturn(JSONObject response) {
		CustomLog.d(""+response);
		try {
			JSONObject responseObject = (JSONObject) ((JSONArray)response.get("body")).get(0);
			boolean status =  (Boolean) responseObject.get("status");
			String validationMsg =  responseObject.getString("validation");
			Log.d("status", " "+status);
			
			if(status){
				// open welcome activity
				Toast.makeText(LoginActivity.this,""+validationMsg, Toast.LENGTH_LONG).show();
				Intent intent = new Intent(LoginActivity.this,WelcomeActivity.class);
				startActivity(intent);
				
			}else{
					
				Toast.makeText(LoginActivity.this,""+validationMsg, Toast.LENGTH_LONG).show();	
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
    
}
