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
import com.azusasoft.lenit.R;
import com.azusasoft.lenit.error.RestApiException;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class WebClient {
	public static final String TAG = "WebService";
	
	private static final String BASE_URL = "http://192.168.244.128:3000";
	private static String userAgent = "Lenit-Application-Client";
	public static String apiRoot = "http://192.168.244.128:3000/api";
	public static String apiSession = "/sessions";

	// private static String formatUserLogin =
	// "\"email\":\"%s\",\"password\":\"%s\"";

	private static AsyncHttpClient mClient = new AsyncHttpClient();

	public WebClient() {
	}

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
		params.put("email", email);
		params.put("password", password);
		WebClient.post("/sessions", params, new WebClient.SessionHandler(activity));
	}

	public static class SessionHandler extends JsonHttpResponseHandler {
		private FragmentActivity mActivity;
		
		public SessionHandler(FragmentActivity context) {
			super();
			mActivity = context;
		}
		
		@Override
		public void onSuccess(JSONObject result) {
			String token = null;
			try {
				token = result.getString("auth_token");
				Log.i(TAG, token);
			} catch (JSONException e) {
				Log.e("WebService", e.getMessage());
				Toast.makeText(mActivity, R.string.error_login_failed, Toast.LENGTH_SHORT).show();
				return;
			}
			LenitApplication.getInstance().setToken(token);
		}

		@Override
		public void onFailure(Throwable e, JSONObject result) {
			if (e.getMessage().endsWith("Unauthorized")) {
				Toast.makeText(mActivity, R.string.error_unauthorized, Toast.LENGTH_SHORT).show();
				DialogFragment dlg = new LoginDialogFragment();
				dlg.show(mActivity.getSupportFragmentManager(), "Login_Dialog");
			}
		}
		
		
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
			Log.e(TAG, "Error fetching event info");
		}
		
		
	}
}
