package com.crunchbang.sanskriti;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends FragmentActivity {

	String[] names;
	String[] classes;
	ActionBarDrawerToggle drawerToggle;
	DrawerLayout drawer;
	ListView navList;

	public static final String KEY = "com.crunchbang.sanskriti.MainActivity";

	private int selection = 0;
	private int oldSelection = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		names = getResources().getStringArray(R.array.nav_names);
		classes = getResources().getStringArray(R.array.nav_classes);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, names);
		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerToggle = new ActionBarDrawerToggle(this, drawer,
				R.drawable.ic_drawer, R.string.open, R.string.close) {
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				updateContent();
				invalidateOptionsMenu();
				/*
				 * if (opened != null && opened == false) { opened = true; if
				 * (pref != null) { Editor editor = pref.edit();
				 * editor.putBoolean(OPENED_KEY, true); editor.apply(); } }
				 */
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle(R.string.app_name);
			}

		};
		drawer.setDrawerListener(drawerToggle);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		updateContent();
		navList = (ListView) findViewById(R.id.drawer);
		navList.setAdapter(adapter);
		navList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				selection = position;
				navList.setItemChecked(position, true);
				drawer.closeDrawer(navList);
			}
		});

		/*
		 * new Thread(new Runnable() {
		 * 
		 * @Override public void run() { pref = getPreferences(MODE_PRIVATE);
		 * opened = pref.getBoolean(OPENED_KEY, false); if (opened == false) {
		 * drawer.openDrawer(navList); }
		 * 
		 * } }).start();
		 */
		FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
		tx.replace(R.id.main, new HomeFragment());
		tx.commit();
		drawer.openDrawer(navList);
	}

	private void updateContent() {
		getActionBar().setTitle(names[selection]);
		if (selection != oldSelection) {
			String value = null;
			switch (selection) {
			case 1:
				value = "intra";
				break;
			case 2:
				value = "sports";
				break;
			}
			Bundle bundle = new Bundle();
			bundle.putString(KEY, value);
			FragmentTransaction tx = getSupportFragmentManager()
					.beginTransaction();
			tx.replace(R.id.main, Fragment.instantiate(MainActivity.this,
					classes[selection], bundle));
			tx.addToBackStack(null);
			tx.commit();
			oldSelection = selection;
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
