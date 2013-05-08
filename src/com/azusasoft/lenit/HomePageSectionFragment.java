package com.azusasoft.lenit;

import org.json.JSONException;
import org.json.JSONObject;

import com.azusasoft.lenit.error.RestApiException;
import com.azusasoft.lenit.http.WebClient;
import com.loopj.android.http.*;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HomePageSectionFragment extends Fragment
	implements OnClickListener {
	
	private TextView mContent;
	private Button mButton;

	public HomePageSectionFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);
		mContent = (TextView) rootView.findViewById(R.id.content);
		mButton = (Button) rootView.findViewById(R.id.button1);
		mButton.setOnClickListener(this);
		
		return rootView;
	}

	@Override
	public void onClick(View v) {
		WebClient.get("/events", null, new WebClient.SetTextHandler(v.getRootView().findViewById(R.id.content)));
	}
	
	
}
