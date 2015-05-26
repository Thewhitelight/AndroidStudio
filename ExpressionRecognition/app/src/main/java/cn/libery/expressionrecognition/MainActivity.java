package cn.libery.expressionrecognition;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.isnc.facesdk.SuperID;
import com.isnc.facesdk.common.SDKConfig;


public class MainActivity extends ActionBarActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.tv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_facedata(v);//调用一登系统界面
                //startActivity(new Intent(MainActivity.this, GetFaceInfoActivity.class));//调用应用扫描界面
            }
        });
    }

    // 有刷脸界面获取表情接口返回请查看onActivityResult，数据处理请查看Aty_AppGetFaceEmotion.class
    public void btn_facedata(View v) {
        SuperID.getFaceFeatures(this);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // 接口返回
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            // 网络连接失败
            case SDKConfig.NETWORKFAIL:
                break;
            // 有界面刷脸获取人脸表情成功
            case SDKConfig.GETEMOTIONRESULT:
                Intent intent = new Intent(this, GetFaceActivity.class);
                intent.putExtra("facedata", data.getStringExtra(SDKConfig.FACEDATA));
                startActivity(intent);
                break;
            // 有界面刷脸获取人脸表情失败
            case SDKConfig.GETEMOTION_FAIL:
                Toast.makeText(getApplicationContext(), "获取人脸信息失败", Toast.LENGTH_SHORT).show();
                break;
            // 取消授权
            case SDKConfig.AUTH_BACK:
                break;
            // 一登SDK版本过低
            case SDKConfig.SDKVERSIONEXPIRED:
                break;
            default:
                break;
        }

    }

}
