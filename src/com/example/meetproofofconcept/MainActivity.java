package com.example.meetproofofconcept;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;

public class MainActivity extends FragmentActivity {
	private MainFragment mainFragment;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    if (savedInstanceState == null) {
	        // Add the fragment on initial activity setup
	        mainFragment = new MainFragment();
	        getSupportFragmentManager()
	        .beginTransaction()
	        .add(android.R.id.content, mainFragment)
	        .commit();
	    } else {
	        // Or set the fragment from restored state info
	        mainFragment = (MainFragment) getSupportFragmentManager()
	        .findFragmentById(android.R.id.content);
	    }
	}
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//
//		// start Facebook Login
//		Session.openActiveSession(this, true, new Session.StatusCallback() {
//
//			// callback when session changes state
//			@Override
//			public void call(Session session, SessionState state,
//					Exception exception) {
//				if (session.isOpened()) {
//					//fill_name(session);
//					getNewsFeed(session);
//				}
//			}
//		});
//	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);
	}
	

//	public void fill_name(Session session) {
//		// make request to the /me API
//		Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
//
//			// callback after Graph API response with user
//			// object
//			@Override
//			public void onCompleted(GraphUser user, Response response) {
//				if (user != null) {
//					do_on_completed(user, response);
//				}
//			}
//		});
//	}
	
//	public void do_on_completed(GraphUser user, Response response) {
//		TextView welcome = (TextView) findViewById(R.id.welcome);
//		welcome.setText("Hello " + user.getName() + "!");
//	    Intent intent = new Intent(this, ListFeedActivity.class);
//	    startActivity(intent);
//	}
//    public void getNewsFeed(Session session) {
//    	Request request = new Request(session, 
//    		    "me/home", 
//    		    new Bundle(), 
//    		    HttpMethod.GET, 
//    		    new Request.Callback(){ 
//    		        public void onCompleted(Response response) {
//    		        	TextView welcome = (TextView) findViewById(R.id.welcome);
//    		    		welcome.setText(response.toString());
//    		    }
//    		});
//    	Request.executeBatchAsync(request);
//    }
    
    
}

