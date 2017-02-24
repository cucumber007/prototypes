package com.cucumber007.prototypes.reusables;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private Context context;
    private List<T> items;
    private int itemLayout;

    public BaseRecyclerViewAdapter(Context context, List<T> items, @LayoutRes int itemLayout) {
        this.context = context;
        this.items = items;
        this.itemLayout = itemLayout;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(itemLayout, parent, false);
        return createViewHolder(v);
    }

    abstract VH createViewHolder(View view);

    @Override
    public int getItemCount() {
        return items.size();
    }

    public T getItem(int position) {
        return items.get(position);
    }

    public void setItemsAndUpdate(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public Context getContext() {
        return context;
    }
}
