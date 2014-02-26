package com.crunchbang.sanskriti;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;

import com.crunchbang.sanskriti.dbhelper.DataBaseHelper;

public class EventListActivity extends ListActivity {

	DataBaseHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_list);
		dbHelper = new DataBaseHelper(getApplicationContext());
		new LoadListTask(getApplicationContext()).execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_list, menu);
		return true;
	}

	private class LoadListTask extends AsyncTask<Void, Void, Void> {
		Cursor cursor;
		Context context;
		
		public LoadListTask(Context context) {
			this.context = context;
		}

		@Override
		protected Void doInBackground(Void... params) {
			cursor = dbHelper.getEvents();
			cursor.moveToFirst();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			String[] from = new String[] { DataBaseHelper.KEY_ENAME };
			int[] to = new int[] { R.id.ename };

			CustomCursorAdapter adapter = new CustomCursorAdapter(context,
					R.layout.list_event_row, cursor, from, to);
			setListAdapter(adapter);
		}

	}

}
