package com.newer.doudoule.api;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.newer.doudoule.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class StatusAPI {

	public static final String TAG = "StatusAPI";
	private Context context;

	public StatusAPI(String url, Context context) {
		this.context = context;

		Oauth2AccessToken oauth2AccessToken = AccessTokenKeeper
				.readAccessToken(context);
		long uid = Long.parseLong(oauth2AccessToken.getUid());
		String accessToken = oauth2AccessToken.getToken();

		String doGet = url + "?access_token=" + accessToken + "&uid=" + uid;

	}

	class LoadDataTask extends AsyncTask<String, Void, String> {

		private String json;
		
		@Override
		protected String doInBackground(String... params) {
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(params[0]);
			
			try {
				HttpResponse response = client.execute(get);
				if (response.getStatusLine().getStatusCode() == 200) {
					json = EntityUtils.toString(response.getEntity());
					return json;
				}
				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Log.d(TAG, "JSON:" + json);
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Log.d(TAG, "result:" + result);
		}

	}
}
