package com.crunchbang.sanskriti;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crunchbang.sanskriti.dbhelper.DataBaseHelper;

public class CustomCursorAdapter extends SimpleCursorAdapter {
	Cursor cursor;
	Context context;

	public CustomCursorAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to) {
		super(context, layout, c, from, to, 0);
		this.cursor = c;
		this.context = context;
	}

	@Override
	public View getView(int pos, View view, ViewGroup parent) {
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.list_event_row, null);
		}
		cursor.moveToPosition(pos);
		String eventName = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_ENAME));
		String eventDate = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_EDATE));
		byte[] eventPic = cursor.getBlob(cursor
				.getColumnIndex(DataBaseHelper.KEY_EPIC));

		TextView tv = (TextView) view.findViewById(R.id.ename);
		TextView tvDate = (TextView) view.findViewById(R.id.edate);
		ImageView iv = (ImageView) view.findViewById(R.id.eimage);

		tv.setText(eventName);
		tvDate.setText(eventDate);
		if (eventPic != null) {
			iv.setImageBitmap(BitmapFactory.decodeByteArray(eventPic, 0,
					eventPic.length));
		}
		
		return view;

	}

}
