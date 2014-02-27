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

	private int selection = 0;
	private int oldSelection = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		names = getResources().getStringArray(R.array.nav_names);
		classes = getResources().getStringArray(R.array.nav_classes);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActionBar()
				.getThemedContext(), android.R.layout.simple_list_item_1, names);
		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerToggle = new ActionBarDrawerToggle(this, drawer,
				R.drawable.ic_drawer, R.string.open, R.string.close) {
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				updateContent();
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
		final ListView navList = (ListView) findViewById(R.id.drawer);
		navList.setAdapter(adapter);
		navList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				selection = position;
				drawer.closeDrawer(navList);
			}
		});
		FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
		tx.replace(R.id.main, new EventListFragment());
		tx.commit();
	}

	private void updateContent() {
		getActionBar().setTitle(names[selection]);
		if (selection != oldSelection) {
			FragmentTransaction tx = getSupportFragmentManager()
					.beginTransaction();
			tx.replace(R.id.main,
					Fragment.instantiate(MainActivity.this, classes[selection]));
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
