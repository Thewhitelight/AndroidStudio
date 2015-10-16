package cn.libery.multiphotopick;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import cn.libery.multiphotopick.photopick.ImageInfo;
import cn.libery.multiphotopick.photopick.PhotoOperate;
import cn.libery.multiphotopick.photopick.PhotoPickActivity;

public class MainActivity extends AppCompatActivity {
    public static int width;
    public static int PHOTO_MAX_COUNT = 3;
    public static final int RESULT_REQUEST_PICK_PHOTO = 1003;
    ArrayList<PhotoData> mData = new ArrayList<>();
    PhotoOperate photoOperate = new PhotoOperate(this);
    private ImageView[] imageViews;
    private ImageView image1, image2, image3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(configuration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);
        imageViews = new ImageView[]{image1, image2, image3};
        width = getResources().getDisplayMetrics().widthPixels;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PhotoPickActivity.class);
                intent.putExtra(PhotoPickActivity.EXTRA_MAX, PHOTO_MAX_COUNT);

                ArrayList<ImageInfo> pickImages = new ArrayList<>();
                for (PhotoData item : mData) {
                    pickImages.add(item.mImageinfo);
                }
                intent.putExtra(PhotoPickActivity.EXTRA_PICKED, pickImages);
                startActivityForResult(intent, RESULT_REQUEST_PICK_PHOTO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_REQUEST_PICK_PHOTO) {
            if (resultCode == RESULT_OK) {
                try {
                    @SuppressWarnings("unchecked")
                    ArrayList<ImageInfo> pickPhots = (ArrayList<ImageInfo>) data.getSerializableExtra("data");
                    mData.clear();
                    for (ImageInfo item : pickPhots) {
                        Uri uri = Uri.parse(item.path);
                        File outputFile = photoOperate.scal(uri);
                        mData.add(new MainActivity.PhotoData(outputFile, item));
                    }
                    if (mData.size() == 0) {
                        image1.setVisibility(View.GONE);
                        image2.setVisibility(View.GONE);
                        image3.setVisibility(View.GONE);
                    } else {
                        int num = 0;
                        for (int i = 0; i < mData.size(); i++) {
                            String path = ImageInfo.pathAddPreFix(mData.get(i).uri.toString());
                            Log.i(getClass().getName(), path);
                            imageViews[i].setVisibility(View.VISIBLE);
                            num++;
                            ImageLoader.getInstance().displayImage(path, imageViews[i], PhotoPickActivity.optionsImage);
                        }
                        for (int i = num; i < 3; i++) {
                            imageViews[i].setVisibility(View.GONE);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public static class PhotoData {
        ImageInfo mImageinfo;
        Uri uri = Uri.parse("");
        String serviceUri = "";

        public PhotoData(File file, ImageInfo info) {
            uri = Uri.fromFile(file);
            mImageinfo = info;
        }

        public PhotoData(PhotoDataSerializable data) {
            uri = Uri.parse(data.uriString);
            serviceUri = data.serviceUri;
            mImageinfo = data.mImageInfo;
        }
    }

    public static class PhotoDataSerializable implements Serializable {
        String uriString = "";
        String serviceUri = "";
        ImageInfo mImageInfo;

        public PhotoDataSerializable(PhotoData data) {
            uriString = data.uri.toString();
            serviceUri = data.serviceUri;
            mImageInfo = data.mImageinfo;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
