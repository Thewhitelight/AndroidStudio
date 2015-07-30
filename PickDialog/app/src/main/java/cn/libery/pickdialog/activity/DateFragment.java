package cn.libery.pickdialog.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.libery.pickdialog.Model.DayModel;
import cn.libery.pickdialog.Model.MonthModel;
import cn.libery.pickdialog.Model.YearModel;
import cn.libery.pickdialog.R;
import cn.libery.pickdialog.widget.OnWheelChangedListener;
import cn.libery.pickdialog.widget.WheelView;
import cn.libery.pickdialog.widget.adapters.ArrayWheelAdapter;

/**
 * Created by Libery on 2015/7/29.
 * Email:libery.szq@qq.com
 */
public class DateFragment extends android.support.v4.app.DialogFragment implements View.OnClickListener, OnWheelChangedListener {


    protected String[] mYear;

    protected Map<String, String[]> mMonthMap = new HashMap<>();

    protected Map<String, String[]> mDayMap = new HashMap<>();

    protected String mCurrentYear;

    protected String mCurrentMonth;

    protected String mCurrentDay = "";

    protected void initProvinceDatas() {
        List<YearModel> yearList;

        List<DayModel> listDis = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            DayModel district = new DayModel();
            district.setName(i + "");
            listDis.add(district);
        }
        List<DayModel> listDis2 = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            DayModel district = new DayModel();
            district.setName(i + "");
            listDis2.add(district);
        }
        List<DayModel> listDis3 = new ArrayList<>();
        for (int i = 1; i <= 28; i++) {
            DayModel district = new DayModel();
            district.setName(i + "");
            listDis3.add(district);
        }
        List<DayModel> listDis4 = new ArrayList<>();
        for (int i = 1; i <= 29; i++) {
            DayModel district = new DayModel();
            district.setName(i + "");
            listDis4.add(district);
        }

        yearList = new ArrayList<>();
        for (int j = 1972; j <= 2050; j++) {
            YearModel province = new YearModel();
            province.setName(j + "");
            Log.i("year", j + "");
            if (j % 4 == 0.0) {
                Log.i("run year", "run year");
                List<MonthModel> listCity = new ArrayList<>();
                for (int i = 1; i <= 12; i++) {
                    MonthModel city = new MonthModel();
                    city.setName(i + "");
                    if (i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10 || i == 12) {
                        city.setDistrictList(listDis2);
                    }
                    if (i == 2) {
                        Log.i("29", "29");
                        city.setDistrictList(listDis4);
                    }
                    if (i == 4 || i == 6 || i == 9 || i == 11) {
                        city.setDistrictList(listDis);
                    }
                    listCity.add(city);
                }
                province.setCityList(listCity);
            } else {
                Log.i("not run year", "not run year");
                List<MonthModel> listCity2 = new ArrayList<>();
                for (int i = 1; i <= 12; i++) {
                    MonthModel city2 = new MonthModel();
                    city2.setName(i + "");
                    if (i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10 || i == 12) {
                        city2.setDistrictList(listDis2);
                    }
                    if (i == 2) {
                        Log.i("28", "28");
                        city2.setDistrictList(listDis3);
                    }
                    if (i == 4 || i == 6 || i == 9 || i == 11) {
                        city2.setDistrictList(listDis);
                    }
                    listCity2.add(city2);
                }
                province.setCityList(listCity2);
            }

            yearList.add(province);
        }
        //Log.i("1972.2.29", yearList.get(2).getCityList().get(1).getDistrictList().get(28).getName());
        if (yearList != null && !yearList.isEmpty()) {
            mCurrentYear = yearList.get(0).getName();
            List<MonthModel> cityList = yearList.get(0).getCityList();
            if (cityList != null && !cityList.isEmpty()) {
                mCurrentMonth = cityList.get(0).getName();
                List<DayModel> districtList = cityList.get(0).getDistrictList();
                mCurrentDay = districtList.get(0).getName();
            }
        }

        mYear = new String[yearList.size()];
        for (int i = 0; i < yearList.size(); i++) {

            mYear[i] = yearList.get(i).getName();

            List<MonthModel> monthList = yearList.get(i).getCityList();
            String[] monthNames = new String[monthList.size()];
            for (int j = 0; j < monthList.size(); j++) {
                monthNames[j] = monthList.get(j).getName();
                List<DayModel> dayList = monthList.get(j).getDistrictList();
                String[] dayNameArray = new String[dayList.size()];
                DayModel[] dayArray = new DayModel[dayList.size()];
                for (int k = 0; k < dayList.size(); k++) {
                    DayModel dayModel = new DayModel(dayList.get(k).getName());
                    dayArray[k] = dayModel;
                    dayNameArray[k] = dayModel.getName();
                    Log.i("dayNameArray", dayNameArray[k] + "");
                }
                mDayMap.put(monthNames[j], dayNameArray);
            }

            mMonthMap.put(yearList.get(i).getName(), monthNames);
        }

    }


    private WheelView mViewYear;
    private WheelView mViewMonth;
    private WheelView mViewDay;
    private Button mBtnConfirm;
    private TextView tv_date;

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.fragment_date, container, false);
        setUpViews();
        setUpListener();
        setUpData();
        tv_date.setText(mCurrentYear + "年" + mCurrentMonth + "月" + mCurrentDay + "日");
        return view;
    }

    private void setUpViews() {
        mViewYear = (WheelView) view.findViewById(R.id.id_year);
        mViewMonth = (WheelView) view.findViewById(R.id.id_month);
        mViewDay = (WheelView) view.findViewById(R.id.id_day);
        tv_date = (TextView) view.findViewById(R.id.tv_date);
        mBtnConfirm = (Button) view.findViewById(R.id.btn_confirm);
    }

    private void setUpListener() {

        mViewYear.addChangingListener(this);

        mViewMonth.addChangingListener(this);

        mViewDay.addChangingListener(this);

        mBtnConfirm.setOnClickListener(this);
    }

    private void setUpData() {
        initProvinceDatas();
        mViewYear.setViewAdapter(new ArrayWheelAdapter<>(getActivity(), mYear));
        mViewYear.setVisibleItems(3);
        mViewMonth.setVisibleItems(3);
        mViewDay.setVisibleItems(3);
        // mViewYear.setCurrentItem(50);
        updateMonth();
        updateDay();
    }


    private void updateDay() {
        int pCurrent = mViewMonth.getCurrentItem();
        mCurrentMonth = mMonthMap.get(mCurrentYear)[pCurrent];
        String[] mDay = mDayMap.get(mCurrentMonth);
        mCurrentDay = mDayMap.get(mCurrentMonth)[0];
        //mCurrentDistrictName=mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        if (mDay == null) {
            mDay = new String[]{""};
        }
        for (int x = 0; x < mDay.length; x++) {
            Log.i("updateDay", mDay[x]);
        }

        mViewDay.setViewAdapter(new ArrayWheelAdapter<>(getActivity(), mDay));
        mViewDay.setCurrentItem(0);
    }


    private void updateMonth() {
        int pCurrent = mViewYear.getCurrentItem();
        mCurrentYear = mYear[pCurrent];
        String[] mMonth = mMonthMap.get(mCurrentYear);
        if (mMonth == null) {
            mMonth = new String[]{""};
        }
        mViewMonth.setViewAdapter(new ArrayWheelAdapter<>(getActivity(), mMonth));
        mViewMonth.setCurrentItem(0);
        updateDay();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mViewYear) {
            updateMonth();
        } else if (wheel == mViewMonth) {
            updateDay();
        } else if (wheel == mViewDay) {
            mCurrentDay = mDayMap.get(mCurrentMonth)[newValue];
        }
        tv_date.setText(mCurrentYear + "年" + mCurrentMonth + "月" + mCurrentDay + "日");
    }
}
