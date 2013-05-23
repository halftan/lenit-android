package com.azusasoft.lenit;

import com.azusasoft.lenit.data.EventData;
import com.azusasoft.lenit.http.TokenValidator;

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
	
	private static LenitApplication applicationInstance;
	
	private SharedPreferences prefs;
	
	public static String userName;
	public static String token;
	
	private EventData mEventData = null;
	
	/**
	 * @return The singleton instance of EventData.
	 */
	public EventData getEventData() {
		if (mEventData == null)
			mEventData = new EventData(getApplicationContext());
		return mEventData;
	}
	
	/**
	 * Get a singleton instance of LenitApplication.
	 * Throws RuntimeException if application instance not found.
	 */
	public static LenitApplication getInstance()
			throws RuntimeException {
		if (applicationInstance != null)
			return applicationInstance;
		else
			throw new RuntimeException("Couldn't find singleton Application instance.");
	}
	
	/**
	 * Used to set the username that will be displayed
	 *  on the  main page.
	 */
	public void setUserName(String userName) {
		this.prefs.edit().putString(key_username, userName).commit();
	}
	
	/**
	 * Used to set token of current user which is used
	 *  for server authentication.
	 */
	public void setToken(String token) {
		this.prefs.edit().putString(key_token, token).commit();
	}
	
	/**
	 * Set username and token at one time.
	 */
	public void setUserNameAndToken(String userName, String token) {
		this.prefs.edit().putString(key_username, userName).putString(key_token, token).commit();
	}
	
	/**
	 * Return true if token exists.
	 */
	public boolean userLoggedIn() {
		return token != null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();

		this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
		this.prefs.registerOnSharedPreferenceChangeListener(this);
		
		LenitApplication.applicationInstance = this;
		mEventData = new EventData(getApplicationContext());
		
		token = prefs.getString(key_token, null);
		userName = prefs.getString(key_username, null);
		
		Log.d(TAG, "LenitApplication-Created");
		
		TokenValidator validator = new TokenValidator(this);
		validator.startValidation(token);
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
		token = this.prefs.getString(key_token, "");
		userName = this.prefs.getString(key_username, "");
		Log.d(TAG, String.format("userName:%s\ntoken:%s", userName, token));
	}

}
