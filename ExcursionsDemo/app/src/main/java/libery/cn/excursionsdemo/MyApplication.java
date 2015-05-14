package libery.cn.excursionsdemo;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by SZQ on 2015/5/14.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(getApplicationContext());
    }

    public void initImageLoader(Context context) {
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "ExcursionDemo/Cache");
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(context).memoryCacheExtraOptions(480, 800).threadPriority(3).denyCacheImageMultipleSizesInMemory().diskCacheFileNameGenerator(new Md5FileNameGenerator()).memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)).tasksProcessingOrder(QueueProcessingType.FIFO).diskCache(new UnlimitedDiskCache(cacheDir)).imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)).memoryCacheSize(2 * 1024 * 1024).diskCacheSize(50 * 1024 * 1024).build();
        ImageLoader.getInstance().init(configuration);
    }
}
