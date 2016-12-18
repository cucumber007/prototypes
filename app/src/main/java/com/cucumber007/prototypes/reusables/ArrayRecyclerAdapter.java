package com.cucumber007.prototypes.reusables;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;


public class ArrayRecyclerAdapter<T> extends RecyclerView.Adapter<ArrayRecyclerAdapter.TextViewHolder> {

    private final Context context;
    private List<T> items;
    private int itemLayout = -1;
    private int textViewId = -1;
    private OnItemClickListener onItemClickListener;

    public ArrayRecyclerAdapter(Context context, T[] items) {
        this.context = context;
        this.items = Arrays.asList(items);
    }

    public ArrayRecyclerAdapter(Context context, List<T> items) {
        this.context = context;
        this.items = items;
    }

    public ArrayRecyclerAdapter(Context context, List<T> items, @LayoutRes int itemLayout, int textViewId) {
        this.context = context;
        this.items = items;
        this.itemLayout = itemLayout;
        this.textViewId = textViewId;
    }

    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(itemLayout != -1) {
            View v = LayoutInflater.from(context)
                    .inflate(itemLayout, parent, false);
            return new TextViewHolder(v);
        } else {
            TextView textView = new TextView(context);
            int padding = (int)DpUtils.dpToPx(15, context);
            textView.setPadding(padding, padding, padding, padding);
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new TextViewHolder(textView, true);
        }
    }

    @Override
    public void onBindViewHolder(ArrayRecyclerAdapter.TextViewHolder holder, int position) {
        holder.getTextView().setText(getItem(position).toString());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public T getItem(int position) {
        return items.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class TextViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private View itemView;

        public TextViewHolder(TextView itemView, boolean simple) {
            super(itemView);
            this.textView = itemView;
            this.itemView = itemView;
            itemView.setOnClickListener(view -> {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(getAdapterPosition(), itemView);
            });
        }

        public TextViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            textView = ButterKnife.findById(itemView, textViewId);
            itemView.setOnClickListener(view -> {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(getAdapterPosition(), itemView);
            });
        }

        public TextView getTextView() {
            return textView;
        }
    }

    static class DpUtils {
        public static float dpToPx(float dp, Context context) {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position, View view);
    }

}
