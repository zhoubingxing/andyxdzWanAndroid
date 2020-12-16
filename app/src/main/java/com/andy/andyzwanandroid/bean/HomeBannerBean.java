package com.andy.andyzwanandroid.bean;

import android.widget.ImageView;
import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.andy.andyzwanandroid.R;
import com.bumptech.glide.Glide;
import java.io.Serializable;

@Entity(tableName = "t_home_banner_data")
public class HomeBannerBean extends BaseObservable implements Serializable {

    @PrimaryKey
    int id;
    String desc;
    int isVisible;
    int order;
    String title;
    String type;
    String url;
    String imagePath;

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
