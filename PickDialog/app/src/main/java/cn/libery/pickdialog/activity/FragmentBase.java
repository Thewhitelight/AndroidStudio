package cn.libery.pickdialog.activity;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import cn.libery.pickdialog.Model.CityModel;
import cn.libery.pickdialog.Model.ProvinceModel;
import cn.libery.pickdialog.xmlparse.XmlParserHandler;

/**
 * Created by Libery on 2015/7/29.
 * Email:libery.szq@qq.com
 */
public class FragmentBase extends android.support.v4.app.DialogFragment {


    /**
     * ����ʡ
     */
    protected String[] mProvinceDatas;
    /**
     * key - ʡ value - ��
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * ��ǰʡ������
     */
    protected String mCurrentProviceName;
    /**
     * ��ǰ�е�����
     */
    protected String mCurrentCityName;

    /**
     * ����ʡ������XML����
     */

    protected void initProvinceDatas(Context context) {
        List<ProvinceModel> provinceList = null;
        AssetManager asset = context.getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // ����һ������xml�Ĺ�������
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // ����xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // ��ȡ��������������
            provinceList = handler.getDataList();
            provinceList = new ArrayList<>();
            ProvinceModel provinceModel = new ProvinceModel();
            CityModel cityModel = new CityModel();
            provinceModel.setName("�㽭");
            cityModel.setName("����");
            List<CityModel> list = new ArrayList<>();
            list.add(cityModel);
            provinceModel.setCityList(list);
            provinceList.add(provinceModel);
            // */ ��ʼ��Ĭ��ѡ�е�ʡ����
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                }
            }
            // */
            mProvinceDatas = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                // ��������ʡ������
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    // ����ʡ����������е�����
                    cityNames[j] = cityList.get(j).getName();
                }
                // ʡ-�е����ݣ����浽mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }


}
