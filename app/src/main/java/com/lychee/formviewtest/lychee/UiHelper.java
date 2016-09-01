package com.lychee.formviewtest.lychee;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.lychee.formviewtest.AppContext;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;


/**
 * Created by lychee on 2015/12/1.
 */
public class UiHelper {


    /**
     * 保证在UI线程中显示Toast
     */
    private static Toast mToast = null;
    private static Handler mHandler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {
            if (mToast != null) {
                mToast.cancel();
            }
            String text = (String) msg.obj;
            mToast = Toast.makeText(AppContext._context, text, Toast.LENGTH_SHORT);
            mToast.show();
        }
    };

    public static void toast(String text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    public static void canclToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    private static void showToast(String text, int duration) {
        mHandler.sendMessage(mHandler.obtainMessage(0, 0, duration, text));
    }


    public static String IMAGE_URL = "http://www.vvsai.com/images/";

    public static String getImageUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        if (url.startsWith("http:")) {
            return url;
        } else if (url.startsWith("/") && url.length() > 1) {
            return IMAGE_URL + url.substring(1);
        }
        return IMAGE_URL + url;
    }

    /**
     * 手动设置本地矩形图片为圆角
     *
     * @return
     */
    public static DisplayImageOptions r360Options() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // 设置正在加载图片
//                .showImageOnLoading(R.drawable.) //1.8.7新增
//                .showImageForEmptyUri(R.drawable.ic_pit)
                // 设置加载失败图片
//                .showImageOnFail(R.drawable.ic_pit)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(new RoundedBitmapDisplayer(360)) // 设置图片角度,0为方形，360为圆角
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// 拉伸图片
                .build();
        return options;
    }
}
