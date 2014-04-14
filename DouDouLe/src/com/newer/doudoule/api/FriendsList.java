package com.newer.doudoule.api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.newer.doudoule.dao.UserInfo;
import com.sina.weibo.sdk.openapi.models.User;

/**
 * 关注列表结构体
 * @author Administrator
 *
 */
public class FriendsList {

	public List<UserInfo> FriendsList;

	public static FriendsList parse(String jsonString) {

		if (TextUtils.isEmpty(jsonString)) {
			return null;
		}

		FriendsList friends = new FriendsList();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			
			JSONArray jsonArray = jsonObject.optJSONArray("users");
			if (jsonArray != null && jsonArray.length() > 0) {

				int length = jsonArray.length();
				friends.FriendsList = new ArrayList<UserInfo>(length);
				for (int i = 0; i < length; i++) {
					friends.FriendsList.add(UserInfo.parse(jsonArray
							.optJSONObject(i)));
				}
				return friends;
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;

	}
}
