package com.crunchbang.sanskriti;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.crunchbang.sanskriti.helper.DataBaseHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

public class DetailsActivity extends ActionBarActivity {

	int itemID;
	DataBaseHelper dbHelper;
	TextView tvName, tvDesc, tvHead1, tvHead2, tvDate, tvRules, tvPhone1,
			tvPhone2, tvVenue, tvPrize, tvTime;
	View ruleLayout, phone1Layout, phone2Layout, prizeVenueLayout, descLayout,
			head2Layout, head1Layout;
	ImageView ivPic;
	ImageLoader imageLoader;
	String ePhone1, ePhone2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		imageLoader = ImageLoader.getInstance();
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
		tvPhone1 = (TextView) findViewById(R.id.tv_ephone1);
		tvPhone2 = (TextView) findViewById(R.id.tv_ephone2);
		tvPrize = (TextView) findViewById(R.id.tv_eprize);
		tvVenue = (TextView) findViewById(R.id.tv_evenue);
		tvTime = (TextView) findViewById(R.id.tv_etime);

		descLayout = findViewById(R.id.card_details);
		ruleLayout = findViewById(R.id.card_rules);
		phone1Layout = findViewById(R.id.phone1);
		phone2Layout = findViewById(R.id.phone2);
		head2Layout = findViewById(R.id.call2);
		head1Layout = findViewById(R.id.call1);
		prizeVenueLayout = findViewById(R.id.prize_venue);

		new DetailsLoaderTask().execute();

		head1Layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent(Intent.ACTION_DIAL);
				intent.setData(Uri.parse("tel:" + ePhone1));
				startActivity(intent);

			}
		});
		head2Layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent(Intent.ACTION_DIAL);
				intent.setData(Uri.parse("tel:" + ePhone2));
				startActivity(intent);

			}
		});
	}

	private class DetailsLoaderTask extends AsyncTask<Void, Void, Void> {
		Cursor cursor;
		String eName, eDate, eHead1, eHead2, eDesc, eRules, picLoc, ePrize,
				eVenue, eTime;

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
			ePhone1 = cursor.getString(cursor
					.getColumnIndex(DataBaseHelper.KEY_ECONTACT1));
			ePhone2 = cursor.getString(cursor
					.getColumnIndex(DataBaseHelper.KEY_ECONTACT2));
			eDesc = cursor.getString(cursor
					.getColumnIndex(DataBaseHelper.KEY_EDESC));
			picLoc = cursor.getString(cursor
					.getColumnIndex(DataBaseHelper.KEY_PICLOC));
			eRules = cursor.getString(cursor
					.getColumnIndex(DataBaseHelper.KEY_ERULES));
			ePrize = cursor.getString(cursor
					.getColumnIndex(DataBaseHelper.KEY_EPRIZE));
			eVenue = cursor.getString(cursor
					.getColumnIndex(DataBaseHelper.KEY_EVENUE));
			eTime = cursor.getString(cursor
					.getColumnIndex(DataBaseHelper.KEY_ETIME));
			cursor.close();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			tvName.setText(eName);
			tvDate.setText(eDate);
			tvHead1.setText(eHead1);

			if (eDesc.equals(""))
				descLayout.setVisibility(View.GONE);
			else
				tvDesc.setText(eDesc);

			if (eRules.equals(""))
				ruleLayout.setVisibility(View.GONE);
			else
				tvRules.setText(eRules);

			if (ePhone1.equals(""))
				phone1Layout.setVisibility(View.GONE);
			else
				tvPhone1.setText(ePhone1);

			if (eHead2.equals(""))
				head2Layout.setVisibility(View.GONE);
			else {
				tvHead2.setText(eHead2);
				if (ePhone2.equals(""))
					phone2Layout.setVisibility(View.GONE);
				else
					tvPhone2.setText(ePhone2);
			}

			if (ePrize.equals(""))
				prizeVenueLayout.setVisibility(View.GONE);
			else {
				tvPrize.setText(ePrize);
				tvVenue.setText(eVenue);
			}

			tvTime.setText(eTime);
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
