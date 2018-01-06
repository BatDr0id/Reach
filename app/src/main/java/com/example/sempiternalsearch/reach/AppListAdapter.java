package com.example.sempiternalsearch.reach;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class AppListAdapter extends ArrayAdapter<AppModel> {
    ArrayList<AppModel> appList;
    Context context;
    int resource;
    private View v;
    LayoutInflater inflater;
    AppModel appModel;

    public AppListAdapter(Context context, int resource, ArrayList<AppModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        appList = objects;
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        v = convertView;
        //Loads data into custom listview item layout
        v = inflater.inflate(R.layout.app_list_adapter, parent, false);
        ImageView image =  v.findViewById(R.id.imageView2);
        TextView applicationName =  v.findViewById(R.id.textView2);
        //Assign textViews in layout names
        image.setImageDrawable(appList.get(position).getIcon());
        applicationName.setText(appList.get(position).getApp());
        return v;

    }

}