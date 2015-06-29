package cn.libery.swiperefreshlayout;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by SZQ on 2015/6/29.
 */
public class ViewPagerActivity extends Activity {
    private ViewPager viewPager;
    private int position;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter();
        viewPager.setAdapter(adapter);
        if (getIntent() != null) {

            position = getIntent().getIntExtra("position", 0);
            viewPager.setCurrentItem(position);
        }
    }

    public class ViewPagerAdapter extends PagerAdapter {
        private String[] imgUrls = ImgUrls.imgUrls;

        @Override
        public int getCount() {
            return imgUrls.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            SimpleDraweeView draweeView = new SimpleDraweeView(container.getContext());
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(imgUrls[position]))
                    .setProgressiveRenderingEnabled(true)
                    .build();
            DraweeController draweeController = Fresco.newDraweeControllerBuilder().
                    setAutoPlayAnimations(true).
                    setImageRequest(request).
                    build();
            draweeView.setAspectRatio(1.33f);
            draweeView.setController(draweeController);
            container.addView(draweeView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            return draweeView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
