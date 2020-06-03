package com.andy.andyzwanandroid.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.databinding.BaseObservable;

import com.andy.andyzwanandroid.BR;
import com.andy.andyzwanandroid.R;
import com.andy.andyzwanandroid.adapter.BindingAdapter;
import com.andy.andyzwanandroid.application.WanAndroidApplication;
import com.andy.andyzwanandroid.httpUtils.HttpCallBack;
import com.andy.andyzwanandroid.httpUtils.HttpManager;
import com.andy.andyzwanandroid.httpUtils.HttpParams;
import com.andy.andyzwanandroid.utils.WanCallback;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.IntConsumer;

public class HomeViewBean extends BaseObservable implements Serializable {

    private HomeData data;
    private List<HomeDatas> list;
    private DateText dateText;

    HomeViewBean() {
        data = new HomeData();
        list = new ArrayList<>();
        dateText = new DateText();
    }

    public void load(WanCallback callback) {
        HttpManager.getInstance(WanAndroidApplication.getInstance()).getHttpRequest("https://www.wanandroid.com/article/list/1/json", new HttpParams(), new HttpCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(String response) {
                try{
                    setData(new Gson().fromJson(response,HomeViewBean.class).getData());
                    setList(new ArrayList<>(Arrays.asList(getData().getDatas())));
                    callback.callback(null);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure() {
                Log.d("OKhttp","onFailure");
            }

            @Override
            public void onSocketTimeout() {
                Log.d("OKhttp","onSocketTimeout");
            }
        });
    }

    public DateText getDateText() {
        return dateText;
    }

    public void setDateText(DateText dateText) {
        this.dateText = dateText;
    }

    public HomeData getData() {
        return data;
    }

    public void setData(HomeData data) {
        this.data = data;
    }

    public List<HomeDatas> getList() {
        return list;
    }

    public void setList(List<HomeDatas> list) {
        this.list = list;
    }

    public class DateText {

        public String day;
        public String month;
        public String title;

        DateText() {
            Calendar calendar = Calendar.getInstance();
            this.month = getMonthText(calendar.get(Calendar.MONTH));
            this.day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
            this.title = getTitle(calendar.get(Calendar.HOUR_OF_DAY));
        }

        String getTitle(int hours) {
            if(hours < 7) {
                return "深夜了，请注意休息。";
            } else if (hours < 12){
                return "早上好！";
            }  else if (hours < 18){
                return "AndyWanAndroid";
            }  else {
                return "晚上好！";
            }
        }

        String getMonthText(int month) {
            switch (month + 1) {
                case 1:
                    return "一月";
                case 2:
                    return "二月";
                case 3:
                    return "三月";
                case 4:
                    return "四月";
                case 5:
                    return "五月";
                case 6:
                    return "六月";
                case 7:
                    return "七月";
                case 8:
                    return "八月";
                case 9:
                    return "九月";
                case 10:
                    return "十月";
                case 11:
                    return "十一月";
                case 12:
                    return "十二月";
            }
            return "神奇之月";
        }
    }
    public class HomeData extends BaseObservable implements Serializable {

        //页码
        int curPage;

        HomeDatas[] datas;

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public HomeDatas[] getDatas() {
            return datas;
        }

        public void setDatas(HomeDatas[] datas) {
            this.datas = datas;
        }
    }

    public class HomeDatas extends BaseObservable implements Serializable {

        String apkLink;
        int audit;
        String author;
        boolean canEdit;
        int chapterId;
        String chapterName;
        boolean collect;
        int courseId;
        String desc;
        String descMd;
        String envelopePic;
        boolean fresh;
        int id;
        //url
        String link;
        String niceDate;
        String niceShareDate;
        String origin;
        String prefix;
        String projectLink;
        long publishTime;
        int selfVisible;
        long shareDate;
        //作者
        String shareUser;
        int superChapterId;
        //副标题
        String superChapterName;
        //文章标题
        String title;
        int userId;
        int visible;
        int zan;

        public String getApkLink() {
            return apkLink;
        }

        public void setApkLink(String apkLink) {
            this.apkLink = apkLink;
        }

        public int getAudit() {
            return audit;
        }

        public void setAudit(int audit) {
            this.audit = audit;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public boolean isCanEdit() {
            return canEdit;
        }

        public void setCanEdit(boolean canEdit) {
            this.canEdit = canEdit;
        }

        public int getChapterId() {
            return chapterId;
        }

        public void setChapterId(int chapterId) {
            this.chapterId = chapterId;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        public boolean isCollect() {
            return collect;
        }

        public void setCollect(boolean collect) {
            this.collect = collect;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getDescMd() {
            return descMd;
        }

        public void setDescMd(String descMd) {
            this.descMd = descMd;
        }

        public String getEnvelopePic() {
            return envelopePic;
        }

        public void setEnvelopePic(String envelopePic) {
            this.envelopePic = envelopePic;
        }

        public boolean isFresh() {
            return fresh;
        }

        public void setFresh(boolean fresh) {
            this.fresh = fresh;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getNiceDate() {
            return niceDate;
        }

        public void setNiceDate(String niceDate) {
            this.niceDate = niceDate;
        }

        public String getNiceShareDate() {
            return niceShareDate;
        }

        public void setNiceShareDate(String niceShareDate) {
            this.niceShareDate = niceShareDate;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getProjectLink() {
            return projectLink;
        }

        public void setProjectLink(String projectLink) {
            this.projectLink = projectLink;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public long getShareDate() {
            return shareDate;
        }

        public void setShareDate(long shareDate) {
            this.shareDate = shareDate;
        }

        public int getSelfVisible() {
            return selfVisible;
        }

        public void setSelfVisible(int selfVisible) {
            this.selfVisible = selfVisible;
        }


        public String getShareUser() {
            return shareUser;
        }

        public void setShareUser(String shareUser) {
            this.shareUser = shareUser;
        }

        public int getSuperChapterId() {
            return superChapterId;
        }

        public void setSuperChapterId(int superChapterId) {
            this.superChapterId = superChapterId;
        }

        public String getSuperChapterName() {
            return superChapterName;
        }

        public void setSuperChapterName(String superChapterName) {
            this.superChapterName = superChapterName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        public int getZan() {
            return zan;
        }

        public void setZan(int zan) {
            this.zan = zan;
        }
    }
}


