package com.andy.andyzwanandroid.fragment.home.bean;

import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;

import com.andy.andyzwanandroid.R;
import com.bumptech.glide.Glide;

import java.io.Serializable;



public class HomeBannerBean extends BaseObservable implements Serializable {

    String desc;
    int id;
    int isVisible;
    int order;
    String title;
    String type;
    String url;
    String imagePath;

    public HomeBannerBean() {
    }

    public HomeBannerBean(String desc, int id, int isVisible, int order, String title, String type, String url, String imagePath) {
        this.desc = desc;
        this.id = id;
        this.isVisible = isVisible;
        this.order = order;
        this.title = title;
        this.type = type;
        this.url = url;
        this.imagePath = imagePath;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    @BindingAdapter("imgPath")
    public static void getImage(ImageView imageView, String imagePath) {
        Glide.with(imageView.getContext()).load(imagePath)
                .placeholder(R.drawable.loading_image)
                .into(imageView);
    }


}
