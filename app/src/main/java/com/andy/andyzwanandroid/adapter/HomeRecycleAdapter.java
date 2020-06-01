package com.andy.andyzwanandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andy.andyzwanandroid.R;
import com.andy.andyzwanandroid.ui.home.HomeViewBean;

/**
 * RecyclerView 基类列表适配器
 *
 * @author zhouchaoliang 2020/05/28
 */

public class HomeRecycleAdapter extends RecyclerView.Adapter<HomeRecycleAdapter.ViewHolder> {

    private HomeViewBean homeViewBean;

    public HomeRecycleAdapter(HomeViewBean homeViewBean) {
        super();
        this.homeViewBean = homeViewBean;
    }

    @NonNull
    @Override
    public HomeRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_home_item , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecycleAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return homeViewBean == null ? 0 : homeViewBean.getData().getDatas().length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public TextView title;

        public TextView secondTitle;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
//            imageView = itemView.findViewById()
        }
    }
}
