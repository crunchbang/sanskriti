package com.crunchbang.sanskriti;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.crunchbang.sanskriti.dbhelper.DataBaseHelper;

public class EventListFragment extends ListFragment {

	public static final String KEY = "com.crunchbang.sanskriti.EventListFragment";
	DataBaseHelper dbHelper;
	View view;
	String eventType;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_event_list, container, false);
		dbHelper = DataBaseHelper.getInstance(getActivity()
				.getApplicationContext());
		eventType = getArguments().getString(MainActivity.KEY);
		new LoadListTask(getActivity().getApplicationContext()).execute();
		return view;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Cursor itemCursor = (Cursor) getListAdapter().getItem(position);
		Log.d("TAG", "CLICKED!");
		int itemID = itemCursor.getInt(itemCursor
				.getColumnIndex(DataBaseHelper.KEY_ID));
		Bundle bundle = new Bundle();
		bundle.putInt(KEY, itemID);

		Intent intent = new Intent(getActivity(), DetailsActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	private class LoadListTask extends AsyncTask<Void, Void, Void> {
		Cursor cursor;
		Context context;

		public LoadListTask(Context context) {
			this.context = context;
		}

		@Override
		protected Void doInBackground(Void... params) {
			cursor = dbHelper.getEvents(eventType);
			cursor.moveToFirst();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			/*
			 * String[] from = new String[] { DataBaseHelper.KEY_ENAME }; int[]
			 * to = new int[] { R.id.ename };
			 * 
			 * CustomCursorAdapter adapter = new CustomCursorAdapter(context,
			 * R.layout.list_event_row, cursor, from, to);
			 */
			ImageCursorAdapter adapter = new ImageCursorAdapter(context, cursor);
			setListAdapter(adapter);
		}

	}

}
