package com.robert.rssparsersample;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.robert.rssparser.RssItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter for listing {@link RssItem}
 */
class RssItemsAdapter extends RecyclerView.Adapter<RssItemsAdapter.ViewHolder> {

    private final List<RssItem> mItems = new ArrayList<>();
    private OnItemClickListener mListener;
    private final Context mContext;

    RssItemsAdapter(Context context) {
        mContext = context;
    }

    /**
     * Item click listener
     *
     * @param listener listener
     */
    void setListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    /**
     * Set {@link RssItem} list
     *
     * @param items item list
     */
    void setItems(List<RssItem> items) {
        mItems.clear();
        mItems.addAll(items);
    }

    @Override
    public RssItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_rss_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RssItemsAdapter.ViewHolder holder, int position) {
        if (mItems.size() <= position) {
            return;
        }
        final RssItem item = mItems.get(position);
        holder.textTitle.setText(item.getTitle());
        holder.textPubDate.setText(item.publishDate);
        Log.e("onBindViewHolder", "--->Title=" + item.getTitle() + "|thumbnail=" + item.thumbnail);
        if (item.thumbnail != null) {
            Picasso.with(mContext).load(item.thumbnail).
                    fit()
                    .centerCrop()
                    .into(holder.imageThumb);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) mListener.onItemSelect(item);
            }
        });
        holder.itemView.setTag(item);

    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }


    interface OnItemClickListener {
        void onItemSelect(RssItem rssItem);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTitle)
        TextView textTitle;

        @BindView(R.id.tvPubDate)
        TextView textPubDate;

        @BindView(R.id.ivThumb)
        ImageView imageThumb;

        @BindView(R.id.llTextContainer)
        RelativeLayout llTextContainer;


        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
