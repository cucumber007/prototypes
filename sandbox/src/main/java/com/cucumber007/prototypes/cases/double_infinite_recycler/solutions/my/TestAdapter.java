package com.cucumber007.prototypes.cases.double_infinite_recycler.solutions.my;

import android.content.Context;
import android.view.View;

import com.cucumber007.prototypes.cases.double_infinite_recycler.Payload;
import com.cucumber007.reusables.recycler.BaseRecyclerViewAdapter;

import java.util.List;

public class TestAdapter extends BaseRecyclerViewAdapter<Payload, BaseViewHolder> {

    public TestAdapter(Context context, List<Payload> items, int itemLayout) {
        super(context, items, itemLayout);
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new BaseViewHolder(view);
    }

    @Override
    public void bindViewHolder(BaseViewHolder baseViewHolder, Payload item, int position) {
        baseViewHolder.bind(item.getName());
    }
}
