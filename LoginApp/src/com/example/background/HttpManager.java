package com.example.background;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import com.example.utilities.CustomLog;

@SuppressLint("NewApi")
public class HttpManager {
	public static JSONObject getData(RequestPackage p) {
		BufferedReader reader = null;
		String uri = p.getUri();
		if (p.getMethod().equals("GET")) {
			uri += "?" + p.getEncodedParams();
		}

		try {
			
			JSONObject response = new JSONObject();
			JSONArray header_array = new JSONArray();
			JSONObject header_object = new JSONObject();
			
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod(p.getMethod());
			//started cookie code

			CookieManager cookieManager = new CookieManager();
			CookieHandler.setDefault(cookieManager);

//			p.getParamValue("username");


			CustomLog.d("Before setting session ID in httpMahager "+ p.getSessionid());

			
			
			//			HttpCookie cookie = new HttpCookie("sessionid", p.getSessionid());
			//			cookie.setDomain("http://192.168.0.72/");
			//			cookie.setPath("/payacc/auth/");
			//			cookie.setVersion(0);
			//			cookieManager.getCookieStore().add(new URI("http://192.168.0.72/"), cookie);

			//			"sessionid="+p.getSessionid();

			//			con.setRequestProperty("Cookie",cookieManager.getCookieStore().get(new URI("http://192.168.0.72/")).toString());

			
			//setting request type in header  
			con.setRequestProperty("br_request_type", p.getBr_request_type());
			CustomLog.d("sent Request type in header inside cookie of http request "+ p.getBr_request_type());


			//check here the condition in if statement when you have already logged in  still remaining to be implemented correctly 
			if(!((p.getSessionid().equals("")) || p.getSessionid().equals("authentication failure"))){
				con.setRequestProperty("Cookie","sessionid="+p.getSessionid());							
				
				//this session id is set in header so that session id can be forwarded to each activiry making request via custom response object
				con.setRequestProperty("br_session_id", p.getSessionid());
				
				
				CustomLog.d("sent session ID in header inside cookie of http request "+ p.getSessionid());
				
			}else{
				CustomLog.d("in HttpManager Not Sending Session ID");
			}

			//cookie code ends


			if (p.getMethod().equals("POST")) {
				con.setDoOutput(true);
				CustomLog.d("Checking connection Exception");

				

				try{
					OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
					CustomLog.d("Checking connection Exception1");
					writer.write(p.getEncodedParams());
					writer.flush();
					CustomLog.d("+++++++++++login request"+writer.toString());
				}catch(ConnectException e){
					CustomLog.d("Server is down contact to Nikhil 9404505206");
					
					header_object.put("status", new Integer(1992));
					header_object.put("br_request_type", con.getRequestProperty("br_request_type"));
					header_array.put(header_object);
					
					response.put("header", header_array);
					response.put("body", null);
					return response;
					
				}





			}

			CustomLog.d("In HTTP Manager CLass  Response Code "+con.getResponseCode());
			
			int status = con.getResponseCode();
			String request_type = con.getRequestProperty("br_request_type");
			String session_id =  con.getRequestProperty("br_session_id");
			CustomLog.d("In httpManager Cookie session Id : "+session_id );
			
			
			CustomLog.d("In HTTP Manager CLass  Request type :  "+request_type);
			
			//if(status == 200){	
				
				StringBuilder sb = new StringBuilder();
				reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

				String line;
				while ((line = reader.readLine()) != null) {
					//sb.append(line);
					sb.append(line + "\n");
				}
				
				
				/**his block will generate a response object that contains header and body
				 * response object:
				 * {
				 *     header:[{
				 *                status: -----,
				 *                request_type: -----,
				 *                session_id :-----
				 *            }]
				 * 		body:[{
				 *           	response_From_Server
				 *           }]
				 * }
				 * */
				 
				
				
				String response_body = sb.toString();
				
				
				header_object.put("status", status);
				header_object.put("br_request_type", request_type);
				header_object.put("br_session_id", session_id);
				header_array.put(header_object);
				CustomLog.d("In HttpManager Response Object : " +response.toString());
				response.put("header", header_array);
				JSONArray body_array = new JSONArray(response_body);
				response.put("body", body_array);
				
				CustomLog.d("In HttpManager Response Object : " +response.toString());
				
				return response;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}

	}
}
