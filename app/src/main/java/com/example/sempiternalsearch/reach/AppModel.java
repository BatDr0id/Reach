package com.example.sempiternalsearch.reach;

import android.graphics.drawable.Drawable;

/**
 * Created by SempiternalSearch on 12/31/2017.
 */

public class AppModel {
    String app, packageName;
    Drawable icon;
    public AppModel(String app, String packageName, Drawable icon){
        this.app = app;
        this.icon = icon;
        this.packageName = packageName;
    }
    public Drawable getIcon(){
        return  icon;
    }
    public String getName() {
        return packageName;
    }
    public String getApp(){
        return app;
    }
}
