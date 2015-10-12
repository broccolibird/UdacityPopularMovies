package com.katmitchell.udacitypopularmovies.network;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Kat on 10/4/15.
 */
public class DateDeserializer implements JsonDeserializer<Date> {

    private static final String TAG = "DateDeserializer";

    private static final String[] DATE_FORMATS = new String[]{
            "yyyy-MM-dd"
    };

    @Override
    public Date deserialize(JsonElement jsonElement, Type type,
            JsonDeserializationContext jsonDeserializationContext) throws
            JsonParseException {

        if (jsonElement.getAsString().isEmpty()) {
            return null;
        }

        for (String format : DATE_FORMATS) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                return sdf.parse(jsonElement.getAsString());
            } catch (ParseException e) {
            }
        }
        throw new JsonParseException(
                "Unparselable date: \"" + jsonElement.getAsString() + "\". Supported formats: "
                        + Arrays
                        .toString(DATE_FORMATS));
    }
}
