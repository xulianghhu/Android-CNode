package org.cnodejs.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.cnodejs.CNodeApplication;
import org.cnodejs.R;

import butterknife.InjectView;
import butterknife.Views;

public class AccountFragment extends Fragment implements View.OnClickListener {

	@InjectView(R.id.scan_button)
	protected Button mScanButton;

	@InjectView(R.id.scan_result)
	protected TextView mScanResult;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_account, container, false);
		Views.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mScanButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.scan_button:
				scanFromFragment();
				break;
		}
	}

	public void scanFromFragment() {
		IntentIntegrator.forSupportFragment(this).initiateScan();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
		if (result != null) {
			if (result.getContents() != null) {
				((CNodeApplication) getActivity().getApplication()).setAccessToken(result.getContents());
				mScanResult.setText(result.getContents());
			}
		}
	}
}
