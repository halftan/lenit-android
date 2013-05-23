package com.azusasoft.lenit.http;

import org.json.JSONException;
import org.json.JSONObject;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.azusasoft.lenit.LenitApplication;
import com.azusasoft.lenit.R;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SessionHandler extends JsonHttpResponseHandler {
	public static final String TAG = "WebService";
	
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
		Toast.makeText(mActivity, R.string.info_login_success, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onFailure(Throwable e, JSONObject result) {
		if (e.getMessage().endsWith("Unauthorized")) {
			Toast.makeText(mActivity, R.string.error_authorization, Toast.LENGTH_SHORT).show();
//				DialogFragment dlg = new LoginDialogFragment();
//				dlg.show(mActivity.getSupportFragmentManager(), "Login_Dialog");
		}
	}
	
	
}