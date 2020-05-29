package com.andy.andyzwanandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * RecyclerView 基类列表适配器
 *
 * @author zhouchaoliang 2020/05/28
 */
public abstract class RecyclerAdapter<T, B extends ViewDataBinding> extends RecyclerView.Adapter<RecyclerHolder<B>> {

    protected Context mContext;
    protected List<T> mList;
    private int layoutId;

    public RecyclerAdapter(Context context, List<T> list, @LayoutRes int layoutId) {
        this.mContext = context;
        this.mList = list;
        this.layoutId = layoutId;
    }

    @Override
    public RecyclerHolder<B> onCreateViewHolder(ViewGroup parent, int viewType) {
        B bing = DataBindingUtil.inflate(LayoutInflater.from(mContext), layoutId, parent, false);
        return new RecyclerHolder<>(bing);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder<B> holder, int position) {
        final T t = mList.get(position);
        onBind(holder, t, position);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    protected abstract void onBind(RecyclerHolder<B> holder, T t, int position);

}