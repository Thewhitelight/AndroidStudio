package cn.libery.frescodemo;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by SZQ on 2015/5/21.
 */
public class MyAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private String[] imgUrls;

    public MyAdapter(Context context, String[] imgUrls) {
        layoutInflater = layoutInflater.from(context);
        this.imgUrls = imgUrls;
    }

    @Override
    public int getCount() {
        return imgUrls.length;
    }

    @Override
    public Object getItem(int position) {
        return imgUrls[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.lv_item, parent, false);
        }
        SimpleDraweeView draweeView = (SimpleDraweeView) convertView.findViewById(R.id.image);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(imgUrls[position]))
                .setProgressiveRenderingEnabled(true)
                .build();
        DraweeController draweeController = Fresco.newDraweeControllerBuilder().
                setAutoPlayAnimations(true).
                setImageRequest(request).
                build();
        draweeView.setAspectRatio(1.33f);
        draweeView.setController(draweeController);
        //draweeView.setImageURI(Uri.parse(imgUrls[position]));//加载非GIF图片
        return convertView;
    }
}
