package com.cucumber007.prototypes.activities._ui.recycler;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class RecyclerEndlessScrollListener extends RecyclerView.OnScrollListener {

    private int visibleThreshold = 3;
    private int currentPage = 0;
    private int currentTotalItems = 0;
    private int firstItemPageIndex = 0;
    private boolean loading = false;

    private OnLoadMoreListener loadMoreListener;


    public RecyclerEndlessScrollListener(
            OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

        int totalItemCount = recyclerView.getAdapter().getItemCount();
        int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
        int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

        if (totalItemCount < currentTotalItems) {
            this.currentPage = this.firstItemPageIndex;
            this.currentTotalItems = totalItemCount;
            if (totalItemCount == 0) { this.loading = true; }
        }

        if (loading && (totalItemCount > currentTotalItems)) {
            loading = false;
            currentTotalItems = totalItemCount;
            currentPage++;
        }

        if (!loading && (totalItemCount - visibleItemCount)<=(firstVisibleItem + visibleThreshold)) {
            loadMoreListener.onLoadMore(currentPage + 1, totalItemCount);
            loading = true;
        }

    }

    public interface OnLoadMoreListener {
        void onLoadMore(int page, int totalItemsCount);
    }
}
