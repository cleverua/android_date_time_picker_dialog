package com.cleverua.android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.DatePicker;
import android.widget.ScrollView;
import android.widget.TimePicker;

import com.cleverua.android.R;
import com.cleverua.android.DateHelper;

public class DateTimePickerDialog extends AlertDialog {

    private static final String TAG = "DateTimePickerDialog";
    
    private static final String DEFAULT_TITLE = "Pick date & time";
    private static final String SET           = "Set";
    private static final String CANCEL        = "Cancel";
    
    private static final String STATE_DATETIME_KEY = "DateTimePickerDialog.datetime";
    
    private DatePicker datePicker;
    private TimePicker timePicker;
    
    public DateTimePickerDialog(Context context, DateTimeAcceptor datetimeAcceptor) {
        this(context, datetimeAcceptor, null, System.currentTimeMillis(), null);
    }

    public DateTimePickerDialog(Context context, DateTimeAcceptor datetimeAcceptor,
            String title) {
        this(context, datetimeAcceptor, title, System.currentTimeMillis(), null);
    }

    public DateTimePickerDialog(Context context, DateTimeAcceptor datetimeAcceptor,
            long datetime) {
        this(context, datetimeAcceptor, null, datetime, null);
    }
    
    public DateTimePickerDialog(Context context, DateTimeAcceptor datetimeAcceptor,
            String title, long datetime) {
        this(context, datetimeAcceptor, title, datetime, null);
    }

    public DateTimePickerDialog(Context context, final DateTimeAcceptor datetimeAcceptor,
            String title, long datetime, final Runnable canceller) {
        super(context);
        
        final LayoutInflater factory = LayoutInflater.from(context);
        
        final ScrollView pickersContainer = 
            (ScrollView) factory.inflate(R.layout.date_time_picker, null);
        
        datePicker = (DatePicker) pickersContainer.findViewById(R.id.date);
        timePicker = (TimePicker) pickersContainer.findViewById(R.id.time);
        
        resetDatetime(datetime);
        
        setIcon(R.drawable.ic_dialog_time);
        setTitle(title == null ? DEFAULT_TITLE : title);
        
        setView(pickersContainer);
        
        setButton(BUTTON_POSITIVE, SET, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                log("positiveBtnListener: entered");
                datetimeAcceptor.accept(getDateTime());
            }
        });
        
        setButton(BUTTON_NEGATIVE, CANCEL, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                log("negativeBtnListener: entered");
                if (canceller != null) canceller.run();
            }
        });
        
        setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                log("cancelListener: entered");
                if (canceller != null) canceller.run();
            }
        });
        
        setCancelable(true);
    }
    
    @Override
    public Bundle onSaveInstanceState() {
        Bundle outState = super.onSaveInstanceState();
        outState.putLong(STATE_DATETIME_KEY, getDateTime());
        return outState;
    }
    
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        resetDatetime(savedInstanceState.getLong(STATE_DATETIME_KEY));
    }
    
    public void resetDatetime(long datetime) {
        DateHelper.initDatePicker(datePicker, datetime);
        DateHelper.initTimePicker(timePicker, datetime);
    }
    
    private long getDateTime() {
        return DateHelper.getStartOfTheDay(DateHelper.getDate(datePicker)) 
            + DateHelper.getTime(timePicker);
    }
    
    private void log(String msg) {
        Log.d(TAG, msg);
    }
    
    public interface DateTimeAcceptor {
        public void accept(long datetime);
    }
}
