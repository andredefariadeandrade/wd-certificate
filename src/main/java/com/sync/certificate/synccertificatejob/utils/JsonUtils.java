package com.sync.certificate.synccertificatejob.utils;

import com.google.gson.Gson;

public class JsonUtils {

    public static String parseObjetToString(final Object object){
        Gson g = new Gson();
        return g.toJson(object);
    }

    public static <T> T  parseStringToObject(final Class<T> clazz, final String json){
        Gson g = new Gson();
        return g.fromJson(json, clazz);
    }

}
