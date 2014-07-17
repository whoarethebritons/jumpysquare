package com.example.flappy;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.opengl.*;
//import android.opengl.GLES20;
//import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.*;
import android.view.GestureDetector.OnGestureListener;
import android.widget.TextView;
import android.os.Build;

public class BlActivity extends ActionBarActivity {
	int mProgram;
	private GLSurfaceView mGLView;
	private final String vertexShaderCode =
		    "attribute vec4 vPosition;" +
		    "void main() {" +
		    "  gl_Position = vPosition;" +
		    "}";

		private final String fragmentShaderCode =
		    "precision mediump float;" +
		    "uniform vec4 vColor;" +
		    "void main() {" +
		    "  gl_FragColor = vColor;" +
		    "}";

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Intent intent = getIntent();
        super.onCreate(savedInstanceState);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);
    }
    @Override
    protected void onPause() {
        super.onPause();
        // The following call pauses the rendering thread.
        // If your OpenGL application is memory intensive,
        // you should consider de-allocating objects that
        // consume significant memory here.
        mGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The following call resumes a paused rendering thread.
        // If you de-allocated graphic objects for onPause()
        // this is a good place to re-allocate them.
        mGLView.onResume();
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
	public class MyGLSurfaceView extends GLSurfaceView {

	    private final MyGLRenderer mRenderer;

	    public MyGLSurfaceView(Context context) {
	        super(context);

	        // Create an OpenGL ES 2.0 context.
	        setEGLContextClientVersion(2);

	        // Set the Renderer for drawing on the GLSurfaceView
	        mRenderer = new MyGLRenderer();
	        setRenderer(mRenderer);

	        // Render the view only when there is a change in the drawing data
	        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	    }

	    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
	    private float mPreviousX;
	    private float mPreviousY;

	    @Override
	    public boolean onTouchEvent(MotionEvent e) {
	        // MotionEvent reports input details from the touch screen
	        // and other input controls. In this case, you are only
	        // interested in events where the touch position changed.
	    	float dx,dy;
	        float x = e.getX();
	        float y = e.getY();
	        float incr = 0.5f;
	        float inCoords[] = {
	                 0.0f,  0.15f, 0.0f,   // top left
	                 0.0f,  0.15f, 0.0f,   // bottom left
	                 0.0f,  0.15f, 0.0f,   // bottom right
	                 0.0f,  0.15f, 0.0f }; // top right
	        switch (e.getAction()) {
	        	//case OnGestureListener.onSingleTapUp(e)
	        	case MotionEvent.ACTION_UP:
	        		mRenderer.jump(incr);
	                requestRender();
	            case MotionEvent.ACTION_MOVE:
	            	//mPreviousX += 0.5f;
	            	//mPreviousY += 0.5f;
	            	
	            	
	                dx = x - mPreviousX;
	                dy = y - mPreviousY;
	                
	                

	                
	                // reverse direction of rotation above the mid-line
	                if (y > getHeight() / 2) {
	                    dx = dx * -1 ;
	                }

	                // reverse direction of rotation to left of the mid-line
	                if (x < getWidth() / 2) {
	                    dy = dy * -1 ;
	                }

	                mRenderer.setAngle(
	                        mRenderer.getAngle() +
	                        ((dx + dy) * TOUCH_SCALE_FACTOR));  // = 180.0f / 320
	                
	                requestRender();
	        }

	        mPreviousX = x;
	        mPreviousY = y;
	        return true;
	    }

	}
	public static class MyGLRenderer implements GLSurfaceView.Renderer {

	    private static final String TAG = "MyGLRenderer";
	    //private Triangle mTriangle;
	    private Square   mSquare;

	    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
	    private final float[] mMVPMatrix = new float[16];
	    private final float[] mProjectionMatrix = new float[16];
	    private final float[] mViewMatrix = new float[16];
	    private final float[] mJumpMatrix = new float[16];
	    float [] result;
	    private float mAngle;

	    @Override
	    public void onSurfaceCreated(GL10 unused, EGLConfig config) {

	        // Set the background frame color
	        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

	        //mTriangle = new Triangle();
	        mSquare   = new Square();
	    }

	    @Override
	    public void onDrawFrame(GL10 gl) {
	        float[] scratch = new float[16];

	        // Draw background color
	        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

	        // Set the camera position (View matrix)
	        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

	        // Calculate the projection and view transformation
	        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

	        // Draw square
	        mSquare.draw(mMVPMatrix);
	        
	        // Create a rotation for the triangle

	        // Use the following code to generate constant rotation.
	        // Leave this code out when using TouchEvents.
	        //long time = SystemClock.uptimeMillis() % 4000L;
	        // float angle = 0.090f * ((int) time);
			
	        Matrix.translateM(mJumpMatrix, 0, 0, 0.5f, 0);
	        //Matrix.setRotateM(mJumpMatrix, 0, mAngle, 0, 0, 1.0f);
			
	        // Combine the rotation matrix with the projection and camera view
	        // Note that the mMVPMatrix factor *must be first* in order
	        // for the matrix multiplication product to be correct.
	        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mJumpMatrix, 0);

	        // Draw triangle
	        mSquare.draw(scratch);
	         
	         
	        
	    }

	    @Override
	    public void onSurfaceChanged(GL10 unused, int width, int height) {
	        // Adjust the viewport based on geometry changes,
	        // such as screen rotation
	        GLES20.glViewport(0, 0, width, height);

	        float ratio = (float) width / height;

	        // this projection matrix is applied to object coordinates
	        // in the onDrawFrame() method
	        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);

	    }

	    /**
	     * Utility method for compiling a OpenGL shader.
	     *
	     * <p><strong>Note:</strong> When developing shaders, use the checkGlError()
	     * method to debug shader coding errors.</p>
	     *
	     * @param type - Vertex or fragment shader type.
	     * @param shaderCode - String containing the shader code.
	     * @return - Returns an id for the shader.
	     */
	    public static int loadShader(int type, String shaderCode){

	        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
	        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
	        int shader = GLES20.glCreateShader(type);

	        // add the source code to the shader and compile it
	        GLES20.glShaderSource(shader, shaderCode);
	        GLES20.glCompileShader(shader);

	        return shader;
	    }

	    /**
	    * Utility method for debugging OpenGL calls. Provide the name of the call
	    * just after making it:
	    *
	    * <pre>
	    * mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
	    * MyGLRenderer.checkGlError("glGetUniformLocation");</pre>
	    *
	    * If the operation is not successful, the check throws an error.
	    *
	    * @param glOperation - Name of the OpenGL call to check.
	    */
	    public static void checkGlError(String glOperation) {
	        int error;
	        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
	            Log.e(TAG, glOperation + ": glError " + error);
	            throw new RuntimeException(glOperation + ": glError " + error);
	        }
	    }

	    /**
	     * Returns the rotation angle of the triangle shape (mTriangle).
	     *
	     * @return - A float representing the rotation angle.
	     */
	    public float getAngle() {
	        return mAngle;
	    }

	    /**
	     * Sets the rotation angle of the triangle shape (mTriangle).
	     */
	    public void setAngle(float angle) {
	        mAngle = angle;
	    }
	    public void jump(float height) {
	    	Matrix.translateM(mJumpMatrix, 0, 0, height, 0);
	    }

	}


}

