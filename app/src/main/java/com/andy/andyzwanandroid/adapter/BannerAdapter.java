package com.andy.andyzwanandroid.adapter;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.RequiresApi;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;

import com.andy.andyzwanandroid.R;

import java.util.List;
import java.util.function.IntConsumer;

/**
 * BannerAdapter
 *
 * @author zhouchaoliang 2020/11/12
 */
public class BannerAdapter<T> extends RecyclerAdapter<T, ViewDataBinding> {

    private IntConsumer onItemClickListener;

    public BannerAdapter(Context context, List<T> list, @LayoutRes int layoutId) {
        super(context, list, layoutId);
    }

    public void setOnItemClickListener(IntConsumer onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerHolder<ViewDataBinding> holder, int position) {
        final T t = mList.get(position % mList.size());
        onBind(holder, t, position);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : Integer.MAX_VALUE;
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