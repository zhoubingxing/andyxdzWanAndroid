package com.andy.andyzwanandroid.ui.home;

import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.andy.andyzwanandroid.R;
import com.andy.andyzwanandroid.activity.HomeWebActivity;
import com.andy.andyzwanandroid.adapter.BindingAdapter;
import com.andy.andyzwanandroid.databinding.FragmentHomeBinding;

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
            BindingAdapter<HomeViewBean.HomeDatas> adapter = new BindingAdapter<>(fragment.getActivity(),homeViewBean.getList(),R.layout.recycler_home_item);
            adapter.setOnItemClickListener((value)-> {
                Intent intent = new Intent();
                intent.putExtra("url",homeViewBean.getList().get(value).getLink());
                intent.setClass(fragment.getActivity(), HomeWebActivity.class);
                fragment.getActivity().startActivity(intent);
//                homeViewBean.getList().get(value).setTitle("sadsadsadsadsad");
//                adapter.notifyDataSetChanged();
            });
            fragment.setRecycle(adapter);
        }));
    }




    //liveData样式
    private MutableLiveData<String> mText;
    public LiveData<String> getText() {
        return mText;
    }
    public void setText(String mText) {
        this.mText.setValue(mText);
    }
    //end

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }




}