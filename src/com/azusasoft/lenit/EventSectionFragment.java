package com.azusasoft.lenit;

import java.text.ParseException;
import java.util.Date;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.util.Log;
import android.view.View;
import android.widget.SearchView.OnCloseListener;

import com.azusasoft.lenit.data.Event;
import com.azusasoft.lenit.data.EventAdapter;

public class EventSectionFragment extends ListFragment
	implements OnCloseListener, LoaderManager.LoaderCallbacks<Cursor> {
	private static String TAG = "EventSectionFragment";
	
	SimpleCursorAdapter mAdapter;
	
//	public static int MY_EVENT_LOADER = 0;
//	public static int ATTENDED_EVENT_LOADER = 1;
	
//	private int mLoaderId;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		setEmptyText(getString(R.string.event_fragment_empty_text));
		setHasOptionsMenu(true);
		
		mAdapter = new SimpleCursorAdapter(getActivity(), R.layout.event_row, null, EventAdapter.FROM, EventAdapter.TO, 0);
		setListAdapter(mAdapter);
		
		setListShown(false);
		getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
//		return new CursorLoader(getActivity(), new Uri(EventData.TABLE),
//				EventData.DB_EVENT_COLUMNS,	null, null,
//				EventData.C_CREATED_AT + " COLLATE LOCALIZED DESC");
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		mAdapter.setViewBinder(EventAdapter.VIEW_BINDER);
	}

	@Override
	public boolean onClose() {
		return true;
	}

}
