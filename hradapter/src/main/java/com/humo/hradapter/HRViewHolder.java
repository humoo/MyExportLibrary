package com.humo.hradapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhxumao on 2017/12/4 18:22.
 */

public abstract class HRViewHolder<T> extends RecyclerView.ViewHolder {

    private HRListener listener;
    private Context mContext;

    public HRViewHolder(Context context, ViewGroup root, int layoutRes, final HRListener listener) {
        super(LayoutInflater.from(context).inflate(layoutRes, root, false));
        this.mContext = context;
        this.listener = listener;
        bindView(itemView);
    }

    public Context getContext() {
        return mContext;
    }

    public HRListener getHRListener() {
        return listener;
    }

    /**
     * 绑定数据入口
     */
    public abstract void bindData(T t, int position, int selectedP);

    public abstract void bindView(View rootView);

}
