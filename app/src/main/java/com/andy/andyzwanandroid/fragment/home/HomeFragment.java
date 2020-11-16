package com.andy.andyzwanandroid.fragment.home;

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
import com.andy.andyzwanandroid.application.WanAndroidApplication;
import com.andy.andyzwanandroid.databinding.FragmentHomeBinding;
import com.andy.andyzwanandroid.fragment.home.bean.HomeViewBean;
import com.andy.andyzwanandroid.repository.HomeRepository;
import com.andy.andyzwanandroid.fragment.home.bean.HomeBannerBean;
import com.andy.andyzwanandroid.fragment.home.bean.HomeRecyclerBean;
import com.andy.andyzwanandroid.fragment.widget.BannerIndicator;

import java.util.ArrayList;
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false);

        WanAndroidApplication.getRefWatcher().watch(this);
        //初始化ViewModel
        homeViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);

        binding.setLifecycleOwner(getActivity());
        binding.setHomeViewBean(homeViewModel.getHomeViewData().getValue());

        //监听数据变化刷新主页列表
        homeViewModel.getHomeViewData().observe(getActivity(),(data)->{
            BindingAdapter<HomeRecyclerBean> adapter = new BindingAdapter<>(this.getActivity(),data.getList(),R.layout.recycler_home_item);
            adapter.setOnItemClickListener((value)-> {
                Intent intent = new Intent();
                intent.putExtra("url",data.getList().get(value).getLink());
                intent.setClass(Objects.requireNonNull(this.getActivity()), HomeWebActivity.class);
                this.getActivity().startActivity(intent);
            });
            this.setRecycle(adapter);
        });

        //http请求主页数据
        HomeRepository.loadHomeData((data)->{
            getActivity().runOnUiThread(()->{
                homeViewModel.setData((HomeViewBean.HomeNewsData)data);
            });
        });


        //http请求banner数据
        HomeRepository.loadBannerData((data)->{
            getActivity().runOnUiThread(()->{
                List<HomeBannerBean> bannerList = (List<HomeBannerBean>)data;
                if (((List<HomeBannerBean>) data).isEmpty()) {
                    return;
                }
                //设置LayoutManager
                BannerAdapter<HomeBannerBean> bindingAdapter = new BannerAdapter<>(this.getActivity(), bannerList,R.layout.recycler_home_banner_item);
                SmoothLinearLayoutManager layoutManager = new SmoothLinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                binding.homeBanner.setLayoutManager(layoutManager);
                bindingAdapter.setOnItemClickListener((value)->{
                    Intent intent = new Intent();
                    intent.putExtra("url",((List<HomeBannerBean>) data).get(value).getUrl());
                    intent.setClass(Objects.requireNonNull(this.getActivity()), HomeWebActivity.class);
                    this.getActivity().startActivity(intent);
                });
                binding.homeBanner.setAdapter(bindingAdapter);

                //自动居中
                binding.homeBanner.setOnFlingListener(null);
                LinearSnapHelper snapHelper = new LinearSnapHelper();
                snapHelper.attachToRecyclerView(binding.homeBanner);

                //定时任务
                ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
                scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        binding.homeBanner.smoothScrollToPosition(layoutManager.findFirstVisibleItemPosition() + 1);
                    }
                }, 2000, 5000, TimeUnit.MILLISECONDS);

                //设置轮播图指示红点
                final BannerIndicator bannerIndicator = binding.indicator;
                bannerIndicator.setNumber(bannerList.size());
                binding.homeBanner.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            //得到指示器红点的位置
                            int i = layoutManager.findFirstVisibleItemPosition() % bannerList.size();
                            bannerIndicator.setPosition(i);
                        }
                    }
                });

            });
        });



        return binding.getRoot();
    }

    public void setRecycle(BindingAdapter adapter) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(()-> {
                binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                binding.recycler.setAdapter(adapter);

            });
        }
    }

    private void initView() {

    }


    public class SmoothLinearLayoutManager extends LinearLayoutManager {

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

}
