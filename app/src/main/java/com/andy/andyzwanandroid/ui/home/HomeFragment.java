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

    private List<HomeViewBean.HomeDatas> list;

    private FragmentHomeBinding binding;

    private HomeViewModel homeViewModel;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false);

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        homeViewModel.setText("dsadsadsadsadsadsa");
        binding.setHomeViewModel(homeViewModel);
        binding.setLifecycleOwner(this);


        HttpManager.getInstance(getActivity()).getHttpRequest("https://www.wanandroid.com/article/list/1/json", new HttpParams(), new HttpCallBack() {
            @Override
            public void onSuccess(String response) {
                try{

                    homeViewModel.setHomeViewBean(new Gson().fromJson(response,HomeViewBean.class));
                    list = new ArrayList<>(Arrays.asList(homeViewModel.getHomeViewBean().getData().getDatas()));

                    BindingAdapter adapter = new BindingAdapter<HomeViewBean.HomeDatas>(getActivity(), list, R.layout.recycle_home_item);
                    adapter.setOnItemClickListener(new IntConsumer() {
                        @Override
                        public void accept(int i) {
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            Uri content_url = Uri.parse(homeViewModel.getHomeViewBean().getData().getDatas()[i].link);//此处填链接
                            intent.setData(content_url);
                            startActivity(intent);
                            Toast.makeText(getActivity(), i + " - 被点击了", Toast.LENGTH_SHORT).show();
                        }
                    });
                    getActivity().runOnUiThread(()->{

                        binding.recycler.setAdapter(adapter);
                    });

                    } catch (Exception e){
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

        list = new ArrayList<>();

        binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        return binding.getRoot();
    }

}
