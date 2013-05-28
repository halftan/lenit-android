package com.azusasoft.lenit.data;

import java.text.ParseException;
import java.util.Date;

import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.azusasoft.lenit.LenitApplication;
import com.azusasoft.lenit.R;

public class EventAdapter {
	private static final String TAG = EventAdapter.class.getSimpleName();
	
	public static final String[] FROM = {
		EventData.C_NAME, EventData.C_TIME, EventData.C_HOST_NAME, EventData.C_LOCATION
	};
	public static final int[] TO = {
		R.id.event_name, R.id.event_time, R.id.event_host, R.id.event_location
	};
	
	public static final ViewBinder VIEW_BINDER = new ViewBinder() {
		
		@Override
		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
			if (view.getId() != R.id.event_time)
				return false;
			
			try {
				Date time = Event.format.parse(cursor.getString(columnIndex));
				((TextView) view).setText(
						DateFormat.getDateFormat(
								LenitApplication.getInstance()).format(time));
				Log.i(TAG, DateFormat.getDateFormat(
						LenitApplication.getInstance()).format(time));
				return true;
			} catch (ParseException e) {
				Log.e(TAG, e.getMessage());
				return false;
			}
		}
	};
}
