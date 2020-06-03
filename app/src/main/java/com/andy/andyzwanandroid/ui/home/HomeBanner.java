package com.andy.andyzwanandroid.ui.home;

import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;

import com.andy.andyzwanandroid.R;

import java.io.Serializable;

public class HomeBanner extends BaseObservable implements Serializable {

    String title;
    String imageUrl;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @BindingAdapter("imgPath")
    public static void getImageUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url)
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);
    }
}
