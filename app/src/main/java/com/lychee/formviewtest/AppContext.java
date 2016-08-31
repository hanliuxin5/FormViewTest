package com.lychee.formviewtest;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;


/**
 * Created by lychee on 2015/9/25.
 */
public class AppContext extends Application {

    public static boolean sIsAtLeastGB;
    public static Context _context;
    public static Resources _resource;

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            sIsAtLeastGB = true;
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        _context = getApplicationContext();
        _resource = _context.getResources();
        // 图片加载器初始化
        initImageLoader(this);


    }

    /**
     * 图片加载器初始化
     *
     * @param context
     */
    public static void initImageLoader(Context context) {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 1)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSizePercentage(13)
                .defaultDisplayImageOptions(defaultOptions)
//                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
    }


}
