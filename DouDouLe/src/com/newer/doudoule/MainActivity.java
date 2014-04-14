package com.newer.doudoule;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnItemClickListener {

	/**
	 * drawer中的组件
	 */
	private DrawerLayout drawerLayout;
	private FrameLayout drawerFrameLayout;
	private LinearLayout drawerlLinearLayout;
	private TextView drawertTextView;
	private ListView drawerListView;

	/**
	 * actionBar中的组件
	 */
	private ActionBar actionBar;
	// drawer在ActionBar中的开关
	private ActionBarDrawerToggle drawerToggle;
	private MenuItem actionSearch;
	

	/**
	 * drawerListView中的数据
	 */
	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initDrawer();
		addListData();
		initActionBar();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}

	private void initActionBar() {
		actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	private void addListData() {
		String[] listItem = getResources().getStringArray(R.array.drawer_list);

		adapter = new ArrayAdapter<String>(this, R.layout.drawer_list_item,
				R.id.textView_list, listItem);

		drawerListView.setAdapter(adapter);

	}

	private void initDrawer() {

		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerFrameLayout = (FrameLayout) findViewById(R.id.drawer_frame);
		drawerlLinearLayout = (LinearLayout) findViewById(R.id.drawer_linear);
		drawertTextView = (TextView) findViewById(R.id.drawer_text);
		drawerListView = (ListView) findViewById(R.id.drawer_list);

		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				actionBar.setTitle(R.string.drawer_close);
				//actionSearch.setVisible(false);
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				actionBar.setTitle(R.string.app_name);
				//actionSearch.setVisible(true);
			}
		};

		drawerLayout.setDrawerListener(drawerToggle);
		drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);
		
		drawerListView.setOnItemClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		//actionSearch = menu.findItem(R.id.action_search);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		//判断，为真则点击按钮打开抽屉，否则关闭
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		FragmentManager manager = getFragmentManager();
		
		switch (position) {
		case 0:
			//首页，放自己的个人中心
			HomeFragment homeFragment = new HomeFragment();
			manager.beginTransaction().replace(R.id.drawer_frame, homeFragment).commit();
			drawerLayout.closeDrawers();
			break;
		case 1:
			//待定
			break;
		case 2:
			//待定
			break;
		case 3:
			//待定
			break;

		default:
			break;
		}
	}

	

}
