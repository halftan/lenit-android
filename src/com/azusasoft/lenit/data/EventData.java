package com.azusasoft.lenit.data;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class EventData{
	private static final String TAG = EventData.class.getSimpleName();
	
	static final String DB_NAME = "event.db";
	static final int DB_VERSION = 1;
	
	public static final String TABLE = "event";
	
	public static final String C_ID = BaseColumns._ID; 
	public static final String C_CREATED_AT = "created_at";
	public static final String C_NAME = "name";
	public static final String C_DESCRIPTION = "description";
	public static final String C_TIME = "time";
	public static final String C_DURATION = "duration";
	public static final String C_HOST_NAME = "host_name";
	public static final String C_LOCATION = "location";
	
	public static final String GET_ALL_ORDER_BY = C_CREATED_AT + " DESC";
	
	public static final String[] MAX_CREATED_AT_COLUMNS = {
		"max(" + EventData.C_CREATED_AT + ")"
	};
	public static final String[] MAX_ID_COLUMNS = {
		"max(" + EventData.C_ID + ")"
	};
	
	public static final String[] DB_EVENT_COLUMNS = {
		C_CREATED_AT, C_NAME, C_DESCRIPTION, C_TIME, C_DURATION, C_HOST_NAME, C_LOCATION
	};

	public static class DbHelper extends SQLiteOpenHelper {
		static final String TAG = "DbHelper";
		Context mContext;
	
		public DbHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
			this.mContext = context;
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			String sql = String.format("create table %s (" +
					"%s int primary key, %s varchar(255), " +
					"%s varchar(255), %s text, " +
					"%s varchar(255), %s integer, %s varchar(255), " +
					"%s varchar(255))", TABLE, C_ID, C_CREATED_AT,
					C_NAME, C_DESCRIPTION, C_TIME, C_DURATION, C_HOST_NAME,
					C_LOCATION);
			
			db.execSQL(sql);
			Log.d(TAG, "onCreated sql: " + sql);
		}
	
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("drop table if exists" + TABLE);
			
			Log.d(TAG, "onUpgraded");
			onCreate(db);
		}
	}
	
	private final DbHelper mDbHelper;
	
	/**
	 * Constructor of EventData
	 */
	public EventData(Context context) {
		mDbHelper = new DbHelper(context);
		Log.i(TAG, "Initialized Data.");
	}
	
	public void close() {
		mDbHelper.close();
	}
	
	public void insertOrIgnore(ContentValues values) {
		Log.d(TAG, "insertOrIgnore on " + values.toString());
		
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		try {
			db.insertWithOnConflict(TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
		} finally {
			db.close();
		}
	}
	
	/**
	 * 
	 * @return Cursor, 可访问列为:created_at, name, description, time, duration, host_name, location
	 */
	public Cursor getEventUpdates() {
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		return db.query(TABLE, null, null, null, null, null, GET_ALL_ORDER_BY);
	}
	
	/**
	 * 
	 * @return Cursor, 可访问列为:created_at, name, description, time, duration, host_name, location
	 */
	public Cursor getEventUpdatesLaterThan(int id) {
		if (id != 0) {
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			String[] selectionArgs = {String.valueOf(id)};
			return db.query(TABLE, null, C_ID + " > ?", selectionArgs, null, null, GET_ALL_ORDER_BY);	
		}
		else return getEventUpdates();
	}
	
	/**
	 * 
	 * @return Date, 数据库中最后一条记录的时间戳
	 */
	public Date getLatestEventCreatedAtTime() {
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		try {
			Cursor cursor = db.query(TABLE, MAX_CREATED_AT_COLUMNS, null, null, null, null, null);
			try {
				return cursor.moveToNext() ? Event.format.parse(cursor.getString(0)) : new Date(Long.MIN_VALUE);
			} catch (ParseException e) {
				Log.e(TAG, e.getMessage());
			} finally {
				cursor.close();
			}
		} finally {
			db.close();
		}
		return new Date(Long.MIN_VALUE);
	}
	
	/**
	 * 
	 * @return int, 数据库中最后一条记录的id
	 */
	public Integer getLatestEventId() {
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		try {
			Cursor cursor = db.query(TABLE, MAX_ID_COLUMNS, null, null, null, null, null);
			try {
				return cursor.moveToNext() ? cursor.getInt(0) : 0;
			} finally {
				cursor.close();
			}
		} finally {
			db.close();
		}
	}
	
	/**
	 *
	 * @param 目标Event的id
	 * @return 目标Event的详细信息
	 */
	public Event getEventItemById(long id) {
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Map<String, String> event = new HashMap<String, String>();
		try {
			Cursor cursor = db.query(TABLE, DB_EVENT_COLUMNS, C_ID + "=" + id, null, null, null, null);
			try {
				if (cursor.moveToNext()) {
					event.put(Event.M_ID, String.valueOf(id));
					event.put(Event.M_CREATED_AT, cursor.getString(cursor.getColumnIndex(C_CREATED_AT)));
					event.put(Event.M_NAME, cursor.getString(cursor.getColumnIndex(C_NAME)));
					event.put(Event.M_DESCRIPTION, cursor.getString(cursor.getColumnIndex(C_DESCRIPTION)));
					event.put(Event.M_TIME, cursor.getString(cursor.getColumnIndex(C_TIME)));
					event.put(Event.M_DURATION, cursor.getString(cursor.getColumnIndex(C_DURATION)));
					event.put(Event.M_HOST_NAME, cursor.getString(cursor.getColumnIndex(C_HOST_NAME)));
					event.put(Event.M_LOCATION, cursor.getString(cursor.getColumnIndex(C_LOCATION)));
				}
			} finally {
				cursor.close();
			}
		} finally {
			db.close();
		}
		return new Event(event);
	}
	
}
