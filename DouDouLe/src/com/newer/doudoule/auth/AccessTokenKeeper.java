package com.newer.doudoule.auth;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AccessTokenKeeper {

	private static final String PREFERENCES_NAME = "com_weibo_sdk_android";
	public static final String KEY_UID = "uid";
	public static final String KEY_ACCESS_TOKEN = "access_token";
	private static final String KEY_EXPIRES_IN = "expires_in";

	/**
	 * 写入首选项
	 * @param context
	 * @param token
	 */
	public static void writeAccessToken(Context context, Oauth2AccessToken token){
		if (context == null || token == null) {
			return;
		}
		
		SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
		Editor editor = preferences.edit();
		editor.putString(KEY_UID, token.getUid());
		editor.putString(KEY_ACCESS_TOKEN, token.getToken());
		editor.putLong(KEY_EXPIRES_IN, token.getExpiresTime());
		editor.commit();
	}
	
	/**
	 * 读取首选项
	 * @param context
	 * @return
	 */
	public static Oauth2AccessToken readAccessToken(Context context){
		if (null == context) {
			return null;
		}
		
		Oauth2AccessToken token = new Oauth2AccessToken();
		SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
		token.setUid(preferences.getString(KEY_UID, ""));
		token.setToken(preferences.getString(KEY_ACCESS_TOKEN, ""));
		token.setExpiresTime(preferences.getLong(KEY_EXPIRES_IN, 0));
		
		return token;
	}
	
	public static void clear(Context context){
		if (context == null) {
			return;
		}
		
		SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
		Editor editor = preferences.edit();
		editor.clear();
		editor.commit();
	}
}
