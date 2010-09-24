package com.cleverua.android;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends BaseActivity implements OnClickListener {

    private static final int DIALOG_PICK_DATETIME_ID = 0;
    
    private static final String STATE_DATE_STARTED = "MainActivity.datetime";
    
    private long datetime;
    private TextView datetimeTextView;
    private Button pickDatetimeButton;
    
    // Hack - makes any subsequent calls to show the same DateTimePickerDialog
    // to reset dialog's datetime to the current shown in datetimeTextView.
    // Otherwise on the second call to show DateTimePickerDialog for the same
    // Activity instance the dialog will not be reinitialized with the current
    // datetime seen in the datetimeTextView.
    private boolean resetDateTimeDialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main_activity);
        
        datetimeTextView   = (TextView) findViewById(R.id.datetime);
        pickDatetimeButton = (Button)   findViewById(R.id.pick_datetime_button);
        
        pickDatetimeButton.setOnClickListener(this);
        
        if (isCleanStart) {
            datetime = System.currentTimeMillis();
        } else {
            datetime = savedInstanceState.getLong(STATE_DATE_STARTED);
        }
        
        datetimeTextView.setText(DateHelper.format(datetime));
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_PICK_DATETIME_ID) {
            return new DateTimePickerDialog(
                MainActivity.this, 
                new DateTimePickerDialog.DateTimeAcceptor() {
                    public void accept(long datetime) {
                        final String formattedDatetime = DateHelper.format(datetime);
                        log("acceptDatetime: got " + formattedDatetime);
                        MainActivity.this.datetime = datetime;
                        datetimeTextView.setText(formattedDatetime);
                    }
                },
                datetime
            );
        }
        
        return super.onCreateDialog(id);
    }
    
    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        if (id == DIALOG_PICK_DATETIME_ID) {
            if (resetDateTimeDialog) {
                resetDateTimeDialog = false;
                ((DateTimePickerDialog) dialog).resetDatetime(datetime);
            }
        }
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(STATE_DATE_STARTED, datetime);
    }
    
    @Override
    public void onClick(View v) {
        if (v == pickDatetimeButton) {
            
            // hack, see details near the field declaration (at the top of the class)
            resetDateTimeDialog = true;
            
            showDialog(DIALOG_PICK_DATETIME_ID);
        }
    }
}
