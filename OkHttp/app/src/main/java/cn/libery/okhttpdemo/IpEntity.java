package cn.libery.okhttpdemo;

/**
 * Created by SZQ on 2015/6/3.
 */
public class IpEntity {

    /**
     * code:0
     * area : 华北
     * country : 中国
     * isp_id : 100017
     * city : 北京市
     * ip : 210.75.225.254
     * isp : 电信
     * county :
     * region_id : 110000
     * area_id : 100000
     * county_id : -1
     * region : 北京市
     * country_id : 86
     * city_id : 110000
     */
    private String area;
    private String country;
    private String isp_id;
    private String city;
    private String ip;
    private String isp;
    private String county;
    private String region_id;
    private String area_id;
    private String county_id;
    private String region;
    private String country_id;
    private String city_id;
    private int code;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setIsp_id(String isp_id) {
        this.isp_id = isp_id;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public void setCounty_id(String county_id) {
        this.county_id = county_id;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getArea() {
        return area;
    }

    public String getCountry() {
        return country;
    }

    public String getIsp_id() {
        return isp_id;
    }

    public String getCity() {
        return city;
    }

    public String getIp() {
        return ip;
    }

    public String getIsp() {
        return isp;
    }

    public String getCounty() {
        return county;
    }

    public String getRegion_id() {
        return region_id;
    }

    public String getArea_id() {
        return area_id;
    }

    public String getCounty_id() {
        return county_id;
    }

    public String getRegion() {
        return region;
    }

    public String getCountry_id() {
        return country_id;
    }

    public String getCity_id() {
        return city_id;
    }

    @Override
    public String toString() {
        return country + "\n" + area + "\n" + region + "\n" + city + "\n" + county + "\n" + isp;
    }
}
