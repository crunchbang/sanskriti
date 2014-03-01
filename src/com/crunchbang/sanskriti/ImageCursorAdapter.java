package com.crunchbang.sanskriti;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crunchbang.sanskriti.dbhelper.DataBaseHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ImageCursorAdapter extends CursorAdapter {
	Cursor cursor;
	Context context;
	DisplayImageOptions options;
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
		options = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisc(true).resetViewBeforeLoading(true).build();
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = mInflater.inflate(R.layout.list_event_row, parent, false);
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
		/*
		 * byte[] eventPic = cursor.getBlob(viewHolder.imageIndex); if
		 * (eventPic.length > 3) {
		 * viewHolder.image.setImageBitmap(BitmapFactory.decodeByteArray(
		 * eventPic, 0, eventPic.length)); }
		 */
		if (!picURI.equals(""))
			imageLoader.displayImage(picURI, viewHolder.image, options);

	}
	/*
	 * @Override public View getView(int pos, View view, ViewGroup parent) {
	 * ViewHolder viewHolder; if (view == null) { view =
	 * inflater.inflate(R.layout.list_event_row, null); viewHolder = new
	 * ViewHolder(); viewHolder.text = (TextView) view.findViewById(R.id.ename);
	 * viewHolder.date = (TextView) view.findViewById(R.id.edate);
	 * viewHolder.image = (ImageView) view.findViewById(R.id.eimage);
	 * view.setTag(viewHolder); } else { viewHolder = (ViewHolder)
	 * view.getTag(); }
	 * 
	 * cursor.moveToPosition(pos); String eventName = cursor.getString(cursor
	 * .getColumnIndex(DataBaseHelper.KEY_ENAME)); String eventDate =
	 * cursor.getString(cursor .getColumnIndex(DataBaseHelper.KEY_EDATE));
	 * byte[] eventPic = cursor.getBlob(cursor
	 * .getColumnIndex(DataBaseHelper.KEY_EPIC));
	 * 
	 * viewHolder.text.setText(eventName); viewHolder.date.setText(eventDate);
	 * if (eventPic != null && eventPic.length > 3) {
	 * viewHolder.image.setImageBitmap(BitmapFactory.decodeByteArray(eventPic,
	 * 0, eventPic.length)); }
	 * 
	 * return view;
	 * 
	 * }
	 */
}
