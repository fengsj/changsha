package com.newer.doudoule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newer.doudoule.auth.AccessTokenKeeper;
import com.newer.doudoule.dao.PutJson;
import com.newer.doudoule.dao.UserInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.models.User;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment implements OnItemClickListener {

	protected static final String TAG = "HomeFragment";

	protected static final String KEY_CATEGORY = "category";
	protected static final String KEY_IMAGE = "image";
	protected static final String KEY_NUMBER = "number";

	// 头像
	private ImageView imageViewUser;

	private TextView textViewName;
	private ListView listViewFuction;

	private SimpleAdapter adapter;
	private List<Map<String, Object>> dataSet;

	private UsersAPI usersAPI;

	private String[] from = { KEY_IMAGE, KEY_CATEGORY, KEY_NUMBER };
	private int[] to = { R.id.imageView_list_homeFragment,
			R.id.textView_category_homeFragment,
			R.id.textView_number_homeFragment };
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.activity_user_center, container,
				false);

		imageViewUser = (ImageView) view.findViewById(R.id.imageView_user);
		textViewName = (TextView) view.findViewById(R.id.textView_user_name);
		listViewFuction = (ListView) view
				.findViewById(R.id.listView_user_fuction);

		Oauth2AccessToken accessToken = AccessTokenKeeper
				.readAccessToken(getActivity());
		usersAPI = new UsersAPI(accessToken);
		long uid = Long.parseLong(AccessTokenKeeper.readAccessToken(
				getActivity()).getUid());
		usersAPI.show(uid, requestListener);

		// listView中放入数据
		dataSet = new ArrayList<Map<String, Object>>();
		adapter = new SimpleAdapter(getActivity(), dataSet,
				R.layout.list_home_fragment, from, to);
		listViewFuction.setAdapter(adapter);

		listViewFuction.setOnItemClickListener(this);

		return view;
	}

	private RequestListener requestListener = new RequestListener() {

		@Override
		public void onWeiboException(WeiboException e) {
			// ErrorInfo errorInfo = ErrorInfo.parse(e.getMessage());
			// showToast(errorInfo.toString() + "没有网络");

			// 直接从本地数据库中获得
			PutJson json = new PutJson(getActivity());
			Cursor cursor = json.getUserJson();
			loadData(cursor);
		}

		@Override
		public void onComplete(String response) {

			if (!TextUtils.isEmpty(response)) {

				// 表中取数据
				PutJson json = new PutJson(getActivity());
				Cursor cursor = json.getUserJson();

				if (cursor == null) {
					// 数据库为空
					json.saveUserJson(response);
					// 添加数据
					Log.d(TAG, "loadData");
					loadData(cursor);

				} else {
					// 数据库不为空
					// 添加数据
					Log.d(TAG, "loadData");
					loadData(cursor);
					
					json.updataUserJson(response);
				}

				Log.d(TAG, response);
				
			}
		}

	};

	protected void loadData(Cursor cursor) {

		UserInfo userInfo = new UserInfo();
		Log.d(TAG, "user");

		while (cursor.moveToNext()) {
			Log.d(TAG, "cursor" + cursor.toString());
			
			String jsonString = cursor.getString(1);
			userInfo = UserInfo.parse(jsonString);
			Log.d(TAG, "loadData:" + jsonString);

			textViewName.setText(userInfo.screen_name);

			// 给listViewFuction中加载数据
			Map<String, Object> friends = new HashMap<String, Object>();
			friends.put(KEY_IMAGE, R.drawable.friend);
			friends.put(KEY_CATEGORY, "关注");
			friends.put(KEY_NUMBER, userInfo.friends_count);

			Map<String, Object> followers = new HashMap<String, Object>();
			followers.put(KEY_IMAGE, R.drawable.followers);
			followers.put(KEY_CATEGORY, "粉丝");
			followers.put(KEY_NUMBER, userInfo.followers_count);

			Map<String, Object> statuses = new HashMap<String, Object>();
			statuses.put(KEY_IMAGE, R.drawable.status);
			statuses.put(KEY_CATEGORY, "微博");
			statuses.put(KEY_NUMBER, userInfo.statuses_count);

			dataSet.add(friends);
			dataSet.add(followers);
			dataSet.add(statuses);
		}
		cursor.close();
		adapter.notifyDataSetChanged();
	}

	protected void showToast(String string) {
		Toast.makeText(getActivity(), string, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		switch (position) {
		case 0:
			// 关注
			Intent intentFriends = new Intent(getActivity(), FriendsActivity.class);
			startActivity(intentFriends);
			break;
		case 1:
			// 粉丝
			Intent intentFollowers = new Intent(getActivity(), FollowsActivity.class);
			startActivity(intentFollowers);
			break;
		case 2:
			// 微博
			Intent intentStatus = new Intent(getActivity(), StatusActivity.class);
			startActivity(intentStatus);
			break;

		}
	}

}
