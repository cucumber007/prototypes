package com.cucumber007.prototypes.cases.double_infinite_recycler.solutions.shkur;

import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cucumber007.prototypes.R;
import com.cucumber007.reusables.utils.Callback;
import com.cucumber007.reusables.utils.logging.LogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Project: EndlessScrollingBothSides
 * Follow me: @tonyshkurenko
 * <p>
 * Create pages after and before
 *
 * @author Anton Shkurenko
 * @since 3/23/18
 */
public class PagesProviderRecyclerViewAdapter extends RecyclerView.Adapter<PagesProviderRecyclerViewAdapter.MyViewHolder> {

    private static final String TAG = "shkur";

    static final int PAGE_LIMIT = 3;

    private final SparseArrayCompat<List<String>> pages = new SparseArrayCompat<>();
    private int firstPage = 0;
    private int currentCount = 0;
    private Callback<String> dataListener;

    private boolean loading = true;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(
                LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_simple,
                        parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        return currentCount;
    }

    String getItem(int position) {
        int count = currentCount;
        for (int i = firstPage + pages.size() - 1; i >= firstPage; i--) {
            final List<String> page = pages.get(i);
            final int pageSize = page.size();
            if (position >= count - pageSize) {
                return page.get(pageSize - (count - position));
            } else {
                count -= pageSize;
            }
        }

        throw new IllegalStateException("Wrong position");
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text) TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(String item) {
            textView.setText(item + "");
        }
    }

    public void initValues(List<String> vals) {
        pages.append(this.firstPage, vals);
        currentCount += vals.size();

        loading = false;

        notifyItemRangeInserted(0, currentCount);

        printPages();
    }

    void appendValues(List<String> values) {

        Log.d(TAG, "appendValues() called (before), count: " + currentCount + ", bottom(): " + bottom());
        printPages();

        if (pages.size() >= PAGE_LIMIT) {
            pages.remove(firstPage++);
        }

        pages.append(firstPage + pages.size(), values);
        currentCount += values.size();

        Log.d(TAG, "Notify inserted: idx: " + (currentCount - values.size()) + ", count: " + currentCount);

        notifyItemRangeInserted(currentCount - values.size(), values.size());

        Log.d(TAG, "appendValues() called (after), count: " + currentCount + ", bottom(): " + bottom());
        printPages();

    }

    void removeValues(List<String> values) {

        Log.d(TAG, "removeValues() called (before), count: " + currentCount + ", top(): " + top());
        printPages();

        if (pages.size() >= PAGE_LIMIT) {
            final int removedSize = pages.get(firstPage + pages.size() - 1).size();
            pages.remove(firstPage + pages.size() - 1);
            currentCount -= removedSize;

            notifyItemRangeRemoved(currentCount, removedSize);
        }

        firstPage--;

        pages.append(firstPage, values);

        Log.d(TAG, "Notify removed: idx: " + (currentCount) + ", count: " + currentCount);
        Log.d(TAG, "removeValues() called (after), count: " + currentCount + ", top(): " + top());
        printPages();
    }

    int bottom() {
        return currentCount - pages.get(firstPage + pages.size() - 1).size();
    }

    int top() {

        int count = currentCount;

        for (int i = firstPage + pages.size() - 1; i > firstPage; i--) {
            final List<String> page = pages.get(i);
            final int pageSize = page.size();

            count -= pageSize;
        }

        return count;
    }

    void setLoading(boolean loading) {
        this.loading = loading;
    }

    boolean isLoading() {
        return loading;
    }

    int nextPage() {
        return firstPage + pages.size();
    }

    int prevPage() {
        return firstPage - 1;
    }

    public void setDataListener(Callback<String> dataListener) {
        this.dataListener = dataListener;
    }

    private void printPages() {
        StringBuilder res = new StringBuilder(pages.size());
        for (int i = 0; i < pages.size(); i++) {
            final int pageNum = pages.keyAt(i);
            res.append(pageNum);
            res.append(' ');
        }
        //LogUtil.logDebug("", res);
        if (dataListener != null) dataListener.onReady(res.toString());
    }
}