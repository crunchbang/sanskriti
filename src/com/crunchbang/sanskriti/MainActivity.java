package com.crunchbang.sanskriti;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

	String[] names;
	String[] classes;
	ActionBarDrawerToggle drawerToggle;
	DrawerLayout drawer;
	ListView navList;

	private int selection = 0;
	private int oldSelection = -1;

	public static final String KEY = "MainActivity";

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
				supportInvalidateOptionsMenu();
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getSupportActionBar().setTitle(R.string.app_name);
			}

		};
		drawer.setDrawerListener(drawerToggle);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
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
		getSupportActionBar().setTitle(names[selection]);
		// if (selection != oldSelection) {
		String value = null;
		if (selection == 6) {
			Intent intent = new Intent(this, ResultActivity.class);
			startActivity(intent);
		}

		else {
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
			case 4:
				value = "concert";
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
		}
		oldSelection = selection;
		// }
	}

	@Override
	protected void onResume() {
		super.onResume();
		getSupportActionBar().setTitle(names[0]);
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
		AlertDialog.Builder infoDialog = new AlertDialog.Builder(this);
		switch (item.getItemId()) {
		case R.id.about_developers:
			infoDialog.setTitle("Developed By");
			/*
			 * infoDialog.setMessage(R.string.developers).setPositiveButton("OK",
			 * new DialogInterface.OnClickListener() {
			 * 
			 * @Override public void onClick(DialogInterface dialog, int which)
			 * {
			 * 
			 * } });
			 */
			ImageView iv = new ImageView(this);
			iv.setImageResource(R.drawable.dev);
			iv.setBackgroundColor(Color.parseColor("#00000000"));
			iv.setScaleType(ScaleType.FIT_CENTER);
			infoDialog.setView(iv);
			break;
		case R.id.about_organizers:
			infoDialog.setTitle("Organizers");
			WebView wv = new WebView(this);
			wv.loadUrl("file:///android_asset/about.html");

			infoDialog.setView(wv);
			infoDialog.setNegativeButton("Close",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							dialog.dismiss();
						}
					});

		}
		AlertDialog alert = infoDialog.create();
		alert.show();
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
