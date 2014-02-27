package com.crunchbang.sanskriti;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class DetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		
		/*
		Bundle bundle = getIntent().getExtras();
		int itemID = bundle.getInt(EventListFragment.KEY);
		
		new DetailsLoaderTask().execute();
		*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details, menu);
		return true;
	}
	
	private class DetailsLoaderTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
		}
		
	}
	

}
