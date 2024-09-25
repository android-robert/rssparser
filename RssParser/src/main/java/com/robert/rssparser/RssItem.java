package com.robert.rssparser;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Model for Rss Item
 */
public class RssItem implements Serializable {

    public String title;
    public String link;
    public String thumbnail;
    public String image;
    public String media;
    public String publishDate;
    public String description;
    public String contentEncoded;

    public ArrayList<String> images = new ArrayList<String>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title.replace("&#39;", "'").replace("&#039;", "'");
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (title != null) {
            builder.append(title).append("\n");
        }
        if (link != null) {
            builder.append(link).append("\n");
        }
        if (media != null) {
            builder.append(media).append("\n");
        }
        if (thumbnail != null) {
            builder.append(thumbnail).append("\n");
        }
        if (image != null) {
            builder.append(image).append("\n");
        }
        if (description != null) {
            builder.append(description).append("\n");
        }
        if (contentEncoded != null) {
            builder.append(contentEncoded);
        }
        return builder.toString();
    }
}
