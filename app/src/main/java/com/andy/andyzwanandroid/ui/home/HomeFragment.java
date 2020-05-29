package com.andy.andyzwanandroid.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.andy.andyzwanandroid.R;
import com.andy.andyzwanandroid.activity.MainActivity;
import com.andy.andyzwanandroid.adapter.BindingAdapter;
import com.andy.andyzwanandroid.databinding.FragmentHomeBinding;
import com.andy.andyzwanandroid.httpUtils.HttpCallBack;
import com.andy.andyzwanandroid.httpUtils.HttpManager;
import com.andy.andyzwanandroid.httpUtils.HttpParams;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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

        homeViewModel = new HomeViewModel(getActivity(),binding);
        binding.setHomeViewModel(homeViewModel);
        binding.setLifecycleOwner(this);




        binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        return binding.getRoot();
    }

    public void setRecycle(BindingAdapter adapter) {

    }

    private void initView() {

    }

}
