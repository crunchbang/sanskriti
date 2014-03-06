package com.crunchbang.sanskriti.helper;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crunchbang.sanskriti.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ImageCursorAdapter extends CursorAdapter {
	Cursor cursor;
	Context context;
	ImageLoader imageLoader;
	private LayoutInflater mInflater;

	private static class ViewHolder {
		public int textIndex;
		public int dateIndex;
		public int imageIndex;
		public TextView text;
		public TextView date;
		public ImageView image;
	}

	public ImageCursorAdapter(Context context, Cursor c) {
		super(context, c, 0);
		this.cursor = c;
		this.context = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = ImageLoader.getInstance();
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = mInflater.inflate(R.layout.event_list_row, parent, false);
		ViewHolder viewHolder = new ViewHolder();
		viewHolder.text = (TextView) view.findViewById(R.id.ename);
		viewHolder.date = (TextView) view.findViewById(R.id.edate);
		viewHolder.image = (ImageView) view.findViewById(R.id.eimage);
		viewHolder.textIndex = cursor
				.getColumnIndexOrThrow(DataBaseHelper.KEY_ENAME);
		viewHolder.dateIndex = cursor
				.getColumnIndexOrThrow(DataBaseHelper.KEY_EDATE);
		viewHolder.imageIndex = cursor
				.getColumnIndexOrThrow(DataBaseHelper.KEY_PICLOC);
		view.setTag(viewHolder);
		return view;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder viewHolder = (ViewHolder) view.getTag();
		viewHolder.text.setText(cursor.getString(viewHolder.textIndex));
		viewHolder.date.setText(cursor.getString(viewHolder.dateIndex));
		String picURI = cursor.getString(viewHolder.imageIndex);
		if (!picURI.equals(""))
			imageLoader.displayImage(picURI, viewHolder.image);

	}
}
