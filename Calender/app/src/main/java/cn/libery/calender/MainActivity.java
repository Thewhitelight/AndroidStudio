package cn.libery.calender;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
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
import widget.LunarCalendar;
import widget.MaterialCalendarView;
import widget.OnDateChangedListener;
import widget.OnMonthChangedListener;
import widget.OneDayDecorator;


public class MainActivity extends AppCompatActivity implements OnDateChangedListener, OnMonthChangedListener {
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private TextView tv;
    private OneDayDecorator oneDayDecorator = new OneDayDecorator();
    private MaterialCalendarView calendarView;
    private CalendarFragment fragment;
    private FragmentManager manager;
    private ActionBar ab;
    private Integer month;
    private int year;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // fragment = new CalendarFragment();
        //manager = getSupportFragmentManager();
        //manager.beginTransaction().add(R.id.content, fragment, "CalendarFragment").commit();
        tv = (TextView) findViewById(R.id.tv);
        calendarView = (MaterialCalendarView) findViewById(R.id.calender);
        calendarView.setShowOtherDates(true);
        calendarView.setOnDateChangedListener(this);
        calendarView.setOnMonthChangedListener(this);
        Calendar calendar = Calendar.getInstance();
        calendarView.setSelectedDate(calendar.getTime());
        ab = getSupportActionBar();
        //
        //widget.addDecorators(new HighlightWeekendsDecorator(),
        //       oneDayDecorator);
        CalendarDay day = new CalendarDay();
        ab.setTitle(Integer.valueOf(day.getMonth() + 1) + "/" + day.getYear());
        calendarView.addDecorators(oneDayDecorator);
        calendarView.setDateTextAppearance(R.style.TextAppearance_MaterialCalendarWidget_Date);
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
            startActivity(new Intent(getApplicationContext(), NewActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    LunarCalendar lunarCalendar;

    @Override
    public void onDateChanged(@NonNull MaterialCalendarView widget, @Nullable CalendarDay date) {
        oneDayDecorator.setDate(date.getDate());
        lunarCalendar = new LunarCalendar();
        widget.invalidateDecorators();
        if (date == null) {
            tv.setText(null);
        } else {
            tv.setText(FORMATTER.format(date.getDate()));
            tv.append("\n" + date.getYear());
            tv.append("\n" + Integer.valueOf(date.getMonth() + 1));
            tv.append("\n" + date.getDay());
            tv.append("\n" + lunarCalendar.getLunarDate(date.getYear(), Integer.valueOf(date.getMonth() + 1), date.getDay(), false));
        }
        calendarView.invalidate();
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        Toast.makeText(this, FORMATTER.format(date.getDate()), Toast.LENGTH_SHORT).show();
        ab.setTitle(Integer.valueOf(date.getMonth() + 1) + "/" + date.getYear());
        calendarView.invalidate();

    }

    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        @Override
        protected List<CalendarDay> doInBackground(Void... voids) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 0);
            // calendar.add(Calendar.DATE, 1);
            ArrayList<CalendarDay> dates = new ArrayList<>();
            // for (int i = 0; i < 30; i++) {
            CalendarDay day = CalendarDay.from(calendar);
            dates.add(day);
            // calendar.add(Calendar.DATE, 7);
            // }

            return dates;
        }

        @Override
        protected void onPostExecute(List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (isFinishing()) {
                return;
            }
            calendarView.addDecorator(new EventDecorator(Color.RED, calendarDays));
            tv.append(calendarDays.toString());
        }
    }
}
