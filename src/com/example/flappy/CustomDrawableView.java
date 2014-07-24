package com.example.flappy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.*;
import android.graphics.drawable.shapes.*;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class CustomDrawableView extends View {
      private Drawable mDrawable;
      private Context cont;
      boolean in;
      int width = 50;
      int height = 50;
      int dx,dy;
      @SuppressLint("NewApi")
      public CustomDrawableView(final Context context) {
    	    this(context, null, 0);
      }

	  public CustomDrawableView(final Context context, final AttributeSet attrs) {
	    this(context, attrs, 0);
	  }

	  @SuppressLint("NewApi")
	public CustomDrawableView(final Context context, final AttributeSet attrs,
	      final int defStyle) {
	      super(context);
	      cont = context;
	      /*
	      mDrawable = new ShapeDrawable(new RectShape());
	      mDrawable.getPaint().setColor(0xff74AC23);
	      */
	      Resources res = getResources();
	      mDrawable = res. getDrawable(R.drawable.rect);
	      //setBackground();
	      /*
	      dx = (context.getWallpaperDesiredMinimumWidth()-width)/2;
	      dy = (context.getWallpaperDesiredMinimumHeight()-height)/2;	
	      */      
      }

      protected void onDraw(Canvas canvas) {
    	  mDrawable.draw(canvas);
    	  
      }
      public boolean getin() {return in;}
      public void setdy(int y) {dy+=y;}
      public boolean withinBounds() {
    		return true;
    	
      }
      public void setLoc() {
    	  if(withinBounds()){
    		  mDrawable.setBounds(dx, dy,dx+width,dy+height);
    	  }
    	  else{
    		  in = false;
    	  }
    	  
      }
}