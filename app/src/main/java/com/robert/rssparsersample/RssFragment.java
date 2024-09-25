package com.robert.rssparsersample;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.robert.rssparser.RssConverterFactory;
import com.robert.rssparser.RssFeed;
import com.robert.rssparser.RssItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * Fragment for listing fetched {@link RssItem} list
 */
public class RssFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RssItemsAdapter.OnItemClickListener {

    private static final String KEY_FEED = "FEED_URL";

    private String mFeedUrl;
    private RssItemsAdapter mAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.swRefresh)
    SwipeRefreshLayout mSwRefresh;

    /**
     * Creates new instance of {@link RssFragment}
     * @param feedUrl Fetched Url Feed
     * @return Fragment
     */
    public static RssFragment newInstance(String feedUrl) {
        RssFragment rssFragment = new RssFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_FEED, feedUrl);
        rssFragment.setArguments(bundle);
        return rssFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFeedUrl = getArguments().getString(KEY_FEED);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rss, container, false);
        ButterKnife.bind(this, view);

        mAdapter = new RssItemsAdapter(getContext());
        mAdapter.setListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mSwRefresh.setOnRefreshListener(this);

        fetchRss();
        return view;
    }

    /**
     * Fetches Rss Feed Url
     */
    private void fetchRss() {
        String basicAuth = "Basic " + Base64.encodeToString("abc:xyz".getBytes(), Base64.NO_WRAP);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();

            Request.Builder requestBuilder = original.newBuilder()
                    .header("Accept", "application/xml; charset=UTF-8")
                    .method(original.method(), original.body());
            requestBuilder.addHeader("Authorization", basicAuth);

            return chain.proceed(requestBuilder.build());
        });

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://vnexpress.net")
                .addConverterFactory(RssConverterFactory.create())
                .client(client)
                .build();

        showLoading();
        RssService service = retrofit.create(RssService.class);
        service.fetchRss(mFeedUrl)
                .enqueue(new Callback<RssFeed>() {
                    @Override
                    public void onResponse(Call<RssFeed> call, Response<RssFeed> response) {
                        onRssItemsLoaded(response.body().getItems());
                        hideLoading();
                    }

                    @Override
                    public void onFailure(Call<RssFeed> call, Throwable t) {
                        Toast.makeText(getContext(), "Failed to fetchRss RSS feed!", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    /**
     * Loads fetched {@link RssItem} list to RecyclerView
     * @param rssItems
     */
    public void onRssItemsLoaded(List<RssItem> rssItems) {
        Log.e("onRssItemsLoaded", "--->rssItems=" + (rssItems != null ? rssItems.size() : 0));
        mAdapter.setItems(rssItems);
        mAdapter.notifyDataSetChanged();
        if (mRecyclerView.getVisibility() != View.VISIBLE) {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Shows {@link SwipeRefreshLayout}
     */
    public void showLoading() {
        mSwRefresh.setRefreshing(true);
    }


    /**
     * Hides {@link SwipeRefreshLayout}
     */
    public void hideLoading() {
        mSwRefresh.setRefreshing(false);
    }


    /**
     * Triggers on {@link SwipeRefreshLayout} refresh
     */
    @Override
    public void onRefresh() {
        fetchRss();
    }

    /**
     * Browse {@link RssItem} url
     * @param rssItem
     */
    @Override
    public void onItemSelect(RssItem rssItem) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(rssItem.link));
        startActivity(intent);
    }
}