package com.azusasoft.lenit.http;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.azusasoft.lenit.LenitApplication;
import com.azusasoft.lenit.LoginDialogFragment;
import com.azusasoft.lenit.error.RestApiException;
import com.azusasoft.lenit.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class WebClient {
	public static final String TAG = "WebService";
	
	private static final String BASE_URL = "http://192.168.1.100:3000";
	private static String userAgent = "Lenit-Application-Client";
	public static String apiRoot = BASE_URL + "/api";
	public static String apiSession = "/sessions";
	public static String apiSessionValidate = "/sessions/validate";
	
	public static String JSON_AUTH_TOKEN = "auth_token";
	public static String JSON_USERNAME = "username";
	public static String JSON_EMAIL = "email";
	public static String JSON_PASSWD = "password";

	// private static String formatUserLogin =
	// "\"email\":\"%s\",\"password\":\"%s\"";

	private static AsyncHttpClient mClient = new AsyncHttpClient();

	public WebClient() {}

	public static void get(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		mClient.get(getAbsoluteUrl(url), params, responseHandler);
	}

	public static void post(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		mClient.post(getAbsoluteUrl(url), params, responseHandler);
	}

	private static String getAbsoluteUrl(String relativeUrl) {
		return apiRoot + relativeUrl;
	}
	
	public static void UserLogin(String email, String password, FragmentActivity activity) {
		RequestParams params = new RequestParams();
		params.put(JSON_EMAIL, email);
		params.put(JSON_PASSWD, password);
		WebClient.post(apiSession, params, new SessionHandler(activity));
	}
	
	
	
	
	public static class SetTextHandler extends JsonHttpResponseHandler {
		private TextView mTarget;
		
		public SetTextHandler(View v) {
			super();
			mTarget = (TextView) v;
		}
		
		@Override
		public void onSuccess(JSONArray result) {
			String str = null;
			str = result.toString();
			mTarget.setText(str);
		}

		@Override
		public void onFailure(Throwable e, JSONObject result) {
			Toast.makeText(mTarget.getContext(), R.string.error_unauthorized, Toast.LENGTH_SHORT).show();
			Log.e(TAG, "Error fetching event info\n" + e.getMessage());
		}
		
		
	}
}
