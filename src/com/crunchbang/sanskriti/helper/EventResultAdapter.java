package com.crunchbang.sanskriti.helper;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.crunchbang.sanskriti.R;

public class EventResultAdapter extends ArrayAdapter<EventResult> {

	private List<EventResult> itemList;
	private Context context;

	public EventResultAdapter(List<EventResult> itemList, Context ctx) {
		super(ctx, android.R.layout.simple_list_item_1, itemList);
		this.itemList = itemList;
		context = ctx;
	}


	public int getCount() {
		if (itemList != null)
			return itemList.size();
		return 0;
	}

	@Override
	public EventResult getItem(int position) {
		if (itemList != null)
			return itemList.get(position);
		return null;
	}

	@Override
	public long getItemId(int position) {
		if (itemList != null)
			return itemList.get(position).hashCode();
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.event_result_row, null);
		}

		EventResult result = itemList.get(position);
		TextView tvName = (TextView) v.findViewById(R.id.tv_name);
		tvName.setText(result.getEvent());
		TextView tvFirst = (TextView) v.findViewById(R.id.tv_first);
		tvFirst.setText(result.getFirst());
		TextView tvSecond = (TextView) v.findViewById(R.id.tv_second);
		tvSecond.setText(result.getSecond());
		TextView tvThird = (TextView) v.findViewById(R.id.tv_third);
		tvThird.setText(result.getThird());

		return v;
	}

	public List<EventResult> getItemList() {
		return itemList;
	}

	public void setItemList(List<EventResult> itemList) {
		this.itemList = itemList;
	}

}
