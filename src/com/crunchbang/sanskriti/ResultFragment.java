package com.crunchbang.sanskriti;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.crunchbang.sanskriti.helper.EventResult;
import com.crunchbang.sanskriti.helper.EventResultAdapter;

public class ResultFragment extends Fragment {
	private String url = "http://sanskriti14.com/eldhose/event.php?event=";
	private String type = "inter";
	private EventResultAdapter adapter;

	ProgressBar progressSpinner;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.result_layout, null);
		type = getArguments().getString("type");
		progressSpinner = (ProgressBar) view
				.findViewById(R.id.progress_spinner);

		makeRequest();
		ListView lv = (ListView) view.findViewById(R.id.lv_result);
		lv.setAdapter(adapter);

		return view;

	}

	private void makeRequest() {

		adapter = new EventResultAdapter(new ArrayList<EventResult>(),
				getActivity());
		RequestQueue req = Volley.newRequestQueue(getActivity());
		JsonArrayRequest jReq = new JsonArrayRequest(url + type,
				new Response.Listener<JSONArray>() {
					public void onResponse(JSONArray response) {
						List<EventResult> result = new ArrayList<EventResult>();
						int length = response.length();
						if (length == 0)
							Toast.makeText(getActivity(),
									"Results are unavailabe at the moment",
									Toast.LENGTH_SHORT).show();
						else {
							for (int i = 0; i < length; ++i) {
								try {
									result.add(convertEventResult(response
											.getJSONObject(i)));
								} catch (JSONException e) {

									e.printStackTrace();
								}
							}
							adapter.setItemList(result);
							adapter.notifyDataSetChanged();
						}
						progressSpinner.setVisibility(View.GONE);
					};
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(getActivity(), "Network Error!",
								Toast.LENGTH_SHORT).show();
						progressSpinner.setVisibility(View.GONE);

					}
				});
		req.add(jReq);

	}

	private final EventResult convertEventResult(JSONObject obj)
			throws JSONException {
		String event = obj.getString("event");
		String first = obj.getString("first");
		String second = obj.getString("second");
		String third = obj.getString("third");

		return new EventResult(event, first, second, third);
	}
}
