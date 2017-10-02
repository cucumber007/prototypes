package com.cucumber007.reusables.recycler;

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


public class ArrayRecyclerAdapter<T> extends BaseRecyclerViewAdapter<T, ArrayRecyclerAdapter.TextViewHolder> {

    private int textViewId = -1;
    private OnItemClickListener onItemClickListener;

    public ArrayRecyclerAdapter(Context context, T[] items) {
        super(context, Arrays.asList(items), -1);
    }

    public ArrayRecyclerAdapter(Context context, List<T> items) {
        super(context, items, -1);
    }

    public ArrayRecyclerAdapter(Context context, List<T> items, @LayoutRes int itemLayout, int textViewId) {
        super(context, items, itemLayout);
        this.textViewId = textViewId;
    }

    @Override
    TextViewHolder createViewHolder(View view) {
        return null;
    }

    @Override
    public void bindViewHolder(ArrayRecyclerAdapter.TextViewHolder holder, T item, int position) {
        holder.getTextView().setText(item.toString());
    }

    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(getItemLayout() != -1) {
            View v = LayoutInflater.from(getContext())
                    .inflate(getItemLayout(), parent, false);
            return new TextViewHolder(v);
        } else {
            TextView textView = new TextView(getContext());
            int padding = (int)DpUtils.dpToPx(15, getContext());
            textView.setPadding(padding, padding, padding, padding);
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new TextViewHolder(textView, true);
        }
    }

    public void setItemsAndUpdate(List<T> items) {
        setItems(items);
        notifyDataSetChanged();
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
