package by.bstu.fit.savelev.datecalc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    TextView currentDateTime;
    TextView currentDateTime2;
    TextView activeDateTime;
    TextView answer;
    Calendar dateAndTime= Calendar.getInstance();
    boolean isFirstInit = true;
    public MainActivity() {
        super(R.layout.activity_main);
    }
    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        setContentView(R.layout.activity_main);
        currentDateTime = findViewById(R.id.currentDateTime);
        currentDateTime2 = findViewById(R.id.currentDateTime2);
        answer = findViewById(R.id.answer);
        activeDateTime = currentDateTime;
        setInitialDateTime();
    }

    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        Button button = (Button) v;
        switch(v.getId()){
            case R.id.dateButton:
                activeDateTime = currentDateTime;
                break;
            case R.id.dateButton2:
                activeDateTime = currentDateTime2;
                break;
        }
        new DatePickerDialog(MainActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // отображаем диалоговое окно для выбора времени
    public void setTime(View v) {
        switch(v.getId()){
            case R.id.timeButton:Button:
                activeDateTime = currentDateTime;
                break;
            case R.id.timeButton2:
                activeDateTime = currentDateTime2;
                break;
        }

        new TimePickerDialog(MainActivity.this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }

    // установка начальных даты и времени
    private void setInitialDateTime() {
        activeDateTime.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));

        if(isFirstInit){
            currentDateTime.setText(activeDateTime.getText());
            currentDateTime2.setText(activeDateTime.getText());
            isFirstInit = false;
        }
    }

    // установка обработчика выбора времени
    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        }
    };

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

    public void calculateTheResult(View view) {
            Calendar firstDate = ParseDate(currentDateTime);
            Calendar secondDate = ParseDate(currentDateTime2);
        long seconds = Math.abs(Duration.between(firstDate.toInstant(), secondDate.toInstant()).getSeconds());
        int day = (int) TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
        answer.setText(day + " days " + hours + " hours " + minute + " minutes.");
    }

    private Calendar ParseDate(TextView textView) {
        Calendar cal = Calendar.getInstance();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd yyyy hh:mm a", Locale.ENGLISH);
            cal.setTime(sdf.parse(textView.getText().toString().replace(",", "")));// all done
        }
        catch(ParseException ex){
            Log.d("Status", ex.getMessage());
        }
        return cal;
    }
}