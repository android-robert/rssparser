package com.robert.rssparsersample;

import com.robert.rssparser.RssFeed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Service for RSS
 */

public interface RssService {

    /**
     * No baseUrl defined. Each RSS Feed will be consumed by it's Url
     * @param url RSS Feed Url
     * @return Retrofit Call
     */
    @GET
    Call<RssFeed> fetchRss(@Url String url);
}
