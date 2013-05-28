package com.azusasoft.lenit.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.util.Log;

public class Event {
	static private final String TAG = "Event";
	
	public static final String M_ID = "id";
	public static final String M_CREATED_AT = "createdAt";
	public static final String M_NAME = "name";
	public static final String M_DESCRIPTION = "description";
	public static final String M_DURATION = "duration";
	public static final String M_TIME = "time";
	public static final String M_HOST_NAME = "hostName";
	public static final String M_URL = "url";
	public static final String M_LOCATION = "location";

	public int id;
	public Date createdAt;
	public String name;
	public String description;
	public String location;
	public int duration;
	public Date time;
	public String hostName;
//	public String url;
	
	public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
	
	public Event(JSONObject obj) {
		try {
			this.id = obj.optInt(M_ID);
			this.createdAt = format.parse(obj.getString(M_CREATED_AT));
			this.name = obj.getString(M_NAME);
			this.description = obj.getString(M_DESCRIPTION);
			this.duration = obj.getInt(M_DURATION);
			this.time = format.parse(obj.getString(M_TIME));
			this.hostName = obj.getString(M_HOST_NAME);
			this.location = obj.getString(M_LOCATION);
//			this.url = obj.getString((M_URL));
		} catch (JSONException e) {
			Log.e(TAG, "JSON: " + e.getMessage());
		} catch (ParseException e) {
			Log.e(TAG, e.getMessage());
		}
		Log.d(TAG, String.format("%d: %s %s", id, name, description));
	}
	
	public Event(Map<String, String> vals) {
		this(new JSONObject(vals));
	}
	
	public static ContentValues toContentValues(JSONObject from) {
		ContentValues values = new ContentValues();
		values.clear();
		values.put(EventData.C_ID, from.optInt(M_ID));
		values.put(EventData.C_CREATED_AT, from.optString(M_CREATED_AT));
		values.put(EventData.C_NAME, from.optString(M_NAME));
		values.put(EventData.C_DESCRIPTION, from.optString(M_DESCRIPTION));
		values.put(EventData.C_DURATION, from.optString(M_DURATION));
		values.put(EventData.C_TIME, from.optString(M_TIME));
		values.put(EventData.C_HOST_NAME, from.optString(M_HOST_NAME));
		values.put(EventData.C_LOCATION, from.optString(M_LOCATION));
		return values;
	}

}
