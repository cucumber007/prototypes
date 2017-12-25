package com.cucumber007.reusables.recycler;

import android.content.Context;

import com.bumptech.glide.Glide;

import java.util.List;

public class GlideImageWithTextAdapter<T extends ImageWithText> extends ImageWithTextRecyclerAdapter<T> {


    public GlideImageWithTextAdapter(Context context, List<T> items, int itemLayout, int textViewId, int imageViewId) {
        super(context, items, itemLayout, textViewId, imageViewId);
    }

    @Override
    public void bindViewHolder(GlideImageWithTextAdapter.ViewHolder holder, T item, int position) {
        if (holder.tvName != null) holder.tvName.setText(item.getText());
        if (holder.ivGameImage != null) Glide.with(getContext()).load(item.getImageResource()).into(holder.ivGameImage);
        holder.itemView.setOnClickListener(view -> getItemClickListener().onItemClick(position, view, item));
    }


}
