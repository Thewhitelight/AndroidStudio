package cn.libery.subsamplingdemo;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import uk.co.senab.photoview.PhotoViewAttacher;


public class PhotoViewActivity extends ActionBarActivity {
    private ImageView imageView;
    private PhotoViewAttacher attacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        imageView = (ImageView) findViewById(R.id.photoView);
        Drawable drawable = getResources().getDrawable(R.drawable.banner);
        imageView.setImageDrawable(drawable);
        attacher = new PhotoViewAttacher(imageView);
        attacher.update();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo_view, menu);
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
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
