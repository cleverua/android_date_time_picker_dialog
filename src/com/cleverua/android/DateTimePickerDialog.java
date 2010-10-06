package com.cleverua.android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TimePicker;

public class DateTimePickerDialog extends AlertDialog {

    private static final String TAG = "DateTimePickerDialog";
    
    private static final String DEFAULT_TITLE = "Pick date & time";
    private static final String SET           = "Set";
    private static final String CANCEL        = "Cancel";
    
    private static final String STATE_DATETIME_KEY = "DateTimePickerDialog.datetime";
    
    private static final String TIME_FORMAT_24 = "24";
    
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
        
        final FrameLayout pickersContainer = 
            (FrameLayout) factory.inflate(R.layout.date_time_picker, null);
        
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
        // honor user settings as to time format
        final String timeFormat = 
            Settings.System.getString(getContext().getContentResolver(), Settings.System.TIME_12_24);
        timePicker.setIs24HourView(TIME_FORMAT_24.equals(timeFormat));
        
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
