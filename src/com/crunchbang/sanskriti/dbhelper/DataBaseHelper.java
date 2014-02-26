package com.crunchbang.sanskriti.dbhelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DataBaseHelper extends SQLiteAssetHelper {
	private static final String DATABASE_NAME = "events.sqlite";
	private static final int DATABASE_VERSION = 1;

	public static final String TABLE_EVENTS = "events";
	public static final String KEY_ID = "_id";
	public static final String KEY_ENAME = "ename";
	public static final String KEY_EDATE = "edate";
	public static final String KEY_ETIME = "etime";
	public static final String KEY_EPIC = "epic";

	private static SQLiteDatabase db;

	public static final String[] EVENTS_PROJECTION = new String[] { KEY_ID,
			KEY_ENAME, KEY_EDATE, KEY_ETIME, KEY_EPIC };

	public DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		setForcedUpgrade(DATABASE_VERSION);
	}

	public Cursor getEvents() {
		db = getReadableDatabase();
		Cursor cursor = db.query(TABLE_EVENTS, EVENTS_PROJECTION, null, null,
				null, null, null);
		return cursor;
	}

	@Override
	public synchronized void close() {
		super.close();
		db.close();
	}

}
