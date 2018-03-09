package com.cucumber007.prototypes.menu;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cucumber007.prototypes.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuListAdapter extends BaseAdapter {

    private Context context;
    private List<MenuItem> items;
    private LayoutInflater layoutInflater;


    public MenuListAdapter(Context context, List<MenuItem> items) {
        this.context = context;
        this.items = items;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_menu_list, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = ((ViewHolder) convertView.getTag());
        }

        if(!getItem(position).isDivider()) {
            vh.text.setText(getItem(position).getName());
            convertView.setOnClickListener(v -> context.startActivity(new Intent(context, getItem(position).getActivityClass())));
        } else
            vh.text.setText("---------------------------------------------------------------");

        return convertView;
    }


    @Override
    public int getCount() {
        return items.size();
    }


    @Override
    public MenuItem getItem(int position) {
        return items.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    static class ViewHolder {
        @BindView(R.id.text) TextView text;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
