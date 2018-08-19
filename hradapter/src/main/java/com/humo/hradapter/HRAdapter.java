package com.humo.hradapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView 基础适配器
 * Created by zhxumao on 2017/12/4 18:26.
 */

public abstract class HRAdapter extends RecyclerView.Adapter<HRViewHolder> {

    MyUtilsd myUtilsd = new MyUtilsd("AAA");
    private List data = new ArrayList<>();
    private int selectedP = -1;

    private final static int TYPE_HEAD = 0;
    private final static int TYPE_CONTENT = 1;
    private final static int TYPE_FOOTER = 2;
    private final static int TYPE_LOADMORE = 3;
    private boolean mLoadMoreEnable, mHeaderEnable, mFooterEnable;

    public HRAdapter() {
    }

    public void addMore(List data) {
        if (data != null) {
            this.data = data;
            notifyDataSetChanged();
        }
    }

    public void clear() {
        if (data != null) {
            data.clear();
            notifyDataSetChanged();
        }
    }

    public void setSelected(int position) {
        selectedP = position;
        notifyDataSetChanged();
    }


    @Override
    public HRViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HRViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_CONTENT:
                viewHolder = getHolder(parent);
                break;
            case TYPE_HEAD:
                viewHolder = setHeaderHolder(parent);
                break;
            case TYPE_FOOTER:
                viewHolder = setFooterHolder(parent);
                break;
            case TYPE_LOADMORE:
                viewHolder = setLoadMoreHolder(parent);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HRViewHolder holder, final int position) {
        int viewType = holder.getItemViewType();
        switch (viewType) {
            case TYPE_CONTENT:
                holder.bindData(data.get(position - getHeaderLayoutCount()), position, selectedP);
                break;
            case TYPE_HEAD:
            case TYPE_FOOTER:
            case TYPE_LOADMORE:
                holder.bindData(null, position, selectedP);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return data.size() + getHeaderLayoutCount() + getFooterLayoutCount() + getLoadMoreViewCount();
    }

    @Override
    public int getItemViewType(int position) {
        int contentSize = data.size();
        if (position < getHeaderLayoutCount()) {
            return TYPE_HEAD;
        } else if (position < getHeaderLayoutCount() + contentSize) {
            return TYPE_CONTENT;
        } else if (position < getHeaderLayoutCount() + getFooterLayoutCount() + contentSize) {
            return TYPE_FOOTER;
        } else {
            return TYPE_LOADMORE;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_CONTENT ? 1 : gridManager.getSpanCount();
                }
            });
        }
    }

    private int getHeaderLayoutCount() {
        return mHeaderEnable ? 1 : 0;
    }

    private int getFooterLayoutCount() {
        return mFooterEnable ? 1 : 0;
    }

    private int getLoadMoreViewCount() {
        if (!mLoadMoreEnable || data.size() == 0) {
            return 0;
        }
        return 1;
    }

    /**
     * 是否开启加载更多功能
     *
     * @param enable
     */
    public void showLoadMoreViewHolder(boolean enable) {
        this.mLoadMoreEnable = enable;
    }

    /**
     * 是否展示头部
     *
     * @param enable
     */
    public void showHeaderViewHolder(boolean enable) {
        this.mHeaderEnable = enable;
    }

    /**
     * 是否展示底部布局
     *
     * @param enable
     */
    public void showFooterViewHolder(boolean enable) {
        this.mFooterEnable = enable;
    }

    protected HRViewHolder setHeaderHolder(ViewGroup parent) {
        throw new IllegalArgumentException("header holder view is null !!");
    }

    protected HRViewHolder setFooterHolder(ViewGroup parent) {
        throw new IllegalArgumentException("footer holder view is null !!");
    }

    protected HRViewHolder setLoadMoreHolder(ViewGroup parent) {
        throw new IllegalArgumentException("load more holder view is null !!");
    }

    public abstract HRViewHolder getHolder(ViewGroup parent);
}
