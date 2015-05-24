package cn.libery.getphoneinfo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class LightSensorActivity extends ActionBarActivity implements SensorEventListener {
    private TextView tv_light, tv_temperature, tv_voice;
    private SensorManager sm;
    private Sensor light, temperature;
    private final static int sampleRateHZ = 44100;
    private final static int BUFFER_SIZE = AudioRecord.getMinBufferSize(sampleRateHZ,
            AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT);
    private AudioRecord mAudioRecord;
    private boolean isGetVoiceRun;
    private Object mLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ligth_sensor);
        mLock = new Object();
        tv_light = (TextView) findViewById(R.id.tv_light);
        tv_temperature = (TextView) findViewById(R.id.tv_temperature);
        tv_voice = (TextView) findViewById(R.id.tv_voice);
        sm = (SensorManager) this.getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        light = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        temperature = sm.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
        if (light == null) {
            tv_light.setText("显示失败");
        } else if (temperature == null) {
            tv_temperature.setText("显示失败");
        } else {
            sm.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);
            sm.registerListener(this, temperature, SensorManager.SENSOR_DELAY_NORMAL);
        }
        getNoiseLevel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ligth_sensor, menu);
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
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            // String mode = getLightMode(event.values[0]);
            tv_light.setText("光线:" + event.values[0] + "lux");
        }
        if (event.sensor.getType() == Sensor.TYPE_TEMPERATURE) {
            float temperature = event.values[0];
            tv_temperature.setText("温度:" + String.valueOf(temperature) + "°C");

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    public void getNoiseLevel() {
        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                sampleRateHZ, AudioFormat.CHANNEL_IN_DEFAULT,
                AudioFormat.ENCODING_PCM_16BIT, BUFFER_SIZE);
        if (isGetVoiceRun) {
            Log.e("AudioRecord", "还在录着呢");
            return;
        }

        if (mAudioRecord == null) {
            Log.e("sound", "mAudioRecord初始化失败");
        }
        isGetVoiceRun = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                mAudioRecord.startRecording();
                short[] buffer = new short[BUFFER_SIZE];
                while (isGetVoiceRun) {
                    //r是实际读取的数据长度，一般而言r会小于buffersize
                    int r = mAudioRecord.read(buffer, 0, BUFFER_SIZE);
                    long v = 0;
                    // 将 buffer 内容取出，进行平方和运算
                    for (int i = 0; i < buffer.length; i++) {
                        v += buffer[i] * buffer[i];
                    }
                    // 平方和除以数据总长度，得到音量大小。
                    double mean = v / (double) r;
                    final double volume = 10 * Math.log10(mean);
                    tv_voice.post(new Runnable() {
                        @Override
                        public void run() {
                            tv_voice.setText("分贝值:" + volume + "dB");
                        }
                    });
                    // 大概一秒十次
                    synchronized (mLock) {
                        try {
                            mLock.wait(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                mAudioRecord.stop();
                mAudioRecord.release();
                mAudioRecord = null;
            }
        }).start();
    }
}
