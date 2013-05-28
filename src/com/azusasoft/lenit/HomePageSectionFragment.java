package com.azusasoft.lenit;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.azusasoft.lenit.data.EventAdapter;
import com.azusasoft.lenit.data.RefreshListener;
import com.azusasoft.lenit.http.FetchService;
import com.azusasoft.lenit.http.WebClient;
import com.loopj.android.http.RequestParams;

public class HomePageSectionFragment extends ListFragment
	implements OnClickListener, RefreshListener {
	
	private Button mButton;
	
	SimpleCursorAdapter mAdapter;	
	Cursor cursor;

	public HomePageSectionFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);
		mButton = (Button) rootView.findViewById(R.id.button_test);
		mButton.setOnClickListener(this);
		
		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		
		cursor = LenitApplication.getInstance().getEventData().getEventUpdates();
		
		mAdapter = new SimpleCursorAdapter(getActivity(), R.layout.event_row, cursor,
				EventAdapter.FROM, EventAdapter.TO, 0);
		mAdapter.setViewBinder(EventAdapter.VIEW_BINDER);
		this.setListAdapter(mAdapter);
	}

	@Override
	public void onStop() {
		super.onStop();
		cursor.close();
	}

	@Override
	public void onClick(View v) {
		RequestParams params = new RequestParams("auth_token", LenitApplication.token);
		WebClient.get("/my_events", params, new FetchService.FetchHandler(this));
//		getActivity().startService(new Intent(getActivity(), FetchService.class));
	}

	@Override
	public void Refresh() {
		cursor.close();
		cursor = LenitApplication.getInstance().getEventData().getEventUpdates();
		mAdapter = new SimpleCursorAdapter(getActivity(), R.layout.event_row, cursor,
				EventAdapter.FROM, EventAdapter.TO, 0);
		mAdapter.setViewBinder(EventAdapter.VIEW_BINDER);
		this.setListAdapter(mAdapter);
	}

	
}
