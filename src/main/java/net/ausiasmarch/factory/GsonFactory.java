package net.ausiasmarch.factory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonFactory {

    public static Gson getGson() {
        GsonBuilder oGsonBuilder = new GsonBuilder();
        oGsonBuilder.setDateFormat("dd/MMM/yyyy HH:mm");
        oGsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson oGson = oGsonBuilder.create();
        return oGson;
    }
}
