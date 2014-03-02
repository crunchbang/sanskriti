package com.crunchbang.sanskriti;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	String[] names;
	String[] classes;
	ActionBarDrawerToggle drawerToggle;
	DrawerLayout drawer;
	ListView navList;

	private int selection = 0;
	private int oldSelection = -1;

	public static final String KEY = "com.crunchbang.sanskriti.MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		names = getResources().getStringArray(R.array.nav_names);
		classes = getResources().getStringArray(R.array.nav_classes);

		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerToggle = new ActionBarDrawerToggle(this, drawer,
				R.drawable.ic_drawer, R.string.open, R.string.close) {
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				updateContent();
				invalidateOptionsMenu();
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

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, names);
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
			case 3:
				value = "inter";
				break;
			}
			Bundle bundle = new Bundle();
			bundle.putString(KEY, value);
			FragmentTransaction tx = getSupportFragmentManager()
					.beginTransaction();
			tx.replace(R.id.main, Fragment.instantiate(MainActivity.this,
					classes[selection], bundle));
			if (oldSelection != -1)
				tx.addToBackStack(null);
			tx.commit();
			oldSelection = selection;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		getActionBar().setTitle(names[0]);
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
		switch (item.getItemId()) {
		case R.id.about_developers:
			AlertDialog.Builder devInfo = new AlertDialog.Builder(this);
			devInfo.setTitle("Developed By");
			devInfo.setMessage(R.string.developers).setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					});
			AlertDialog alert = devInfo.create();
			alert.show();
			break;

		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
