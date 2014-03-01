package com.crunchbang.sanskriti;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crunchbang.sanskriti.helper.DataBaseHelper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class DetailsActivity extends Activity {

	int itemID;
	DataBaseHelper dbHelper;
	TextView tvName, tvDesc, tvHead1, tvHead2, tvDate, tvRules, tvEPhone1,
			tvEPhone2;
	View ruleLayout, phone1Layout, phone2Layout;
	ImageView ivPic;
	ImageLoader imageLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		dbHelper = DataBaseHelper.getInstance(getApplicationContext());
		Bundle bundle = getIntent().getExtras();
		itemID = bundle.getInt(EventListFragment.KEY);
		tvName = (TextView) findViewById(R.id.tv_ename);
		tvDate = (TextView) findViewById(R.id.tv_edate);
		tvDesc = (TextView) findViewById(R.id.tv_edesc);
		tvHead1 = (TextView) findViewById(R.id.tv_ehead1);
		tvHead2 = (TextView) findViewById(R.id.tv_ehead2);
		tvRules = (TextView) findViewById(R.id.tv_erules);
		ivPic = (ImageView) findViewById(R.id.iv_epic);
		tvEPhone1 = (TextView) findViewById(R.id.tv_ephone1);
		tvEPhone2 = (TextView) findViewById(R.id.tv_ephone2);

		ruleLayout = findViewById(R.id.card_rules);
		phone1Layout = findViewById(R.id.phone1);
		phone2Layout = findViewById(R.id.phone2);

		// Temporary adjustment
		phone1Layout.setVisibility(View.GONE);
		phone2Layout.setVisibility(View.GONE);

		new DetailsLoaderTask().execute();
	}

	private class DetailsLoaderTask extends AsyncTask<Void, Void, Void> {
		Cursor cursor;
		String eName, eDate, eHead1, eHead2, eDesc, eRules, picLoc;

		@Override
		protected Void doInBackground(Void... params) {
			eDate = null;
			cursor = dbHelper.getEventDetails(itemID);
			cursor.moveToFirst();
			eName = cursor.getString(cursor
					.getColumnIndex(DataBaseHelper.KEY_ENAME));
			eDate = cursor.getString(cursor
					.getColumnIndexOrThrow(DataBaseHelper.KEY_EDATE));
			eHead1 = cursor.getString(cursor
					.getColumnIndex(DataBaseHelper.KEY_EHEAD1));
			eHead2 = cursor.getString(cursor
					.getColumnIndex(DataBaseHelper.KEY_EHEAD2));
			eDesc = cursor.getString(cursor
					.getColumnIndex(DataBaseHelper.KEY_EDESC));
			picLoc = cursor.getString(cursor
					.getColumnIndex(DataBaseHelper.KEY_PICLOC));
			eRules = cursor.getString(cursor
					.getColumnIndex(DataBaseHelper.KEY_ERULES));
			cursor.close();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			tvName.setText(eName);
			tvDate.setText(eDate);
			tvHead1.setText(eHead1);
			tvHead2.setText(eHead2);
			tvDesc.setText(eDesc);
			if (eRules.equals(""))
				ruleLayout.setVisibility(View.GONE);
			else
				tvRules.setText(eRules);
			imageLoader.displayImage(picLoc, ivPic);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details, menu);
		return true;
	}
}
