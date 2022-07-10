package com.datapay.onecard.network;

import com.google.gson.Gson;

public class JsonHelper {
    private static JsonHelper jsonHelper;
    private static Gson gson;

    private JsonHelper() {

    }

    public static JsonHelper getInstance() {
        if (jsonHelper == null) {
            jsonHelper = new JsonHelper();
        }
        if (gson == null) {
            gson = new Gson();
        }
        return jsonHelper;
    }

    public String toJson(Object object) {
        try {
            return gson.toJson(object);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public Object fromJson(String json, Class<?> zlass) {
        try {
            return gson.fromJson(json, zlass);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
