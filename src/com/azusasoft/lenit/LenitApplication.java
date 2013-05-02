package com.azusasoft.lenit;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.Log;

public class LenitApplication extends Application implements
	OnSharedPreferenceChangeListener {

	private static final String TAG = "LenitApplication";
	private static final String key_username = "prefs_api_username";
	private static final String key_token = "prefs_api_token";
	
	private SharedPreferences prefs;
	
	public String userName;
	public String token;
	
	/**
	 * Used to set the username that will be displayed
	 *  on the  main page.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
		this.prefs.edit().putString(key_username, userName).commit();
	}
	
	/**
	 * Used to set token of current user which is used
	 *  for server authentication.
	 */
	public void setToken(String token) {
		this.token = token;
		this.prefs.edit().putString(key_token, token).commit();
	}
	
	/**
	 * Set username and token at one time.
	 */
	public void setUserNameAndToken(String userName, String token) {
		this.userName = userName;
		this.token = token;
		this.prefs.edit().putString(key_username, userName).putString(key_token, token).commit();
	}
	
	@Override
	public void onCreate() {
		super.onCreate();

		this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
		this.prefs.registerOnSharedPreferenceChangeListener(this);
		Log.d(TAG, "LenitApplication-Created");
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
		
		Log.d(TAG, "Terminated");
	}
	
	@Override
	public synchronized void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		Log.d(TAG, "PrefsChanged");
		// TODO fetch user info from server.
		Log.d(TAG, String.format("userName:%s\ntoken:%s", userName, token));
	}

}
