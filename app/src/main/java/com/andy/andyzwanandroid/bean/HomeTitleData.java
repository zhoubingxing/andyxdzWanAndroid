package com.andy.andyzwanandroid.bean;

import androidx.databinding.BaseObservable;

import java.io.Serializable;
import java.util.Calendar;

public class HomeTitleData extends BaseObservable implements Serializable {

    public String day;
    public String month;
    public String title;

    public HomeTitleData() {
        Calendar calendar = Calendar.getInstance();
        this.month = getMonthText(calendar.get(Calendar.MONTH));
        this.day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        this.title = getTitle(calendar.get(Calendar.HOUR_OF_DAY));
    }

    String getTitle(int hours) {
        if (hours < 7) {
            return "深夜了，请注意休息。";
        } else if (hours < 12) {
            return "早上好！";
        } else if (hours < 18) {
            return "AndyWanAndroid";
        } else {
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


