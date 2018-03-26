package com.cucumber007.prototypes.cases.double_infinite_recycler.solutions;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cucumber007.prototypes.R;
import com.cucumber007.prototypes.cases.double_infinite_recycler.DataProvider;
import com.cucumber007.prototypes.cases.double_infinite_recycler.Payload;
import com.cucumber007.reusables.recycler.BaseRecyclerViewAdapter;
import com.cucumber007.reusables.utils.Callback;
import com.cucumber007.reusables.utils.logging.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DoubleEndlessRecyclerAdapter extends RecyclerView.Adapter<DoubleEndlessRecyclerAdapter.StringHolder> {

    private Context context;
    private int itemLayout;
    private DataProvider dataProvider;
    private boolean isLoading;
    int pageLimit = 3;
    int count;
    //todo android collection
    private SparseArray<List<Payload>> pageMap = new SparseArray<>();
    private Callback<String> dataListener;

    public DoubleEndlessRecyclerAdapter(Context context, @LayoutRes int itemLayout, DataProvider dataProvider) {
        this.context = context;
        this.itemLayout = itemLayout;
        this.dataProvider = dataProvider;
        loadNextPage(0);
    }

    public void bindViewHolder(StringHolder holder, Payload item, int position) {
        fillData(holder, item, position);
        onItemRequest(position);
    }

    private void fillData(StringHolder holder, Payload item, int position) {
        if(holder.getLayoutPosition() == position) holder.tvText.setText(item.getName());
    }

    private void onItemRequest(int position) {
        int page = detectPage(position);
        //todo async
        if(!isLoading) {
            if (page >= getMaxPage()) {
                loadNextPage(getMaxPage() + 1);
            }
            if (page != 0 && page <= getMinPage()) {
                loadPreviousPage(getMinPage() - 1);
            }
        }
    }

    private int getMaxPage() {
        return pageMap.keyAt(pageMap.size()-1);
    }

    private int getMinPage() {
        return pageMap.keyAt(0);
    }

    private void loadNextPage(int page) {
        LogUtil.logDebug("loading", page);
        isLoading = true;

        if (dataListener != null) dataListener.onReady(getData());

        dataProvider.getDataForPage(page, lst -> {
            if(pageMap.size() > pageLimit) {
                LogUtil.logDebug("delete", getMinPage());
                pageMap.delete(getMinPage());
            }
            onPageLoaded(page, lst);
        });
    }

    private void loadPreviousPage(int page) {
        LogUtil.logDebug("loading", page);
        isLoading = true;

        if (dataListener != null) dataListener.onReady(getData());

        dataProvider.getDataForPage(page, lst -> {
            if(pageMap.size() > pageLimit) {
                LogUtil.logDebug("delete", getMaxPage());
                pageMap.delete(getMaxPage());
            }
            onPageLoaded(page, lst);
        });
    }

    private void onPageLoaded(int page, List<Payload> lst) {
        count += lst.size();
        pageMap.put(page, lst);
        notifyDataSetChanged();
        isLoading = false;

        if (dataListener != null) dataListener.onReady(getData());
    }

    @Override
    public int getItemCount() {
        return count;
    }

    private int detectPage(int position) {
        return position / dataProvider.getPageSize();
    }

    public Payload getItem(int position) {
        int page = detectPage(position);
        return pageMap.get(page).get(page == 0 ? position : position - page*dataProvider.getPageSize());
    }

    public void setDataListener(Callback<String> dataListener) {
        this.dataListener = dataListener;
    }

    private String getData() {
        StringBuilder stringBuilder = new StringBuilder(pageLimit*2);
        for (int i = 0; i < pageMap.size(); i++) {
            stringBuilder.append(pageMap.keyAt(i));
            stringBuilder.append(' ');
        }
        if(isLoading) stringBuilder.append(" - loading...");
        return stringBuilder.toString();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Misc
    ///////////////////////////////////////////////////////////////////////////

    public StringHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(this.itemLayout, parent, false);
        return this.createViewHolder(v);
    }

    public void onBindViewHolder(StringHolder holder, int position) {
        this.bindViewHolder(holder, this.getItem(position), position);
    }

    public StringHolder createViewHolder(View view) {
        return new StringHolder(view);
    }

    public int getItemLayout() {
        return this.itemLayout;
    }

    public Context getContext() {
        return context;
    }

    public static class StringHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text) TextView tvText;
        
        public StringHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    
}
