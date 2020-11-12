package com.andy.andyzwanandroid.ui.home;

import android.app.ActivityManager;
import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;

import com.andy.andyzwanandroid.R;
import com.bumptech.glide.Glide;

import java.io.Serializable;

public class HomeBannerBean extends BaseObservable implements Serializable {

    String title;
    String imageUrl;
    String link;

    public HomeBannerBean(String title, String imageUrl, String link) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.link = link;

    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @BindingAdapter("imgPath")
    public static void getImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url)
                .placeholder(R.drawable.loading_image)
                .into(imageView);
    }


}
