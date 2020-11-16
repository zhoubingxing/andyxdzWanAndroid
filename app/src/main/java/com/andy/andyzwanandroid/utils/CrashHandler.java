package com.andy.andyzwanandroid.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.NonNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    private static final String FILE_NAME_SUFFIX = ".trace";
    private static final CrashHandler sInstance = new CrashHandler();
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private Context mContext;

    private CrashHandler()
    {
    }

    public static CrashHandler getInstance()
    {
        return sInstance;
    }

    public void init(@NonNull Context context)
    {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
    }

    /**
     * 当程序中有未被捕获的异常，系统将会调用这个方法
     *
     * @param t 出现未捕获异常的线程
     * @param e 得到异常信息
     */
    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
        try {
            //保存到本地
            exportExceptionToSDCard(e);
        } catch (Exception e1){
            e1.printStackTrace();
        }
    }

    /**
     * 导出异常信息到SD卡
     *
     * @param e
     */
    private void exportExceptionToSDCard(@NonNull Throwable e)
    {
        //判断SD卡是否存在
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            return;
        }

        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        File file = new File(PATH + File.separator + time + FILE_NAME_SUFFIX);

        try
        {
            //往文件中写入数据
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            pw.println(time);
            pw.println(appendPhoneInfo());
            e.printStackTrace(pw);
            pw.close();
        } catch (IOException | PackageManager.NameNotFoundException e1)
        {
            e1.printStackTrace();
        }
    }

    /**
     * 获取手机信息
     */
    private String appendPhoneInfo() throws PackageManager.NameNotFoundException
    {
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        StringBuilder sb = new StringBuilder();
        //App版本
        sb.append("App Version: ");
        sb.append(pi.versionName);
        sb.append("_");
        sb.append(pi.versionCode).append("\n");

        //Android版本号
        sb.append("OS Version: ");
        sb.append(Build.VERSION.RELEASE);
        sb.append("_");
        sb.append(Build.VERSION.SDK_INT).append("\n");

        //手机制造商
        sb.append("Vendor: ");
        sb.append(Build.MANUFACTURER).append("\n");

        //手机型号
        sb.append("Model: ");
        sb.append(Build.MODEL).append("\n");

        //CPU架构
        sb.append("CPU: ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            sb.append(Arrays.toString(Build.SUPPORTED_ABIS));
        } else
        {
            sb.append(Build.CPU_ABI);
        }
        return sb.toString();
    }
}
