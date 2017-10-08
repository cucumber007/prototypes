package com.cucumber007.reusables.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseHeaderRecyclerAdapter<T, IH extends RecyclerView.ViewHolder, HH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private Context context;
    private List<T> items;
    private int itemLayout;
    private int headerLayout;
    private OnItemClickListener<T> itemClickListener;

    public BaseHeaderRecyclerAdapter(Context context, List<T> items, int itemLayout, int headerLayout) {
        this.context = context;
        this.items = items;
        this.itemLayout = itemLayout;
        this.headerLayout = headerLayout;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(context).inflate(itemLayout, parent, false);
            return createItemHolder(v);
        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(context).inflate(headerLayout, parent, false);
            return createHeaderHolder(v);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(isHeaderHolder(holder)) {
            bindHeaderHolder((HH)holder, position);
        } else {
            bindItemHolder((IH)holder, getItem(position), position);
        }
    }

    protected abstract boolean isHeaderHolder(RecyclerView.ViewHolder holder);
    protected abstract IH createItemHolder(View view);
    protected abstract HH createHeaderHolder(View view);
    protected abstract void bindItemHolder(IH holder, T item, int position);
    protected abstract void bindHeaderHolder(HH holder, int position);

    @Override
    public int getItemCount() {
        return items.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) return TYPE_HEADER;
        else return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private T getItem(int position) {
        return items.get(position-1);
    }

    public OnItemClickListener<T> getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener<T>{
        void onItemClick(int position, View view, T item);
    }
}
