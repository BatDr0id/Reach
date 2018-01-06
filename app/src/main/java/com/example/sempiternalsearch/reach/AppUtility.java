package com.example.sempiternalsearch.reach;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SempiternalSearch on 12/31/2017.
 */

public class AppUtility {

    Application application;
    private PackageManager packageManager;
    private List<PackageInfo> packageList;
    PackageInfo packageInfo;

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(PackageInfo packageInfo) {
        this.packageInfo = packageInfo;
    }

    public List<PackageInfo> getInstalledApps () {
        return packageList;
    }


}
