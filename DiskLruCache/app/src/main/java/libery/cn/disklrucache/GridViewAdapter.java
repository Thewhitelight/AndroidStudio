package libery.cn.disklrucache;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by SZQ on 2015/5/16.
 */
public class GridViewAdapter extends ArrayAdapter<String> {
    private DiskLruCache diskLruCache;
    private LruCache<String, Bitmap> lruCache;
    private GridView gridView;
    private Set<LoadBitmapTask> tasks;
    private int mHeight = 0;

    public GridViewAdapter(Context context, int resource, String[] object, GridView gv) {
        super(context, resource, object);
        gridView = gv;
        tasks = new HashSet<>();
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        lruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
        try {
            File cacheDisk = getCacheFile(context, "DisLruCache");
            if (!cacheDisk.exists()) {
                cacheDisk.mkdirs();
            }
            diskLruCache = DiskLruCache.open(cacheDisk, getVersion(context), 1, 10 * 1024 * 1024);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 1);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 1;
        }
    }

    private File getCacheFile(Context context, String pathName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + pathName);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String URL = getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.gird_item, parent, false);
        } else {
            view = convertView;
        }
        final ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        if (imageView.getLayoutParams().height != mHeight) {
            imageView.getLayoutParams().height = mHeight;
        }
        imageView.setTag(URL);
        imageView.setImageResource(R.drawable.empty_photo);
        loadBitmap(imageView, URL);
        return view;
    }

    private void loadBitmap(ImageView imageView, String imageUrl) {
        try {
            Bitmap bitmap = getBitmapFromCache(imageUrl);
            if (bitmap == null) {
                LoadBitmapTask task = new LoadBitmapTask();
                tasks.add(task);
                task.execute(imageUrl);
            } else {
                if (imageView != null && bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap getBitmapFromCache(String key) {
        return lruCache.get(key);
    }

    public String hasKeyDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(key.getBytes());
            cacheKey = bytesToHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }

        return cacheKey;
    }

    public void flushCache() {
        if (diskLruCache != null) {
            try {
                diskLruCache.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                stringBuilder.append('0');
            }
            stringBuilder.append(hex);
        }
        return stringBuilder.toString();
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromCache(key) == null) {
            lruCache.put(key, bitmap);
        }
    }

    public void cancelTask() {
        if (tasks != null) {
            for (LoadBitmapTask task : tasks) {
                task.cancel(false);
            }
        }
    }

    public void setmHeight(int height) {
        if (height == mHeight) {
            return;
        }
        mHeight = height;
        notifyDataSetChanged();
    }

    public class LoadBitmapTask extends AsyncTask<String, Void, Bitmap> {
        private String imageUrl = null;

        @Override
        protected Bitmap doInBackground(String... params) {
            imageUrl = params[0];
            FileDescriptor fileDescriptor = null;
            FileInputStream fileInputStream = null;
            DiskLruCache.Snapshot snapshot = null;
            try {
                final String key = hasKeyDisk(imageUrl);
                snapshot = diskLruCache.get(key);
                if (snapshot == null) {
                    DiskLruCache.Editor editor = diskLruCache.edit(key);
                    if (editor != null) {
                        OutputStream outputStream = editor.newOutputStream(0);
                        if (downloadUrlToStream(imageUrl, outputStream)) {
                            editor.commit();
                        } else {
                            editor.abort();
                        }
                    }
                    snapshot = diskLruCache.get(key);
                }
                if (snapshot != null) {
                    fileInputStream = (FileInputStream) snapshot.getInputStream(0);
                    fileDescriptor = fileInputStream.getFD();
                }
                Bitmap bitmap = null;
                if (fileDescriptor != null) {
                    bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                }
                if (bitmap != null) {
                    addBitmapToMemoryCache(params[0], bitmap);
                }
                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                if (fileDescriptor == null && fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView imageView = (ImageView) gridView.findViewWithTag(imageUrl);
            if (imageView != null && bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
            tasks.remove(this);
        }

        private boolean downloadUrlToStream(String stringUrl, OutputStream outputStream) {
            HttpURLConnection connection = null;
            BufferedInputStream bufferedInputStream = null;
            BufferedOutputStream bufferedOutputStream = null;
            try {
                final URL url = new URL(stringUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                bufferedInputStream = new BufferedInputStream(connection.getInputStream(), 10 * 1024);
                bufferedOutputStream = new BufferedOutputStream(outputStream, 10 * 1024);
                int n;
                while ((n = bufferedInputStream.read()) != -1) {
                    bufferedOutputStream.write(n);
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) connection.disconnect();
                try {
                    if (bufferedOutputStream != null) bufferedOutputStream.close();
                    if (bufferedInputStream != null) bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
    }
}
