package cn.libery.how_old;

import android.graphics.Bitmap;
import android.util.Log;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by SZQ on 2015/5/17.
 */
public class Detect {
    public interface CallBack {
        void success(JSONObject jsonObject);

        void error(FaceppParseException exception);
    }

    public static void detect(final Bitmap bitmap, final CallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    HttpRequests requests = new HttpRequests(Constant.KEY, Constant.SECRET, true, true);
                    Bitmap bmSmall = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight());
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmSmall.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] bytes = stream.toByteArray();
                    PostParameters postParameters = new PostParameters();
                    postParameters.setImg(bytes);
                    Log.i("PostParameters",postParameters.toString());
                    JSONObject jsonObject = requests.detectionDetect(postParameters);
                    if (callBack != null) {
                        callBack.success(jsonObject);
                    }
                } catch (FaceppParseException e) {
                    if (callBack != null) {
                        callBack.error(e);
                    }
                }
            }
        }).start();
    }
}

