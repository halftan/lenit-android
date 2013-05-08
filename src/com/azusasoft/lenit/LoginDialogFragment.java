package com.azusasoft.lenit;

import com.azusasoft.lenit.error.RestApiException;
import com.azusasoft.lenit.http.WebClient;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

public class LoginDialogFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		// Inflate and set layout for the dialog
		builder.setView(inflater.inflate(R.layout.dialog_login, null))
			.setPositiveButton(R.string.action_login, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Dialog dlg = LoginDialogFragment.this.getDialog();
					String email = ((EditText) dlg.findViewById(R.id.email)).getText().toString();
					String password= ((EditText) dlg.findViewById(R.id.password)).getText().toString();
					WebClient.UserLogin(email, password, getActivity());
				}
			})
			.setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					LoginDialogFragment.this.getDialog().cancel();
				}
			});
		
		return builder.create();
	}

}
