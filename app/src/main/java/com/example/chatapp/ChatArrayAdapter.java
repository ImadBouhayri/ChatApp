package com.example.chatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class ChatArrayAdapter extends ArrayAdapter<Chats> {

    private TextView chatText;
    private ArrayList<Chats> chatMessageList = new ArrayList<Chats>();
    private Context context;

    @Override
    public void add(Chats object) {
        chatMessageList.add(object);
        super.add(object);
    }

    public ChatArrayAdapter(Context context, int textViewResourceId,ArrayList<Chats> chatMessageList ) {
        super(context, textViewResourceId);
        this.context = context;
        this.chatMessageList=chatMessageList;
    }

    public int getCount() {
        return this.chatMessageList.size();
    }

    public Chats getItem(int index) {
        return this.chatMessageList.get(index);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Chats chatMessageObj = getItem(position);
        View row = convertView;
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (chatMessageObj.IdSender==2000) {
            row = inflater.inflate(R.layout.right, parent, false);
        }else{
            row = inflater.inflate(R.layout.left, parent, false);
        }
        chatText = (TextView) row.findViewById(R.id.msgr);
        chatText.setText(chatMessageObj.MessageSent);
        return row;
    }
}