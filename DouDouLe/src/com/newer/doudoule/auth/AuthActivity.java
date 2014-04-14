package com.newer.doudoule.auth;

import com.newer.doudoule.MainActivity;
import com.newer.doudoule.R;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class AuthActivity extends Activity {

	private static final String TAG = "AuthActivity";

	// 微博授权
	private WeiboAuth weiboAuth;

	/** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能 */
	private Oauth2AccessToken accessToken;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auth);

	}

	@Override
	protected void onStart() {
		super.onStart();
		// 从 SharedPreferences 中读取上次已保存好 AccessToken 等信息，
		// 第一次启动本应用，AccessToken 不可用
		accessToken = AccessTokenKeeper.readAccessToken(this);
		if (!accessToken.getToken().equals("")) {
			Log.d(TAG, "SharePreference" + accessToken.getToken());
			
			Intent intent = new Intent(AuthActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}else{
			Log.d(TAG, "accessToken");
			
			// 创建微博实例
			weiboAuth = new WeiboAuth(this, Constants.APP_KEY,
					Constants.REDIRECT_URL, Constants.SCOPE);
			// web授权
			weiboAuth.anthorize(new AuthListener());
		}
	}

	class AuthListener implements WeiboAuthListener {

		@Override
		public void onCancel() {
			Log.d(TAG, "AuthListener + onCancel");
			
			Toast.makeText(AuthActivity.this,
					R.string.weibosdk_demo_toast_auth_canceled,
					Toast.LENGTH_LONG).show();
		}

		@Override
		public void onComplete(Bundle values) {
			Log.d(TAG, "AuthListener + onComplete");
			
			// 从Bundle中解析token
			accessToken = Oauth2AccessToken.parseAccessToken(values);

			if (accessToken.isSessionValid()) {
				// 保存Token到SharedPreferences
				AccessTokenKeeper.writeAccessToken(AuthActivity.this,
						accessToken);
				Toast.makeText(AuthActivity.this,
						R.string.weibosdk_demo_toast_auth_success,
						Toast.LENGTH_LONG).show();

				Intent intent = new Intent(AuthActivity.this, MainActivity.class);
				startActivity(intent);
				finish();

			} else {
				// 以下几种情况，您会收到 Code：
				// 1. 当您未在平台上注册的应用程序的包名与签名时；
				// 2. 当您注册的应用程序包名与签名不正确时；
				// 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
				String code = values.getString("code");
				String message = getString(R.string.weibosdk_demo_toast_auth_failed);
				if (!TextUtils.isEmpty(code)) {
					message = message + "\nObtained the code:" + code;
				}
				Toast.makeText(AuthActivity.this, message, Toast.LENGTH_LONG)
						.show();

			}

		}

		@Override
		public void onWeiboException(WeiboException e) {
			Log.d(TAG, "AuthListener + onWeiboException");
			
			Toast.makeText(AuthActivity.this,
					"Auth excption:" + e.getMessage(), Toast.LENGTH_LONG)
					.show();
		}

	}

}
