package cn.libery.okhttpdemo;

import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.util.Map;

/**
 * Created by SZQ on 2015/6/3.
 */
public class Http {
    private OkHttpClient client = new OkHttpClient();
    private static Http http = null;
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    public static Http getInstance() {
        if (http == null) {
            http = new Http();
        }
        return http;
    }

    /**
     * get传值
     *
     * @param map
     * @param url
     * @param callback
     */
    public void getHttp(Map<String, Object> map, String url, Callback callback) {
        Request request;
        if (map == null) {
            request = new Request.Builder().url(Constant.HOST + url).build();
        } else {
            String urlQuest = "?";
            for (Map.Entry entry : map.entrySet()) {
                urlQuest += entry.getKey().toString() + "=" + entry.getValue().toString() + "&";
            }
            urlQuest.substring(0, urlQuest.length() - 1);
            request = new Request.Builder().url(Constant.HOST + url + urlQuest).build();
        }
        client.newCall(request).enqueue(callback);
    }

    /**
     * post传值
     *
     * @param map
     * @param url
     * @param callback
     */
    public void postHttp(Map<String, Object> map, String url, Callback callback) {
        RequestBody requestBody;
        FormEncodingBuilder builder = new FormEncodingBuilder();
        if (map == null) {
            requestBody = builder.build();
        } else {
            for (Map.Entry entry : map.entrySet()) {
                builder.add(entry.getKey().toString(), entry.getValue().toString());
            }
            requestBody = builder.build();
        }
        Request request = new Request.Builder().url(Constant.HOST + url).post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }

    public void postHttpImage(String id, String url, String filePath, Callback callback) {
        File file = new File(filePath);
        RequestBody requestBody = new MultipartBuilder().type(MultipartBuilder.FORM)
                .addFormDataPart("title", "libery.test")
                .addFormDataPart("image", file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file))
                .build();
        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + id)
                .url(url)
                .post(requestBody)
                .build();
        Log.i("postHttpImage", request.urlString());
        client.newCall(request).enqueue(callback);
    }
}
