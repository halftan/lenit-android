package com.azusasoft.lenit;

import android.app.Activity;
import android.os.Bundle;
import android.preference.*;
import android.view.Window;

public class PrefsActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
		getFragmentManager().beginTransaction().replace(android.R.id.content,
				new PrefsFragment()).commit();
	}
	
	public static class PrefsFragment extends PreferenceFragment {
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			addPreferencesFromResource(R.xml.prefs);
		}
	}
}
