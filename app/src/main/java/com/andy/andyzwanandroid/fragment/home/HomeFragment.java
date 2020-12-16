package com.andy.andyzwanandroid.fragment.home;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.andy.andyzwanandroid.R;
import com.andy.andyzwanandroid.activity.HomeWebActivity;
import com.andy.andyzwanandroid.adapter.BannerAdapter;
import com.andy.andyzwanandroid.adapter.BindingAdapter;
import com.andy.andyzwanandroid.bean.HomeInformationBean;
import com.andy.andyzwanandroid.databinding.FragmentHomeBinding;
import com.andy.andyzwanandroid.bean.HomeBannerBean;
import com.andy.andyzwanandroid.fragment.widget.BannerIndicator;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * HomeFragment
 *
 * @author zhouchaoliang 2020/05/28
 */

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private HomeViewModel homeViewModel;

    ScheduledExecutorService scheduledExecutorService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scheduledExecutorService = Executors.newScheduledThreadPool(1);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        //初始化ViewModel
        homeViewModel = new ViewModelProvider(HomeFragment.this).get(HomeViewModel.class);
        binding.setLifecycleOwner(getActivity());
        binding.setHomeTitleData(homeViewModel.getHomeViewData().getValue());
        initView();
        return binding.getRoot();
    }


    private void initView() {
        //监听首页文章数据变化
        homeViewModel.getHomeInformationData().observe(getViewLifecycleOwner(), this::setInformationRecycle);
        //监听banner数据变化
        homeViewModel.getHomeBannerData().observe(getViewLifecycleOwner(),this::setBanner);
        //下拉刷新
        refreshInformationData();
        //同步服务器数据
        homeViewModel.getServiceData();
    }

    /**
     * 更新bannerUI
     */
    public void setBanner(List<HomeBannerBean> data) {
        if (data.isEmpty()) {
            return;
        }
        //设置LayoutManager
        BannerAdapter<HomeBannerBean> bindingAdapter = new BannerAdapter<>(this.getActivity(), data, R.layout.recycler_home_banner_item);
        SmoothLinearLayoutManager layoutManager = new SmoothLinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        bindingAdapter.setOnItemClickListener((value) -> {
            Intent intent = new Intent();
            intent.putExtra("url", data.get(value).getUrl());
            intent.setClass(Objects.requireNonNull(this.getActivity()), HomeWebActivity.class);
            this.getActivity().startActivity(intent);
        });

        //切换到UI线程
        Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
            binding.homeBanner.setLayoutManager(layoutManager);
            binding.homeBanner.setAdapter(bindingAdapter);
            //自动居中
            binding.homeBanner.setOnFlingListener(null);
            LinearSnapHelper snapHelper = new LinearSnapHelper();
            snapHelper.attachToRecyclerView(binding.homeBanner);

            scheduledExecutorService.scheduleAtFixedRate(()
                    -> binding.homeBanner.smoothScrollToPosition(layoutManager.findFirstVisibleItemPosition() + 1), 2000, 5000, TimeUnit.MILLISECONDS);

            //设置轮播图指示红点
            final BannerIndicator bannerIndicator = binding.indicator;
            bannerIndicator.setNumber(data.size());
            binding.homeBanner.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        //得到指示器红点的位置
                        int i = layoutManager.findFirstVisibleItemPosition() % data.size();
                        bannerIndicator.setPosition(i);
                    }
                }
            });
        });
    }

    /**
     * 刷新文章列表UI
     */
    public void setInformationRecycle(List<HomeInformationBean> data) {
        if (data.isEmpty()) {
            return;
        }
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                BindingAdapter<HomeInformationBean> adapter = new BindingAdapter<>(this.getActivity(), data, R.layout.recycler_home_item);
                adapter.setOnItemClickListener((value) -> {
                    Intent intent = new Intent();
                    intent.putExtra("url", data.get(value).getLink());
                    intent.setClass(Objects.requireNonNull(this.getActivity()), HomeWebActivity.class);
                    this.getActivity().startActivity(intent);
                });
                binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                binding.recycler.setAdapter(adapter);
            });
        }
    }

    public void refreshInformationData() {
        binding.refreshInformation.setOnRefreshListener(()-> {
            homeViewModel.refreshHomeInformation();
            binding.refreshInformation.setRefreshing(false);
        });
    }

    /**
     * 设置滚动速度的自定义LayoutManager
     */
    public static class SmoothLinearLayoutManager extends LinearLayoutManager {

        public SmoothLinearLayoutManager(Context context) {
            super(context);
        }

        public SmoothLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
            LinearSmoothScroller linearSmoothScroller =
                    new LinearSmoothScroller(recyclerView.getContext()) {
                        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                            return 0.2f; //返回0.2
                        }
                    };
            linearSmoothScroller.setTargetPosition(position);
            startSmoothScroll(linearSmoothScroller);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        homeViewModel.getHomeBannerData().removeObservers(getViewLifecycleOwner());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scheduledExecutorService.shutdownNow();
    }
}
