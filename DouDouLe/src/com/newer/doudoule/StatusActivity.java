package com.newer.doudoule;

import com.sina.weibo.sdk.openapi.legacy.StatusesAPI;

import android.app.Activity;
import android.app.Service;
import android.os.Bundle;
import android.widget.ListView;

public class StatusActivity extends Activity {

	private ListView listView;
	private StatusesAPI statusesAPI;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status);
		
		initView();
	}

	private void initView() {
		
		listView = (ListView) findViewById(R.id.listView_status);
		
	}
}
