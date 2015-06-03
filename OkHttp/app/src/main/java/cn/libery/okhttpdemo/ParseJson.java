package cn.libery.okhttpdemo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by SZQ on 2015/6/3.
 */
public class ParseJson {

    public static String ParseJson(String json) {

        IpEntity entity = new IpEntity();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject data = jsonObject.getJSONObject("data");
            entity.setCountry(data.getString("country"));
            entity.setArea(data.getString("area"));
            entity.setRegion(data.getString("region"));
            entity.setCity(data.getString("city"));
            entity.setCounty(data.getString("county"));
            entity.setIsp(data.getString("isp"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return entity.toString();
    }
}
