package com.example.meetproofofconcept;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;

public class ListFeedActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_feed);
		getNewsFeed(GlobalVars.session);

	}

	public void getNewsFeed(Session session) {
		Log.i("ListFeedActivity", "Making request");
		Bundle params = new Bundle();
		params.putString("limit", "0");
		Request request = new Request(session, "me/home", params,
				HttpMethod.GET, new Request.Callback() {
					public void onCompleted(Response response) {
						Log.i("ListFeedActivity", "finised request");
						Log.i("ListFeedActivity", response.toString());
						ArrayList<String> values = buildValuesFromResponse(response);
						buildList(values);
					}
				});
		request.executeAsync();
	}

	public ArrayList<String> buildValuesFromResponse(Response response) {
		JSONObject responseJson = response.getGraphObject()
				.getInnerJSONObject();
		ArrayList<String> values = new ArrayList<String>();
		JSONArray jArray;
		try {
			jArray = responseJson.getJSONArray("data");

			for (int i = 0; i < jArray.length(); i++) {

				JSONObject j = jArray.getJSONObject(i);
				
				if (j.has("message")) {
					String text = j.getString("message");
					Pattern pattern = Pattern.compile("\\b(https?|ftp|file)://[-A-Z0-9+&@#/%?=~_|!:,.;]*[-A-Z0-9+&@#/%=~_|]");
					Matcher matcher = pattern.matcher(text);
			        String modifier;
			        if(matcher.find()){
			        	modifier = "Included:";
			        } else {
			        	modifier = "Not included:";
			        }
					values.add(modifier + text);
				}
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return values;
	}

	public void buildList(ArrayList<String> values) {
		final ListView listview = (ListView) findViewById(R.id.listview);
		final ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < values.size(); ++i) {
			list.add(values.get(i));
		}
		final StableArrayAdapter adapter = new StableArrayAdapter(this,
				android.R.layout.simple_list_item_1, list);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				final String item = (String) parent.getItemAtPosition(position);
				view.animate().setDuration(2000).alpha(0)
						.withEndAction(new Runnable() {
							@Override
							public void run() {
								list.remove(item);
								adapter.notifyDataSetChanged();
								view.setAlpha(1);
							}
						});
			}

		});

		Session.openActiveSession(this, true, new Session.StatusCallback() {

			// callback when session changes state
			@Override
			public void call(Session session, SessionState state,
					Exception exception) {
				if (session.isOpened()) {
					getNewsFeed(session);
				}
			}
		});
	}

	private class StableArrayAdapter extends ArrayAdapter<String> {

		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId,
				List<String> objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
		}

		@Override
		public long getItemId(int position) {
			String item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

	}

}