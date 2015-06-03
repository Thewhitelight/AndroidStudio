package cn.libery.okhttpdemo;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;


public class UploadActivity extends ActionBarActivity implements View.OnClickListener {
    private ImageView image;
    private TextView tv;
    private Button btn;
    private String filePath;
    private static final int PHOTO_CODE = 1;
    private static final int CAMAEA_CODE = 2;
    private boolean imgIsClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        initView();
    }

    private void initView() {
        image = (ImageView) findViewById(R.id.image_upload);
        tv = (TextView) findViewById(R.id.tv_path);
        btn = (Button) findViewById(R.id.btn_upload);
        btn.setOnClickListener(this);
        image.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_upload, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.image_upload:
                imgIsClick = true;
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PHOTO_CODE);
                break;
            case R.id.btn_upload:
                if (imgIsClick) {
                    Http http = new Http().getInstance();
                    http.postHttpImage("9sdafggfef135dexf", Constant.IMAGE_HOST, filePath, new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            //Toast.makeText(getApplicationContext(), "SUCCESSFUL", Toast.LENGTH_SHORT).show();
                            Log.i("onResponse", response.toString());
                        }
                    });
                } else Toast.makeText(getApplicationContext(), "请选择图片", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = this.getContentResolver().query(uri, projection,
                            null, null, null);
                    int column_index = cursor
                            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    filePath = cursor.getString(column_index);
                    tv.setText(filePath);
                    image.setImageURI(uri);

                }
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
