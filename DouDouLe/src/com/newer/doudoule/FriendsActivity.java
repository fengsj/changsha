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

public class FriendsActivity extends Activity {

	private static final String TAG = "FriendsActivity";

	private static final String KEY_NAME = "name";
	private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_IMAGE = "image";

	private List<UserInfo> list;

	private ListView listView;
	private List<Map<String, Object>> dataSet;
	private SimpleAdapter adapter;

	private String[] from = {KEY_IMAGE, KEY_NAME, KEY_DESCRIPTION};
	private int[] to = { R.id.imageView_friend, R.id.textView_friend_name,
			R.id.textView_friend_introduce };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "oncreate");

		setContentView(R.layout.activity_friend);
		initView();

		String url = "https://api.weibo.com/2/friendships/friends.json";
		FriendsAPI api = new FriendsAPI(url, this);

		PutJson json = new PutJson(getApplicationContext());
		Cursor cursor = json.getFriendsJson();

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
				Log.d(TAG, "users:" + userInfo.screen_name + userInfo.id + userInfo.avatar_hd);
				
				data = new HashMap<String, Object>();
				data.put(KEY_NAME, userInfo.screen_name);
				data.put(KEY_DESCRIPTION, userInfo.description);
				data.put(KEY_IMAGE, R.drawable.ic_launcher);
				
				dataSet.add(data);
			}
		}
		cursor.close();
		adapter.notifyDataSetChanged();
	}
}
