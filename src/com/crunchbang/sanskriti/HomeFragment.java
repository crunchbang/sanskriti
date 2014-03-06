package com.crunchbang.sanskriti;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		return view;

	}

	@Override
	public void onResume() {
		super.onResume();
		// getActivity().getActionBar().setTitle(R.string.home);
		((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(
				R.string.home);
	}

}
