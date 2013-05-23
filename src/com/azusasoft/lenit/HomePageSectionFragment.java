package com.azusasoft.lenit;

import java.util.List;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

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

import com.azusasoft.lenit.data.Event;
import com.azusasoft.lenit.http.WebClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

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
		RequestParams params = new RequestParams("auth_token", LenitApplication.token);
		Vector<Event> eventList = new Vector<Event>();
		WebClient.get("/my_events", params, new GetEventHandler(eventList));
	}
	
	public static class GetEventHandler extends JsonHttpResponseHandler {
		private List<Event> mList;
		
		public GetEventHandler(List<Event> list) {
			super();
			mList = list;
		}
		
		@Override
		public void onSuccess(JSONArray result) {
			for (int i = 0; i < result.length(); ++i) {
				mList.add(new Event(result.optJSONObject(i)));
			}
		}

		@Override
		public void onFailure(Throwable e, JSONObject result) {
			Toast.makeText(LenitApplication.getInstance(), R.string.error_unauthorized, Toast.LENGTH_SHORT).show();
			Log.e("WebService", "Error fetching event info\n" + e.getMessage());
		}
		
		
	}

	
}
