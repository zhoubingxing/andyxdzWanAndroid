package com.andy.andyzwanandroid.adapter;

import android.content.Context;
import android.os.Build;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.RequiresApi;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;

import com.andy.andyzwanandroid.R;

import java.util.List;
import java.util.function.IntConsumer;

/**
 * DataBinding 基类适配器
 *
 * @author zhouchaoliang 2020/05/28
 */
public class BindingAdapter<T> extends RecyclerAdapter<T, ViewDataBinding> {

    private IntConsumer onItemClickListener;

    public BindingAdapter(Context context, List<T> list, @LayoutRes int layoutId) {
        super(context, list, layoutId);
    }

    public void setOnItemClickListener(IntConsumer onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected void onBind(RecyclerHolder<ViewDataBinding> holder, T t, final int position) {
        holder.binding.setVariable(BR.item, t);
        holder.binding.executePendingBindings();
        if (onItemClickListener != null) {
            holder.itemView.findViewById(R.id.item_view).setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {
                    onItemClickListener.accept(position);
                }
            });
        }
    }
}