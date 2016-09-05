package com.lychee.formviewtest.lychee;

import java.util.HashMap;

/**
 * Created by lychee on 2016/8/31.
 */
public class APIContext {
    public static void findKnockOutScore4OldCells(String eventSystemId, HttpCallback handler) {
        HashMap<String, String> rp = new HashMap<>();
        rp.put("eventSystemId", eventSystemId);
        MyOkHttpClientManager.postAsyn("http://192.168.1.112:8080/api/knockOut/findKnockOutScore4OldCells", rp, handler);
    }
}
