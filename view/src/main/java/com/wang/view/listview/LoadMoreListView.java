package com.wang.view.listview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

public class LoadMoreListView extends ListView {

    private static final String TAG = "LoadMoreListView";

    private int mLastVisibleItem;
    private int mTotalItemCount;
    private boolean isLoading;
    private View mLoadMoreView;

    public LoadMoreListView(Context context) {
        super(context);
        setOnItemLongClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            startActionMode(new MultiChoiceModeListener() {
                @Override
                public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                    Log.d(TAG, "onItemCheckedStateChanged: position = " + position);
                }

                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                }
            });
            return true;
        });
    }

    public void setLoadMoreView(@LayoutRes int resId) {
        View loadMoreView = LayoutInflater.from(getContext()).inflate(resId, null);
        setLoadMoreView(loadMoreView);
    }

    public void setLoadMoreView(View loadMoreView) {
        if (loadMoreView == null) {
            return;
        }
        mLoadMoreView = loadMoreView;
        addFooterView(mLoadMoreView);
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mLastVisibleItem = firstVisibleItem + visibleItemCount;
                mTotalItemCount = totalItemCount;
            }
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (mLastVisibleItem == mTotalItemCount && scrollState == SCROLL_STATE_IDLE) {
                    if (!isLoading) {
                        isLoading = true;
                        if (mOnLoadListener != null) {
                            mOnLoadListener.onLoad();
                        }
                    }
                }
            }
        });
    }

    public void loadCompleted() {
        isLoading = false;
        if (mLoadMoreView != null) {
            mLoadMoreView.setVisibility(View.GONE);
        }
    }

    public interface OnLoadListener {
        void onLoad();
    }
    OnLoadListener mOnLoadListener;
    public void setOnLoadListener(OnLoadListener listener) {
        mOnLoadListener = listener;
    }
}
