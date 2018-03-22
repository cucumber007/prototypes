package com.cucumber007.prototypes.sandbox.google_paging;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cucumber007.prototypes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PagingAdapter extends PagedListAdapter<String, PagingAdapter.ViewHolder> {

    private final Context context;

    public PagingAdapter(Context context, @NonNull DiffCallback<String> diffCallback) {
        super(diffCallback);
        this.context = context;
    }



    @Override
    public PagingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_simple, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PagingAdapter.ViewHolder holder, int position) {
        holder.tvText.setText(getItem(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text) TextView tvText;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
