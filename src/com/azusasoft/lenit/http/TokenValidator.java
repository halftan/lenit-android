package com.azusasoft.lenit.http;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.azusasoft.lenit.LenitApplication;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class TokenValidator extends JsonHttpResponseHandler {
	public static final String TAG = "WebService";

	private LenitApplication mApp;
	
	public TokenValidator(LenitApplication app) {
		super();
		mApp = app;
	}
	
	public void startValidation(String token) {
		RequestParams params = new RequestParams(WebClient.JSON_AUTH_TOKEN, token);
		WebClient.get(WebClient.apiSessionValidate, params, this);
	}
	
	@Override
	public void onFailure(Throwable e, JSONObject obj) {
		mApp.setToken(null);
	}

	@Override
	public void onSuccess(JSONObject obj) {
		try {
			mApp.setUserNameAndToken(obj.getString(WebClient.JSON_USERNAME), obj.getString(WebClient.JSON_AUTH_TOKEN));
		} catch (JSONException e) {
			Log.e(TAG, "TokenValidator: " + e.getMessage());
		}
	}

}
