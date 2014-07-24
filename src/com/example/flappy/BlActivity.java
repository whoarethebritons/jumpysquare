package com.example.flappy;


import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.*;
import android.widget.EditText;
import android.os.Build;

public class BlActivity extends ActionBarActivity {
	Drawable mDrawable; 
	int width = 50;
    int height = 50;
    private Context cont;
	int dx = (cont.getWallpaperDesiredMinimumWidth()-width)/2;
    int dy = (cont.getWallpaperDesiredMinimumHeight()-height)/2;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Resources res = getResources();
		mDrawable = res.getDrawable(R.drawable.rect);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bl);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	
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
		setLoc();
		/*
		if(! mcdv.getin()) {
			endGame(mcdv);
		}
		*/
		setContentView(R.layout.activity_bl);
		
	    
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
	public void setLoc() {
		Rect rect = mDrawable.copyBounds();
		rect.offset(0, -10);
  	  	mDrawable.setBounds(rect);
    }
	public boolean onTouchEvent(MotionEvent e) {	        
        switch (e.getAction()) {
        	case MotionEvent.ACTION_UP:
        		setLoc();
        		setContentView(R.layout.activity_bl);
        }
        return true;
    }
}
