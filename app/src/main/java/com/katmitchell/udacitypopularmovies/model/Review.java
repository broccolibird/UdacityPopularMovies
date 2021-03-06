package com.katmitchell.udacitypopularmovies.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kat on 11/15/15.
 */
public class Review {

    @SerializedName("id")
    private String id;

    @SerializedName("author")
    private String author;

    @SerializedName("content")
    private String content;

    @SerializedName("url")
    private String url;

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }
}
