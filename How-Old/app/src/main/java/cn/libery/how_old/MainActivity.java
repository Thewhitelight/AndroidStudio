package cn.libery.how_old;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facepp.error.FaceppParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private Button btn_ok, btn_photo, btn_camera;
    private ImageView imageView;
    private final static int PHOTO = 0;
    private final static int CAMERA = 1;
    private String mCurrentPhotoStr;
    private Bitmap mBitmap;
    private Paint paint;
    private FrameLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        paint = new Paint();
        init();
    }

    private void init() {
        btn_camera = (Button) findViewById(R.id.btn_camera);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_photo = (Button) findViewById(R.id.btn_photo);
        imageView = (ImageView) findViewById(R.id.imageView);
        frame = (FrameLayout) findViewById(R.id.frame);
        btn_photo.setOnClickListener(this);
        btn_camera.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
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
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_camera:

                Intent intent_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent_camera, CAMERA);
                break;
            case R.id.btn_photo:

                Intent intent_photo = new Intent(Intent.ACTION_PICK);
                intent_photo.setType("image/*");
                startActivityForResult(intent_photo, PHOTO);
                break;
            case R.id.btn_ok:
                if (imageView.getDrawable() == null) {
                    Toast.makeText(getApplicationContext(), "Place select picture", Toast.LENGTH_SHORT).show();
                } else {
                    frame.setVisibility(View.VISIBLE);
                    Detect.detect(mBitmap, new Detect.CallBack() {
                        @Override
                        public void success(JSONObject jsonObject) {
                            Log.i("***************", jsonObject.toString());
                            Message msg = Message.obtain();
                            msg.what = SUCCESS;
                            msg.obj = jsonObject;
                            handler.sendMessage(msg);
                        }

                        @Override
                        public void error(FaceppParseException exception) {
                            Message msg = Message.obtain();
                            msg.what = ERROR;
                            msg.obj = exception.getErrorMessage();
                            handler.sendMessage(msg);
                        }
                    });
                }

                break;
        }
    }

    private final static int SUCCESS = 3;
    private final static int ERROR = 4;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    frame.setVisibility(View.GONE);
                    JSONObject js = (JSONObject) msg.obj;
                    prepareBitmap(js);
                    imageView.setImageBitmap(mBitmap);
                    break;
                case ERROR:
                    frame.setVisibility(View.GONE);
                    String error = (String) msg.obj;
                    if (TextUtils.isEmpty(error)) {
                        Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void prepareBitmap(JSONObject js) {
        Bitmap bitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), mBitmap.getConfig());
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        try {
            JSONArray faces = js.getJSONArray("face");
            int faceCount = faces.length();
            for (int i = 0; i < faceCount; i++) {
                JSONObject face = faces.getJSONObject(i);
                JSONObject position = face.getJSONObject("position");
                float x = (float) position.getJSONObject("center").getDouble("x");
                float y = (float) position.getJSONObject("center").getDouble("y");
                float width = (float) position.getDouble("width");
                float height = (float) position.getDouble("height");
                int age = face.getJSONObject("attribute").getJSONObject("age").getInt("value");
                String gender = face.getJSONObject("attribute").getJSONObject("gender").getString("value");
                x = x / 100 * bitmap.getWidth();
                y = y / 100 * bitmap.getHeight();
                width = width / 100 * bitmap.getWidth();
                height = height / 100 * bitmap.getHeight();
                paint.setColor(0xFFFFFFFF);
                paint.setStrokeWidth(2);
                canvas.drawLine(x - width / 2, y - height / 2, x - width / 2, y + height / 2, paint);
                canvas.drawLine(x - width / 2, y - height / 2, x + width / 2, y - height / 2, paint);
                canvas.drawLine(x + width / 2, y - height / 2, x + width / 2, y + height / 2, paint);
                canvas.drawLine(x - width / 2, y + height / 2, x + width / 2, y + height / 2, paint);
                Bitmap ageBitmap = buildAgeBitmap(age, "Male".equals(gender));
                int ageWith = ageBitmap.getWidth();
                int ageHeight = ageBitmap.getHeight();
                if (bitmap.getWidth() < imageView.getWidth() && bitmap.getHeight() < imageView.getHeight()) {
                    float ratio = Math.max(bitmap.getWidth() * 1.0f / imageView.getWidth(),
                            bitmap.getHeight() * 1.0f / imageView.getHeight());
                    ageBitmap = Bitmap.createScaledBitmap(ageBitmap, (int) (ageWith * ratio), (int) (ageHeight * ratio), false);
                }
                canvas.drawBitmap(ageBitmap, x - ageBitmap.getWidth() / 2, y - ageBitmap.getHeight() - height / 2, null);
                mBitmap = bitmap;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Bitmap buildAgeBitmap(int age, boolean ismale) {
        TextView tv = (TextView) frame.findViewById(R.id.tv_age_gender);
        tv.setText(age + " ");
        if (ismale) {
            tv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.male), null, null, null);
        } else {
            tv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.female), null, null, null);
        }
        tv.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(tv.getDrawingCache());
        tv.destroyDrawingCache();
        return bitmap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        switch (requestCode) {
            case PHOTO:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                    cursor.moveToFirst();
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    mCurrentPhotoStr = cursor.getString(index);
                    cursor.close();
                    compressPhoto();
                    imageView.setImageBitmap(mBitmap);
                }
                break;
            case CAMERA:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    mBitmap = bitmap;
                    imageView.setImageBitmap(mBitmap);
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void compressPhoto() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoStr, options);
        double ratio = Math.max(options.outWidth * 1.0d / 1024f, options.outHeight * 1.0d / 1024f);
        options.inSampleSize = (int) Math.ceil(ratio);
        options.inJustDecodeBounds = false;
        mBitmap = BitmapFactory.decodeFile(mCurrentPhotoStr, options);
    }
}
