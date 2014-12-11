package com.example.utilities;

public class CustomURL {
	
	public static final String PROTOCOL = "http://";
	public static final String DOMAIN = "192.168.0.69:";
	public static final String PORT = "1992";

	// Valid Paths are Register here
	public static final String LOGIN_URL_PATH = "/payacc/auth/";
	public static final String REGISTER_URL_PATH = "/payacc/register_user/";
	public static final String COUPON_URL_PATH = "/ucoupons/get/";
	public static final String MERCHANT_URL_PATH = "/merchants/search/";
	public static final String SEARCH_MERCHANT_BY_ID = "/merchants/merchant_custom_id/";
	public static final String GET_BALANCE = "/ucoupons/user_balance/"; 
	public static final String SEND_COUPON = "/coupons/used_coupons/"; 
	
	
	
	public static String getLoginLocalCustomURL()
	{
		String url = PROTOCOL+DOMAIN+PORT+LOGIN_URL_PATH;
		return url;
	}
	
	public static String getRegisterLocalCustomURL()
	{
		String url = PROTOCOL+DOMAIN+PORT+REGISTER_URL_PATH;
		return url;
	}
	
	public static String getCouponsLocalCustomURL()
	{
		String url = PROTOCOL+DOMAIN+PORT+COUPON_URL_PATH;
		return url;
	}
	public static String getMerchantLocalCustomURL()
	{
		String url = PROTOCOL+DOMAIN+PORT+MERCHANT_URL_PATH;
		return url;
	}
	public static String getMerchantByidCustomURL()
	{
		String url = PROTOCOL+DOMAIN+PORT+SEARCH_MERCHANT_BY_ID;
		return url;
	}
	
	public static String getBalanceLocalCustomURL()
	{
		String url = PROTOCOL+DOMAIN+PORT+GET_BALANCE;
		return url;	
	}
	public static String sendCouponLocalCustomURL()
	{
		String url = PROTOCOL+DOMAIN+PORT+SEND_COUPON;
		return url;	
	}
}
