package com.newer.doudoule.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PutJson {

	private SQLiteDatabase db;

	public PutJson(Context context) {
		super();
		db = new DbOpenHelper(context).getWritableDatabase();
	}

	public void saveUserJson(String response) {
		ContentValues values = new ContentValues();
		values.put(DbOpenHelper.TABLE_USER_JSON, response);

		db.insert(DbOpenHelper.TABLE_NAME, null, values);
	}

	public void updataUserJson(String response) {
		ContentValues values = new ContentValues();
		values.put(DbOpenHelper.TABLE_USER_JSON, response);

		db.update(DbOpenHelper.TABLE_NAME, values, null, null);
	}

	public void updataFriends(String response) {
		ContentValues values = new ContentValues();
		values.put(DbOpenHelper.TABLE_FRIENDS_JSON, response);

		db.update(DbOpenHelper.TABLE_NAME, values, null, null);
	}

	public void updataFollowers(String response) {
		ContentValues values = new ContentValues();
		values.put(DbOpenHelper.TABLE_FOLLOWERS_JSON, response);

		db.update(DbOpenHelper.TABLE_NAME, values, null, null);
	}

	public void updataStatuses(String response) {
		ContentValues values = new ContentValues();
		values.put(DbOpenHelper.TABLE_STATUSES_JSON, response);

		db.update(DbOpenHelper.TABLE_NAME, values, null, null);
	}

	// 获取用户的JSON信息
	public Cursor getUserJson() {
		Cursor cursor = db.query(DbOpenHelper.TABLE_NAME, new String[] {
				DbOpenHelper.TABLE_ID, DbOpenHelper.TABLE_USER_JSON }, null,
				null, null, null, null);

		return cursor;
	}

	// 获取用户所关注的JSON信息
	public Cursor getFriendsJson() {
		Cursor cursor = db.query(DbOpenHelper.TABLE_NAME, new String[] {
				DbOpenHelper.TABLE_ID, DbOpenHelper.TABLE_FRIENDS_JSON }, null,
				null, null, null, null);

		return cursor;
	}

	// 获取用户粉丝的JSON信息
	public Cursor getFollowsJson() {

		Cursor cursor = db.query(DbOpenHelper.TABLE_NAME, new String[] {
				DbOpenHelper.TABLE_ID, DbOpenHelper.TABLE_FOLLOWERS_JSON },
				null, null, null, null, null);

		return cursor;
	}

	// 获取用户微博的JSON信息
	public Cursor getStatuseJson() {
		Cursor cursor = db.query(DbOpenHelper.TABLE_NAME, new String[] {
				DbOpenHelper.TABLE_ID, DbOpenHelper.TABLE_STATUSES_JSON },
				null, null, null, null, null);
		
		return cursor;
	}

}
