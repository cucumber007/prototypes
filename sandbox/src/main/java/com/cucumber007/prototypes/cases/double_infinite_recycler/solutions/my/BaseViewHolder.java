package com.cucumber007.prototypes.cases.double_infinite_recycler.solutions.my;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cucumber007.prototypes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text) TextView textView;

    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(String item) {
        textView.setText(item);
    }
}
