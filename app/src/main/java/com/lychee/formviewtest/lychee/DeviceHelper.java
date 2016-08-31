package com.lychee.formviewtest.lychee;

import android.content.ClipboardManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


/**
 * Created by lychee on 2016/5/5.
 */
public class DeviceHelper {

    private static ConnectivityManager mCnnManager;
    private static InputMethodManager mSoftInputManager;

    private static ConnectivityManager getCnnManager(Context context) {
        if (mCnnManager == null)
            mCnnManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return mCnnManager;
    }

    private static InputMethodManager getSoftInputManager(Context context) {
        if (mSoftInputManager == null)
            mSoftInputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return mSoftInputManager;
    }

    /**
     * 检测是否有网络
     *
     * @return
     */
    public static boolean hasInternet(Context context) {
        return getCnnManager(context).getActiveNetworkInfo() != null && getCnnManager(context).getActiveNetworkInfo().isAvailable();
    }

    /**
     * 网络类型
     *
     * @return
     */
    public static int getInternetType(Context context) {
        return getCnnManager(context).getActiveNetworkInfo().getType();
    }

    /**
     * 关闭软键盘
     *
     * @param context
     * @param view
     */
    public static void hideSoftInput(Context context, View view) {
        if (view == null) return;
        getSoftInputManager(context).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    /**
     * 获取剪切板
     *
     * @param context
     * @return
     */
    public static ClipboardManager getClipboardManager(Context context) {
        return (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    }
}
