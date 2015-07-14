package cn.libery.calender;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by Libery on 2015/7/14.
 * Email:libery.szq@qq.com
 */
public class CalendarFragment extends Fragment implements OnDateChangedListener, OnMonthChangedListener {
    private View view;
    private MaterialCalendarView calendarView;
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private OneDayDecorator oneDayDecorator = new OneDayDecorator();
    private LunarCalendar lunarCalendar;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

/*    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendarView = (MaterialCalendarView) view.findViewById(R.id.calender);
        calendarView.setShowOtherDates(true);
        calendarView.setOnDateChangedListener(this);
        Calendar calendar = Calendar.getInstance();
        calendarView.setSelectedDate(calendar.getTime());
        //
        //widget.addDecorators(new HighlightWeekendsDecorator(),
        //       oneDayDecorator);
        calendarView.addDecorators(oneDayDecorator);
        calendarView.setDateTextAppearance(R.style.TextAppearance_MaterialCalendarWidget_Date);
        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
        return view;
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calendar, container, false);
        calendarView = (MaterialCalendarView) view.findViewById(R.id.calender);
        calendarView.setShowOtherDates(true);
        calendarView.setOnDateChangedListener(this);
        Calendar calendar = Calendar.getInstance();
        calendarView.setSelectedDate(calendar.getTime());
        //
        //widget.addDecorators(new HighlightWeekendsDecorator(),
        //       oneDayDecorator);
        calendarView.addDecorators(oneDayDecorator);
        calendarView.setDateTextAppearance(R.style.TextAppearance_MaterialCalendarWidget_Date);
        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
        return view;
    }

    @Override
    public void onDateChanged(@NonNull MaterialCalendarView widget, @Nullable CalendarDay date) {
        oneDayDecorator.setDate(date.getDate());
        lunarCalendar = new LunarCalendar();
        widget.invalidateDecorators();
        if (date == null) {
            Toast.makeText(getActivity(), "«Î—°‘Ò»’∆⁄", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), lunarCalendar.getLunarDate(date.getYear(), Integer.valueOf(date.getMonth() + 1), date.getDay(), false), Toast.LENGTH_SHORT).show();
        }
        widget.invalidate();
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        calendarView.removeView(widget);
        Toast.makeText(getActivity(), date.toString(), Toast.LENGTH_SHORT).show();
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
            calendar.add(Calendar.MONTH, 0);
            calendar.add(Calendar.DATE, 1);
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

            if (getActivity().isFinishing()) {
                return;
            }
            calendarView.addDecorator(new EventDecorator(Color.RED, calendarDays));
        }
    }
}
