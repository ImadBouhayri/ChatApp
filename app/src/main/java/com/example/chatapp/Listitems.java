package com.example.chatapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Listitems extends ArrayAdapter<User> {
    private ArrayList<User> listUsers;
    private Activity context;
    public Listitems(Activity context, ArrayList<User> listUsers) {
        super(context, R.layout.row_item,listUsers);
        this.context = context;
        this.listUsers = listUsers;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if(convertView==null)
        row = inflater.inflate(R.layout.row_item, null, true);
        TextView name = (TextView) row.findViewById(R.id.name);
        TextView message = (TextView) row.findViewById(R.id.message);
        TextView time = (TextView) row.findViewById(R.id.time);
        ImageView image = (ImageView) row.findViewById(R.id.image);
        String s;
        if(listUsers.get(position).LastMessageDateTime!=null){
            s=listUsers.get(position).LastMessageDateTime.substring(11,16)+listUsers.get(position).LastMessageDateTime.substring(19);
            time.setText(s);
        }
        else  time.setText(listUsers.get(position).LastMessageDateTime);
        name.setText(listUsers.get(position).FullName);
        message.setText(listUsers.get(position).LastMessageSent);
        image.setImageResource(listUsers.get(position).Image);
        return  row;
    }

}

