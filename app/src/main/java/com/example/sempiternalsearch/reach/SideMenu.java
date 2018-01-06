package com.example.sempiternalsearch.reach;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SideMenu extends Service {
    private WindowManager windowManager;
    private ListView lView;
    private RelativeLayout menuLeft;
    private ImageView imageView;

    private final String[] defaultList = new String[]{"Overlays", "Quick Access", "Settings", "Designs", "Gesture"};
    private LayoutInflater inflater;

    @Override
    public IBinder onBind(Intent intent) {
        // Not used
        return null;
    }

    Context context;

    @Override
    public void onCreate() {
        context = this.getBaseContext();
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
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
        menuLeft = (RelativeLayout) inflater.inflate(R.layout.left_menu, null);
        lView = menuLeft.findViewById(R.id.listView);
        imageView = (ImageView) menuLeft.findViewById(R.id.imageView3);
        ArrayAdapter<String> ad = new ArrayAdapter<String>(this, R.layout.left_menu_list_adapter, defaultList);
        lView.setAdapter(ad);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(getApplicationContext(), SideMenu.class));
            }
        });
        menuLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(getApplicationContext(), SideMenu.class));
            }
        });
        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                if (lView.getItemAtPosition(i) == "Quick Access") {
                    Intent intent = new Intent(context, MenuClicks.class);
                    stopSelf();
                    startService(intent);
                    Toast.makeText(getApplicationContext(), "Hello you selected quick access", Toast.LENGTH_LONG).show();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
            }
        });

        windowManager.addView(menuLeft, params);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (menuLeft != null) windowManager.removeView(menuLeft);
    }
}