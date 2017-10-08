package com.cucumber007.prototypes.sandbox.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cucumber007.prototypes.R;
import com.cucumber007.reusables.recycler.BaseHeaderRecyclerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeaderAdapter extends BaseHeaderRecyclerAdapter<String, HeaderAdapter.ItemHolder, HeaderAdapter.HeaderHolder> {

    public HeaderAdapter(Context context, List<String> items, int itemLayout, int headerLayout) {
        super(context, items, itemLayout, headerLayout);
    }

    @Override
    protected boolean isHeaderHolder(RecyclerView.ViewHolder holder) {
        return holder instanceof HeaderHolder;
    }

    @Override
    protected ItemHolder createItemHolder(View view) {
        return new ItemHolder(view);
    }

    @Override
    protected HeaderHolder createHeaderHolder(View view) {
        return new HeaderHolder(view);
    }

    @Override
    protected void bindItemHolder(ItemHolder holder, String item, int position) {
        holder.tvText.setText(item);
    }

    @Override
    protected void bindHeaderHolder(HeaderHolder holder, int position) {
        holder.tvText.setText("Header");
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text) TextView tvText;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class HeaderHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text) TextView tvText;

        public HeaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
