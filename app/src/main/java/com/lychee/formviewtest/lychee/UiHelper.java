package com.lychee.formviewtest.lychee;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import com.lychee.formviewtest.AppContext;


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

}
