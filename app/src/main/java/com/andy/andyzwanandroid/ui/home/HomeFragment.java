package com.andy.andyzwanandroid.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.andy.andyzwanandroid.R;
import com.andy.andyzwanandroid.databinding.FragmentHomeBinding;
import com.andy.andyzwanandroid.httpUtils.HttpCallBack;
import com.andy.andyzwanandroid.httpUtils.HttpManager;
import com.andy.andyzwanandroid.httpUtils.HttpParams;
import com.google.gson.Gson;

import java.util.Objects;
import okhttp3.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false);

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        homeViewModel.setText("dsadsadsadsadsadsa");
        binding.setHomeViewModel(homeViewModel);
        binding.setLifecycleOwner(this);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                binding.textHome.setText(s);
            }
        });

        binding.buttonHome.setOnClickListener( (v)->{

        });

        binding.textHome.setOnClickListener((v) -> {
            homeViewModel.setText("21312312");
        });


        HttpManager.getInstance(getActivity()).getHttpRequest("https://www.wanandroid.com/article/list/1/json", new HttpParams(), new HttpCallBack() {
            @Override
            public void onSuccess(String response) {
                try{
                        HomeViewBean homeViewBean = new Gson().fromJson(response,HomeViewBean.class);
                        Log.d("ss","ss");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onSocketTimeout() {

            }
        });
        return binding.getRoot();
    }

}
