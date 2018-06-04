package com.deguffroy.adrien.mynews.Models;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrien Deguffroy on 01/06/2018.
 */

public class APIResponseDeserializer implements JsonDeserializer<Result> {
    @Override
    public Result deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Result result =  new Gson().fromJson(json, Result.class);
        JsonElement jsonObject = ((JsonObject) json).get("media");
        if (jsonObject instanceof JsonArray){
            JsonArray jsonArray = (JsonArray)((JsonObject) json).get("media");
            Media[] arrayMedia = new Gson().fromJson(jsonArray,Media[].class);
            result.setUrlImageMedia(arrayMedia[0].getMediaMetadata().get(0).getUrl());
        }
        return result;
    }
}
