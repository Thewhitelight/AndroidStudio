package cn.libery.pickdialog.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import cn.libery.pickdialog.R;
import cn.libery.pickdialog.widget.OnWheelChangedListener;
import cn.libery.pickdialog.widget.WheelView;
import cn.libery.pickdialog.widget.adapters.ArrayWheelAdapter;

/**
 * Created by Libery on 2015/7/29.
 * Email:libery.szq@qq.com
 */
public class DialogFragment extends FragmentBase implements View.OnClickListener, OnWheelChangedListener {
    private View view;
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private Button btn_ok;
    private TextView tv_location;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.dialog, container, false);
        setUpViews();
        setUpListener();
        setUpData();
        return view;
    }

    private void setUpViews() {
        mViewProvince = (WheelView) view.findViewById(R.id.id_province);
        mViewCity = (WheelView) view.findViewById(R.id.id_city);
        tv_location = (TextView) view.findViewById(R.id.tv_location);
        btn_ok = (Button) view.findViewById(R.id.btn_ok);
    }

    private void setUpListener() {

        mViewProvince.addChangingListener(this);

        mViewCity.addChangingListener(this);

        btn_ok.setOnClickListener(this);
    }

    private void setUpData() {
        initProvinceDatas(getActivity());
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<>(getActivity(), mProvinceDatas));
        mViewProvince.setVisibleItems(3);
        mViewCity.setVisibleItems(3);
        updateCities();
        updateAreas();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_ok) {
            dismiss();
        }
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        }
    }


    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        tv_location.setText(mCurrentProviceName + " " + mCurrentCityName);
    }


    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<>(getActivity(), cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }
}
