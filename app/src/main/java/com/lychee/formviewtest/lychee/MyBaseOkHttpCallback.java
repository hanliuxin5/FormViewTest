package com.lychee.formviewtest.lychee;

import android.app.Activity;
import android.content.Context;

import com.google.gson.Gson;

/**
 * Created by lychee on 2016/3/17.
 */
public abstract class MyBaseOkHttpCallback implements HttpCallback {
    //    private String json = null;
    private BaseBean bb = null;
    public static Gson gson = new Gson();
    private Context context;
    private Activity activity;

    public MyBaseOkHttpCallback(Context context) {
        this.context = context;
        if (!DeviceHelper.hasInternet(context)) {
            UiHelper.toast("没有可用网络！");
        }
    }

    @Override
    public void onError(String error) {
        LogUtil.d("MyOkHttpCallback onError---" + error);
        if (!"Canceled".equals(error)) {
            UiHelper.toast("请检查网络");
        }
        onFinsh(error);
    }

    @Override
    public void onResponse(String response) {
//        LogUtil.d("onResponse---" + response);

        bb = ResolveJson(response);

        if (bb != null) {
            onSuccess(response);
        } else {
            onNotSuccess(response);
            onFinsh(response);
        }
        onFinsh(response);
    }

    public abstract void onSuccess(String json);

    public abstract void onNotSuccess(String json);

    public abstract void onFinsh(String json);


    private BaseBean ResolveJson(String json) {
        BaseBean bb = null;
        try {
//            A a = gson.fromJson(json, A.class);
//            a.getResult().get(0).getDoubleName();
            bb = gson.fromJson(json, BaseBean.class);
            if (1 == bb.getStatus()) {
                return bb;
            }

            UiHelper.toast(ErrorCodeTransform.Transform(bb.getMsg() + ""));
            LogUtil.e(ErrorCodeTransform.Transform(bb.getMsg() + ""));
            if (Integer.parseInt(bb.getMsg() + "") == 100004) {
                activity = (Activity) context;
                UiHelper.toast("未登录，操作待实现");
//                LogUtil.i("当前activity" + activity.getLocalClassName());
//                if (activity instanceof GroupFightDetailsActivity
//                        || activity instanceof VenuesDetailsActivity) {
//                } else {
                //需要结束当前页面
//                AppManager.getAppManager().finishActivity(activity);
//                }
                //sessionid过期跳转到登录
//                AppUser.cleanLoginInfo();
//                 UiHelper.jumpToLogin(context);

            }
            if (Integer.parseInt(bb.getMsg() + "") == 100008) {
                activity = (Activity) context;
//                MobclickAgent.reportError(context, "接口请求错误：" + activity.getLocalClassName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            UiHelper.toast("数据解析出错 >_<||");
            return null;
        }
        return null;
    }
}
