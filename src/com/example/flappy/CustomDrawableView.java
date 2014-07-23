package com.example.flappy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.*;
import android.graphics.drawable.shapes.*;
import android.view.View;

public class CustomDrawableView extends View {
      private ShapeDrawable mDrawable;
      
      int x = 100;
      int y = 100;
      int width = 50;
      int height = 50;
      int dx,dy;
      public CustomDrawableView(Context context) {
	      super(context);
	      mDrawable = new ShapeDrawable(new RectShape());
	      mDrawable.getPaint().setColor(0xff74AC23);
	      
	      dx = (context.getWallpaperDesiredMinimumWidth()-width)/2;
	      dy = (context.getWallpaperDesiredMinimumHeight()-height)/2;
	      mDrawable.setBounds(dx, dy,dx+width,dy+height);
      }

      protected void onDraw(Canvas canvas) {
    	  mDrawable.draw(canvas);
    	  
      }
      public void setdy(int y) {dy+=y;}
      public boolean withinBounds() {
    	  
		return true;
      }
      public void setLoc() {
    	  if(withinBounds()){
    		  mDrawable.setBounds(dx, dy,dx+width,dy+height);
    	  }
    	  
      }
}