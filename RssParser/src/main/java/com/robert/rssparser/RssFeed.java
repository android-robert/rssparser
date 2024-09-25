package com.robert.rssparser;

import java.util.List;

/**
 * RSS Feed response model
 */

public class RssFeed {
    public RssFeed(List<RssItem> items) {
        this.items = items;
    }
    /**
     * List of parsed {@link RssItem} objects
     */
    private List<RssItem> items;

    public List<RssItem> getItems() {
        return items;
    }

    void setItems(List<RssItem> items) {
        this.items = items;
    }
}
