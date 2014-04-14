package com.newer.doudoule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newer.doudoule.api.FriendsAPI;
import com.newer.doudoule.api.FriendsList;
import com.newer.doudoule.dao.PutJson;
import com.newer.doudoule.dao.UserInfo;
import com.sina.weibo.sdk.openapi.models.User;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class FollowsActivity extends Activity {

	private static final String TAG = "FollowsActivity";

	private static final String KEY_NAME = "name";
	private static final String KEY_STATUS = "status";
	private static final String KEY_IMAGE = "image";

	private List<UserInfo> list;

	private ListView listView;
	private List<Map<String, Object>> dataSet;
	private SimpleAdapter adapter;

	private String[] from = {KEY_IMAGE, KEY_NAME, KEY_STATUS};
	private int[] to = { R.id.imageView_friend, R.id.textView_friend_name,
			R.id.textView_friend_introduce };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend);

		String url = "https://api.weibo.com/2/friendships/followers.json";
		FriendsAPI api = new FriendsAPI(url, this);
		
		PutJson json = new PutJson(getApplicationContext());
		Cursor cursor = json.getFollowsJson();
		
		initView();
		loadData(cursor);
	}
	private void initView() {
		listView = (ListView) findViewById(R.id.listView_friend_activity);
		dataSet = new ArrayList<Map<String, Object>>();
		adapter = new SimpleAdapter(this, dataSet,
				R.layout.activity_friend_item, from, to);
		listView.setAdapter(adapter);

	}
	private void loadData(Cursor cursor) {
		while (cursor.moveToNext()) {
			String jsonString = cursor.getString(1);
			Log.d(TAG, "cursor:" + jsonString);

			list = FriendsList.parse(jsonString).FriendsList;
			Log.d(TAG, "list" + list.toString());
			
			Map<String, Object> data;
			for (UserInfo userInfo : list) {
				Log.d(TAG, "users:" + userInfo.screen_name + userInfo.description + userInfo.status_id);
				
				data = new HashMap<String, Object>();
				data.put(KEY_NAME, userInfo.screen_name);
				data.put(KEY_STATUS, userInfo.status_id);
				data.put(KEY_IMAGE, R.drawable.ic_launcher);
				Log.d(TAG, data.toString());
				
				dataSet.add(data);
			}
		}
		cursor.close();
		adapter.notifyDataSetChanged();
	}
}
