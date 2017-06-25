package com.cucumber007.prototypes.activities.giver;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cucumber007.prototypes.R;
import com.cucumber007.prototypes.reusables.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.TagViewHolder> {


    private Context context;
    private List<PendingTag> items = new ArrayList<>();
    private OnTagClickListener onTagClickListener;

    public TagsAdapter(Context context, List<String> tags, OnTagClickListener onTagClickListener) {
        this.context = context;
        for (int i = 0; i < tags.size(); i++) items.add(new PendingTag(tags.get(i)));
        this.onTagClickListener = onTagClickListener;
    }

    @Override
    public TagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tag, parent, false);
        return new TagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TagViewHolder holder, int position) {
        holder.text.setText("#"+getItem(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public PendingTag getItem(int position) {
        return items.get(position);
    }

    public int getItemPosition(String name) {
        for (int i = 0; i < items.size(); i++) {;
            if(items.get(i).getTag().equals(name)) return i;
        }
        return -1;
    }

    public List<String> getVisibleTags() {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < items.size(); i++)
            res.add(items.get(i).getTag());
        return res;
    }

    public void addTag(String tag) {
        items.add(new PendingTag(tag));
    }

    public int removeTag(String tag) {
        //todo check -1
        int position = getItemPosition(tag);
        items.remove(position);
        return position;
    }

    public void removeTag(int position) {
        items.remove(position);
    }

    public boolean contains(String tag) {
        return getItemPosition(tag) != -1;
    }

    public class TagViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.text) TextView text;
        @Bind(R.id.root) View root;

        public TagViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.root)
        void onTagClick(View view) {
            try {
                onTagClickListener.onClick(view, getItem(getAdapterPosition()).getTag(), getAdapterPosition());
            } catch (IndexOutOfBoundsException e) {
                LogUtil.logError(e);
                e.printStackTrace();
            }
        }
    }

    public interface OnTagClickListener {
        void onClick(View view, String tag, int position);
    }

    public class PendingTag {
        private String tag;
        private boolean pending;

        public PendingTag(String tag) {
            this.tag = tag;
        }

        public String getTag() {
            return tag;
        }

        public boolean isPending() {
            return pending;
        }

        public void setPending(boolean pending) {
            this.pending = pending;
        }

        @Override
        public String toString() {return tag;}

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof PendingTag)) return false;
            return tag.equals(((PendingTag)obj).getTag());
        }
    }

}
