package com.andy.andyzwanandroid.ui.home;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.andy.andyzwanandroid.R;
import com.andy.andyzwanandroid.activity.HomeWebActivity;
import com.andy.andyzwanandroid.activity.MainActivity;
import com.andy.andyzwanandroid.adapter.BannerAdapter;
import com.andy.andyzwanandroid.adapter.BindingAdapter;
import com.andy.andyzwanandroid.application.WanAndroidApplication;
import com.andy.andyzwanandroid.databinding.FragmentHomeBinding;
import com.andy.andyzwanandroid.httpUtils.HttpCallBack;
import com.andy.andyzwanandroid.httpUtils.HttpManager;
import com.andy.andyzwanandroid.httpUtils.HttpParams;
import com.andy.andyzwanandroid.repository.HomeRepository;
import com.andy.andyzwanandroid.ui.widget.BannerIndicator;
import com.bumptech.glide.manager.LifecycleListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.IntConsumer;

import okhttp3.Response;

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
        binding.setHomeViewBean(homeViewModel.getHomeViewModelLiveData().getValue());

        //监听数据变化刷新主页列表
        homeViewModel.getHomeViewModelLiveData().observe(getActivity(),(data)->{
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
                homeViewModel.setData((HomeViewBean.HomeData)data);
            });
        });

        //人造数据 = =
        List<HomeBannerBean> bannerList = new ArrayList<>();
        bannerList.add(new HomeBannerBean("阳光愈发灿烂的初夏的天空下。","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1591245493615&di=196f46fb0d7148c434ca009f2d9b1c0b&imgtype=0&src=http%3A%2F%2Fimg8.zol.com.cn%2Fbbs%2Fupload%2F14883%2F14882592.jpg",""));
        bannerList.add(new HomeBannerBean("夏至未至。","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1605174024888&di=66b9b846041304db59bdef6ffe69f0c0&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2Fa044ad345982b2b75133e15130adcbef77099bf3.jpg",""));
        bannerList.add(new HomeBannerBean("凌冬将至。","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1605178841919&di=cb79bd876f02f2751dad9351ae16aabe&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F84%2F25%2F01300000220758122356250990451.jpg",""));

        //设置LayoutManager
        BannerAdapter<HomeBannerBean> bindingAdapter = new BannerAdapter<>(this.getActivity(), bannerList,R.layout.recycler_home_banner_item);
        SmoothLinearLayoutManager layoutManager = new SmoothLinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        binding.homeBanner.setLayoutManager(layoutManager);
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
