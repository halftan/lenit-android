package com.azusasoft.lenit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomePageSectionFragment extends Fragment {
	
	private TextView mContent;

	public HomePageSectionFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_main_dummy, container, false);
		mContent = (TextView) rootView.findViewById(R.id.content);
		
		mContent.setText("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLlllllll");
				
		return rootView;
	}
	
	
}
