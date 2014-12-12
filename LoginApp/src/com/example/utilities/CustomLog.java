package com.example.utilities;

import android.util.Log;

public class CustomLog {
	static String tag = "EduTechLabs";
	String msg = "";

	public static void d(String msg) {
		Log.d(tag, msg);
	}
	public static void e(String msg) {
		Log.e(tag, msg);
	}
	public static void w(String msg) {
		Log.w(tag, msg);
	}
	public static void i(String msg) {
		Log.i(tag, msg);
	}


}
