package com.example.sempiternalsearch.reach;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SempiternalSearch on 12/30/2017.
 */

public class MenuClicks extends Service {
    private Context context;
    private PackageManager packageManager = getPackageManager();
    private WindowManager wm;
    private LayoutInflater inflater;
    private ListView lView;
    private RelativeLayout relativeLayout;
    private static ArrayList<AppModel> appModel = new ArrayList<>();
    private static List<ApplicationInfo> packageList;
    private ImageView imageView;

    public void onCreate() {
        super.onCreate();

        //Creates the new view for the window
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 0;


        //Get and assign varibales of layout components
        relativeLayout = (RelativeLayout) inflater.inflate(R.layout.left_menu, null);
        lView = relativeLayout.findViewById(R.id.listView);

        //Get list of installed apps
        packageList = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        //If appModel list is empty populate it with apps, data will be cached for quick redraw
        if (appModel.isEmpty()) {
            for (ApplicationInfo app : packageList) {
                //If the app can be launched then add it list (help avoid unnecessay apps)
                if (packageManager.getLaunchIntentForPackage(app.packageName) != null) {
                    //If its an unusable app
                    if ((app.flags & (ApplicationInfo.FLAG_UPDATED_SYSTEM_APP | ApplicationInfo.FLAG_SYSTEM)) == 1) {
                    }
                    appModel.add(new AppModel((String) packageManager.getApplicationLabel(app), app.packageName, packageManager.getApplicationIcon(app)));
                }
            }
        }
        context = this;
        lView.setAdapter(new AppListAdapter(context.getApplicationContext(), R.layout.left_menu, appModel));
        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                stopService(new Intent(getApplicationContext(), AppListAdapter.class));
                stopService(new Intent(getApplicationContext(), MenuClicks.class));
                stopService(new Intent(getApplicationContext(), SideMenu.class));
                String packageName = appModel.get(i).getName();
                try {
                    Intent intent = packageManager.getLaunchIntentForPackage(packageName);
                    startActivity(intent);
                }
                catch (NullPointerException e){
                    Toast.makeText(getApplicationContext(), "Sorry there is something wrong", Toast.LENGTH_LONG).show();
                }
            }
        });
        imageView = relativeLayout.findViewById(R.id.imageView3);
        imageView.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(getApplicationContext(), MenuClicks.class));
            }
        });
        wm.addView(relativeLayout, params);
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (lView != null)
            wm.removeView(relativeLayout);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Not used
        return null;
    }


}

