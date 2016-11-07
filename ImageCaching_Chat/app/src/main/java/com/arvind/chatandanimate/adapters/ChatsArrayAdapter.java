package com.arvind.chatandanimate.adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arvind.chatandanimate.R;
import com.arvind.chatandanimate.controllers.CacheImageSvc;
import com.arvind.chatandanimate.controllers.ExternalImageBackThreadSvc;
import com.arvind.chatandanimate.models.ChatData;
import com.arvind.chatandanimate.models.MachinatoFont;

import java.util.ArrayList;

public class ChatsArrayAdapter extends ArrayAdapter<ChatData> {
    public CacheImageSvc imgsvc = new CacheImageSvc((5 * 1024 * 1024));
    private AssetManager manage;

    public ChatsArrayAdapter(Context context, ArrayList<ChatData> objects) {
        super(context, 0, objects);
        manage = context.getAssets();
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        ChatData chatData = getItem(position);

        ChatCell chatCell;
        if (convertView == null) {
            chatCell = new ChatCell();
            chatCell.position = position;
            LayoutInflater inflateChat = LayoutInflater.from(getContext());
            convertView = inflateChat.inflate(R.layout.cell_chat, parent, false);

            chatCell.profileImage = (ImageView) convertView.findViewById(R.id.chat_image);
            chatCell.usernameTextView = (TextView) convertView.findViewById(R.id.usernameTextView);
            chatCell.messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);
            convertView.setTag(chatCell);
        } else {
            chatCell = (ChatCell) convertView.getTag();
            chatCell.position = position;
        }

        if (imgsvc.get(chatData != null ? chatData.avatarURL : null) != null) {
            Bitmap temp = imgsvc.get(chatData != null ? chatData.avatarURL : null);
            chatCell.profileImage.setImageBitmap(temp);
        } else {
            ExternalImageBackThreadSvc externalImage = new ExternalImageBackThreadSvc(chatCell);
            externalImage.execute(chatData != null ? chatData.avatarURL : null);
        }

        chatCell.usernameTextView.setText(chatData != null ? chatData.username : null);
        chatCell.usernameTextView.setTypeface(MachinatoFont.getRegular(manage));
        chatCell.messageTextView.setText(chatData != null ? chatData.message : null);
        chatCell.messageTextView.setTypeface(MachinatoFont.getLight(manage));

        return convertView;
    }

    public class ChatCell {
        public CacheImageSvc cache = imgsvc;
        public ImageView profileImage;
        int position;
        TextView usernameTextView;
        TextView messageTextView;
    }
}
