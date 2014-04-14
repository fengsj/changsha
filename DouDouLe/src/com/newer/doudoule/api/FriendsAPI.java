package com.newer.doudoule.api;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.newer.doudoule.auth.AccessTokenKeeper;
import com.newer.doudoule.dao.PutJson;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class FriendsAPI {
	private static final String TAG = "FriendsAPI";
	private Context context;
	private String witchOne;

	public FriendsAPI(String url, Context context) {
		this.context = context;

		Oauth2AccessToken oauth2AccessToken = AccessTokenKeeper
				.readAccessToken(context);
		long uid = Long.parseLong(oauth2AccessToken.getUid());
		String accessToken = oauth2AccessToken.getToken();

		String doGet = url + "?access_token=" + accessToken + "&uid=" + uid;
		Log.d(TAG, "accessToken:" + accessToken + "\nuid:" + uid + "\nurl:"
				+ url);

		if (url == "https://api.weibo.com/2/friendships/friends.json") {
			witchOne = "attention";
		} else if (url == "https://api.weibo.com/2/friendships/followers.json") {
			witchOne = "follow";
			Log.d(TAG, "witchOne : follow");
		}

		new LoadDataTask().execute(doGet);
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

			PutJson json = new PutJson(context);
			if (result != null) {

				if (witchOne.equals("attention")) {
					json.updataFriends(result);

				} else if (witchOne.equals("follow")) {
					json.updataFollowers(result);
					Log.d(TAG, "updatafollowers");
				}

			}
		}

	}
}
