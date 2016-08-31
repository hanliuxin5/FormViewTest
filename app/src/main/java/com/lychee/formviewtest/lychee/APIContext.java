package com.lychee.formviewtest.lychee;

import java.util.HashMap;

/**
 * Created by lychee on 2016/8/31.
 */
public class APIContext {
    public static void findKnockOutScore4OldCells(String eventSystemId, HttpCallback handler) {
        HashMap<String, String> rp = new HashMap<>();
        rp.put("eventSystemId", eventSystemId);
        MyOkHttpClientManager.postAsyn("http://vvsai.com/api/knockOut/findKnockOutScore4OldCells", rp, handler);
    }
}
