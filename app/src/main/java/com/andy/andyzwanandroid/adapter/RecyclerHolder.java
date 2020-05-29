package com.andy.andyzwanandroid.adapter;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerHolder
 *
 * @author zhouchaoliang 2020/05/28
 */

public class RecyclerHolder<B extends ViewDataBinding> extends RecyclerView.ViewHolder {

    public final B binding;

    public RecyclerHolder(B binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}