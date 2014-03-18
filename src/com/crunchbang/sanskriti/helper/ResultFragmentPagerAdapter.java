package com.crunchbang.sanskriti.helper;

import com.crunchbang.sanskriti.ResultFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ResultFragmentPagerAdapter extends FragmentPagerAdapter {
	final int pageCount = 2;

	public ResultFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		String type = null;
		switch (index) {
		case 0:
			type = "intra";
			break;
		case 1:
			type = "inter";
			break;
		}
		ResultFragment frag = new ResultFragment();
		Bundle bundle = new Bundle();
		bundle.putString("type", type);
		frag.setArguments(bundle);
		return frag;
	}

	@Override
	public int getCount() {
		return 2;
	}

}
