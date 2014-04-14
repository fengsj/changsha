package com.newer.doudoule.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper{

	private static final String DbName = "doudoule.db";

	// 表名及列名
	public static final  String TABLE_NAME = "doudoule";
	public static final String TABLE_ID = "_id";
	public static final String TABLE_USER_JSON = "user_json";
	public static final String TABLE_FRIENDS_JSON = "friends_json";
	public static final String TABLE_FOLLOWERS_JSON = "followers_json";
	public static final String TABLE_STATUSES_JSON = "statuses";

	private static final String CREATE_TABLE = "create table " + TABLE_NAME
			+ "(" + TABLE_ID + " integer primary key autoincrement, " 
			+ TABLE_USER_JSON + " real, "
			+ TABLE_FRIENDS_JSON + " real, "
			+ TABLE_FOLLOWERS_JSON + " real,"
			+ TABLE_STATUSES_JSON + "real)";

	private static final String DROP_TABLE = "drop table if exists"
			+ TABLE_NAME;

	public DbOpenHelper(Context context) {
		super(context, DbName, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DROP_TABLE);
		onCreate(db);
	}
}
