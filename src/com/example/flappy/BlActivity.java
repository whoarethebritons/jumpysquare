package com.example.flappy;



import java.util.Timer;
import java.util.TimerTask;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.*;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BlActivity extends ActionBarActivity {
	CustomDrawableView mcdv;
	
	private RefreshHandler mRedrawHandler = new RefreshHandler();

	  class RefreshHandler extends Handler {
		  @Override
	    public void handleMessage(Message msg) {
	      BlActivity.this.updateUI();
	    }

	    public void sleep(long delayMillis) {
	      this.removeMessages(0);
	      sendMessageDelayed(obtainMessage(0), delayMillis);
	    }
	  };
	  
	  private void updateUI(){
		mRedrawHandler.sleep(1000);
		mcdv.setdy(5);
  		mcdv.setLoc();
  		setContentView(mcdv);
  		
	    
	   }
	  
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        
		mcdv = new CustomDrawableView(this);
		
	    updateUI();
	    /*
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		*/
    }
   
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bl, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_bl, container,
					false);
			return rootView;
		}
	}
	
	public boolean onTouchEvent(MotionEvent e) {	        
        switch (e.getAction()) {
        	case MotionEvent.ACTION_UP:		
        		mcdv.setdy(-10);
        		mcdv.setLoc();
        		setContentView(mcdv);
        }
        return true;
    }
	
}

