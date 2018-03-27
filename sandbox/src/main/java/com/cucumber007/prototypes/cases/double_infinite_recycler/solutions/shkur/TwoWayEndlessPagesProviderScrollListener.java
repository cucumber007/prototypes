package com.cucumber007.prototypes.cases.double_infinite_recycler.solutions.shkur;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.List;

/**
 * Project: EndlessScrollingBothSides
 * Follow me: @tonyshkurenko
 *
 * @author Anton Shkurenko
 * @since 3/26/18
 */

public class TwoWayEndlessPagesProviderScrollListener extends RecyclerView.OnScrollListener {

    PagesProviderRecyclerViewAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    PageProvider mProvider;

    public TwoWayEndlessPagesProviderScrollListener(PagesProviderRecyclerViewAdapter adapter,
                                                    LinearLayoutManager layoutManager, PageProvider provider) {
        mAdapter = adapter;
        mLayoutManager = layoutManager;
        mProvider = provider;
    }

    @Override public void onScrolled(final RecyclerView view, int dx, int dy) {
        final int lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
        final int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

        if (!mAdapter.isLoading()) {
            if (dy > 0) { // scroll down
                if (lastVisibleItemPosition > mAdapter.bottom()) {
                    mAdapter.setLoading(true);
                    mProvider.request(mAdapter.nextPage(), ints -> view.post(() -> {
                        mAdapter.setLoading(false);
                        mAdapter.appendValues(ints);
                    }));
                }
            } else if (dy < 0) { // scroll top
                if (firstVisibleItemPosition < mAdapter.top()) {
                    mAdapter.setLoading(true);
                    mProvider.request(mAdapter.prevPage(), ints -> view.post(() -> {
                        mAdapter.setLoading(false);
                        mAdapter.removeValues(ints);
                    }));
                }
            }
        }
    }
}
