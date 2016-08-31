package com.lychee.formviewtest.lychee;

/**
 * Created by lychee on 2015/12/23.
 */
public class FuckJson {

    /**
     * 对象转数组
     *
     * @param fjson
     * @param feild
     * @return
     */
    public static String cao(String fjson, String... feild) {
        for (int i = 0; i < feild.length; i++) {
            fjson = fjson.replace("\"" + feild[i] + "\":{}", "\"" + feild[i] + "\":[]");
        }
        return fjson;
    }

    /**
     * 数组转对象
     *
     * @param fjson
     * @param feild
     * @return
     */
    public static String gan(String fjson, String... feild) {
        for (int i = 0; i < feild.length; i++) {
            fjson = fjson.replace("\"" + feild[i] + "\":[]", "\"" + feild[i] + "\":{}");
        }
        return fjson;
    }
}
