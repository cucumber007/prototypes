package com.cucumber007.reusables.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

public class ImageWithTextRecyclerAdapter<T extends ImageWithText> extends BaseRecyclerViewAdapter<T, ImageWithTextRecyclerAdapter.ViewHolder> {

    private final int textViewId;
    private final int imageViewId;

    public ImageWithTextRecyclerAdapter(Context context, List<T> items, int itemLayout, int textViewId, int imageViewId) {
        super(context, items, itemLayout);
        this.textViewId = textViewId;
        this.imageViewId = imageViewId;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindViewHolder(ImageWithTextRecyclerAdapter.ViewHolder holder, T item, int position) {
        holder.tvName.setText(item.getText());
        holder.ivGameImage.setImageResource(item.getImageResource());
        holder.itemView.setOnClickListener(view -> getItemClickListener().onItemClick(position, view, item));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivGameImage;
        TextView tvName;

        public View getRoot() {
            return itemView;
        }

        ViewHolder(View view) {
            super(view);
            ivGameImage = view.findViewById(imageViewId);
            tvName = view.findViewById(textViewId);
        }
    }
}
