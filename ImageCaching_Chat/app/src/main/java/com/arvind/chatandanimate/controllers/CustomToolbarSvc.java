package com.arvind.chatandanimate.controllers;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.arvind.chatandanimate.R;
import com.arvind.chatandanimate.models.MachinatoFont;

public class CustomToolbarSvc {
    private AppCompatActivity activity = null;
    private String title = null;

    public CustomToolbarSvc(AppCompatActivity activity, String title) {
        this.activity = activity;
        this.title = title;
    }

    public void activateToolbar() {
        TextView tv_chat = (TextView) activity.findViewById(R.id.tv_chat);
        AssetManager manage = activity.getApplicationContext().getAssets();
        tv_chat.setTypeface(MachinatoFont.getExtraLight(manage));
        tv_chat.setText(title);
    }
}
