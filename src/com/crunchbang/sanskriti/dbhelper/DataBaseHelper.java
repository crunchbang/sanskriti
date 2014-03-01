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
	public static final String KEY_EDESC = "edesc";
	public static final String KEY_EHEAD1 = "ehead1";
	public static final String KEY_EHEAD2 = "ehead2";
	public static final String KEY_EPIC = "epic";
	public static final String KEY_ETYPE = "etype";
	public static final String KEY_ERULES = "erules";
	public static final String KEY_PICLOC = "picloc";

	private static SQLiteDatabase db;
	private static DataBaseHelper sInstance;

	public static final String[] EVENTS_PROJECTION = new String[] { KEY_ID,
			KEY_ENAME, KEY_EDATE, KEY_EDESC, KEY_EHEAD1, KEY_EHEAD2, KEY_EPIC,
			KEY_ETYPE, KEY_ERULES, KEY_PICLOC };

	public static DataBaseHelper getInstance(Context context) {
		if (sInstance == null) {
			sInstance = new DataBaseHelper(context.getApplicationContext());
		}
		return sInstance;
	}

	private DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		setForcedUpgrade(DATABASE_VERSION);
	}

	public Cursor getEvents(String eventType) {
		db = this.getReadableDatabase();
		String[] listProjection = new String[] { KEY_ID, KEY_ENAME, KEY_EDATE,
				KEY_EPIC, KEY_ETYPE, KEY_PICLOC };
		String where = null;
		if (eventType != null)
			where = KEY_ETYPE + "=" + "'" + eventType + "'";
		Cursor cursor = db.query(TABLE_EVENTS, listProjection, where, null,
				null, null, KEY_EDATE);
		return cursor;
	}

	public Cursor getEventDetails(int eventID) {
		String where = KEY_ID + "=" + "'" + eventID + "'";
		db = this.getReadableDatabase();
		return db.query(TABLE_EVENTS, EVENTS_PROJECTION, where, null, null,
				null, KEY_EDATE);
	}

	@Override
	public synchronized void close() {
		super.close();
		db.close();
	}

}
