package cn.libery.calender;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

import widget.CalendarDay;
import widget.EventDecorator;
import widget.MaterialCalendarView;
import widget.OnDateChangedListener;
import widget.OnMonthChangedListener;
import widget.OneDayDecorator;


public class MainActivity extends AppCompatActivity implements OnDateChangedListener, OnMonthChangedListener {
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private TextView tv;
    private OneDayDecorator oneDayDecorator = new OneDayDecorator();
    private MaterialCalendarView widget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        widget = (MaterialCalendarView) findViewById(R.id.calender);
        widget.setShowOtherDates(true);
        widget.setOnDateChangedListener(this);
        widget.setOnMonthChangedListener(this);
        Calendar calendar = Calendar.getInstance();
        widget.setSelectedDate(calendar.getTime());
//    周六周日可高亮显示
        //widget.addDecorators(new HighlightWeekendsDecorator(),
        //       oneDayDecorator);
        widget.addDecorators(oneDayDecorator);//添加事件小红点

        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDateChanged(@NonNull MaterialCalendarView widget, @Nullable CalendarDay date) {
        oneDayDecorator.setDate(date.getDate());
        widget.invalidateDecorators();
        if (date == null) {
            tv.setText(null);
        } else {
            tv.setText(FORMATTER.format(date.getDate()));
        }
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        Toast.makeText(this, FORMATTER.format(date.getDate()), Toast.LENGTH_SHORT).show();
    }

    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        @Override
        protected List<CalendarDay> doInBackground(Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -2);
            ArrayList<CalendarDay> dates = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                CalendarDay day = CalendarDay.from(calendar);
                dates.add(day);
                calendar.add(Calendar.DATE, 5);
            }

            return dates;
        }

        @Override
        protected void onPostExecute(List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (isFinishing()) {
                return;
            }
            widget.addDecorator(new EventDecorator(Color.RED, calendarDays));
        }
    }
}
