package cn.libery.pickdialog.xmlparse;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

import cn.libery.pickdialog.Model.CityModel;
import cn.libery.pickdialog.Model.ProvinceModel;


public class XmlParserHandler extends DefaultHandler {

    /**
     * �洢���еĽ�������
     */
    private List<ProvinceModel> provinceList = new ArrayList<ProvinceModel>();

    public XmlParserHandler() {

    }

    public List<ProvinceModel> getDataList() {
        return provinceList;
    }

    @Override
    public void startDocument() throws SAXException {
        // ��������һ����ʼ��ǩ��ʱ�򣬻ᴥ���������
    }

    ProvinceModel provinceModel = new ProvinceModel();
    CityModel cityModel = new CityModel();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // ��������ʼ��ǵ�ʱ�򣬵����������
        if (qName.equals("province")) {
            provinceModel = new ProvinceModel();
            provinceModel.setName(attributes.getValue(0));
            provinceModel.setCityList(new ArrayList<CityModel>());
        } else if (qName.equals("city")) {
            cityModel = new CityModel();
            cityModel.setName(attributes.getValue(0));

        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        // ����������ǵ�ʱ�򣬻�����������
        if (qName.equals("city")) {
            provinceModel.getCityList().add(cityModel);
        } else if (qName.equals("province")) {
            provinceList.add(provinceModel);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
    }

}
