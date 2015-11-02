package com.katmitchell.udacitypopularmovies.model;

/**
 * Created by Kat on 11/1/15.
 */
public class Video {

    private String id;

    private String iso639_1;

    private String key;

    private String name;

    private String site;

    private int size;

    private String type;

    public String getId() {
        return id;
    }

    public String getIso639_1() {
        return iso639_1;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }

    public int getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return "https://www.youtube.com/watch?v=" + key;
    }
}
