package com.lychee.formviewtest.lychee;

/**
 * Created by lychee on 2016/3/17.
 */
public interface HttpCallback {
    void onError(String error);

    void onResponse(String response);
}
