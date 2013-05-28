package com.azusasoft.lenit.http;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.azusasoft.lenit.LenitApplication;
import com.azusasoft.lenit.data.Event;
import com.azusasoft.lenit.data.EventData;
import com.azusasoft.lenit.data.RefreshListener;
import com.loopj.android.http.JsonHttpResponseHandler;

public class FetchService extends Service {
	private static String TAG = "FetchService";

	private EventData mED;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mED = LenitApplication.getInstance().getEventData();
		Log.d(TAG, "onCreated");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroyed");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		Log.d(TAG, "onStartCommanded");
		
		return START_NOT_STICKY;
	}
	
	public static class FetchHandler extends JsonHttpResponseHandler {
		private RefreshListener mHandler;
		
		public FetchHandler(RefreshListener refreshHandler) {
			super();
			mHandler = refreshHandler;
		}

		@Override
		public void onSuccess(JSONArray arr) {
			EventData eD = LenitApplication.getInstance().getEventData();
			for (int i = 0; i < arr.length(); ++i) {
				try {
					JSONObject row = arr.getJSONObject(i);
					eD.insertOrIgnore(Event.toContentValues(row));
				} catch (JSONException e) {
					Log.e(FetchService.TAG, e.getMessage());
				}
			}
			mHandler.Refresh();
		}

	}
	

}
