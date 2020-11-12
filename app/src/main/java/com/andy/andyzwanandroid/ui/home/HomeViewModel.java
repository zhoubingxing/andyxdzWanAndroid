package com.andy.andyzwanandroid.ui.home;

import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.andy.andyzwanandroid.R;
import com.andy.andyzwanandroid.activity.HomeWebActivity;
import com.andy.andyzwanandroid.adapter.BindingAdapter;
import com.andy.andyzwanandroid.databinding.FragmentHomeBinding;

import java.util.HashMap;
import java.util.Objects;

public class HomeViewModel extends ViewModel {

    private HomeFragment fragment;
    private FragmentHomeBinding binding;
    private HomeViewBean homeViewBean;



    public HomeViewModel(HomeFragment fragment, FragmentHomeBinding binding) {
        this.fragment = fragment;
        this.binding = binding;
        homeViewBean = new HomeViewBean();
        binding.setHomeViewBean(homeViewBean);
        homeViewBean.load((object -> {
            BindingAdapter<HomeRecyclerBean> adapter = new BindingAdapter<>(fragment.getActivity(),homeViewBean.getList(),R.layout.recycler_home_item);
            adapter.setOnItemClickListener((value)-> {
                Intent intent = new Intent();
                intent.putExtra("url",homeViewBean.getList().get(value).getLink());
                intent.setClass(Objects.requireNonNull(fragment.getActivity()), HomeWebActivity.class);
                fragment.getActivity().startActivity(intent);
            });
            fragment.setRecycle(adapter);
        }));
    }




    private MutableLiveData<HomeViewBean> homeViewModelLiveData;

    public MutableLiveData<HomeViewBean> getHomeViewModelLiveData() {
        return homeViewModelLiveData;
    }

    public void setHomeViewModelLiveData(MutableLiveData<HomeViewBean> homeViewModelLiveData) {
        this.homeViewModelLiveData = homeViewModelLiveData;
    }

    public void setData(HomeViewBean.HomeData data) {
        HomeViewBean temp = homeViewModelLiveData.getValue();
        if (temp == null) {
            temp = new HomeViewBean();
        }
        temp.setData(data);
        homeViewModelLiveData.setValue(temp);
    }

    public HomeViewModel() {
        homeViewModelLiveData = new MutableLiveData<>();
        homeViewModelLiveData.setValue(new HomeViewBean());
    }




}