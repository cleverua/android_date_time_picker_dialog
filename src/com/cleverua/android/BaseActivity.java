package com.cleverua.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class BaseActivity extends Activity {

	private String tag;
    protected boolean isCleanStart; // whether this is a clean start or a restore
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		logInfo("onCreate");
        isCleanStart = (savedInstanceState == null);
        logInfo("onCreate: isCleanStart = " + isCleanStart);
	}
	
    @Override
    protected void onStart() {
    	super.onStart();
    	logInfo("onStart");
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	logInfo("onResume");
    }
    
    @Override
    protected void onRestart() {
    	super.onRestart();
    	logInfo("onRestart");
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	logInfo("onPause");
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	logInfo("onStop");
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	logInfo("onDestroy");
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	super.onRestoreInstanceState(savedInstanceState);
    	logInfo("onRestoreInstanceState");
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	logInfo("onSaveInstanceState");
    }
    
	protected String getTag() {
		if (tag == null) {
			tag = this.getString(R.string.log_tag_prefix) + ' ' + this.getClass().getSimpleName();
		}
		return tag;
	}
	
    protected void log(String msg) {
        Log.d(getTag(), msg);
    }

    protected void logInfo(String msg) {
        Log.i(getTag(), msg);
    }
    
    protected void log(String msg, Throwable tr) {
        Log.e(getTag(), msg, tr);
    }
}
