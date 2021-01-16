package com.example.chatapp;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MyAsyncTask extends AsyncTask<Chats, Chats, Void> {
    private ListView listview;
    ArrayList<Chats> list;
    DbHandler Db;
    ArrayAdapter a;
    Context context;

    public MyAsyncTask(ListView listView, ArrayList<Chats> l, ArrayAdapter a, Context context) {
        this.context = context;
        this.listview = listView;
        this.a=a;
        this.list=l;
    }
    @Override
    protected Void doInBackground(Chats... params) {

        //0.5 sec
        try {
            Thread.sleep((500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        publishProgress(params[0]);
        return null;
    }
    @Override
    protected void onProgressUpdate(Chats... progress) {
        super.onProgressUpdate();
        Db= new DbHandler(context);
        Chats c=new Chats(progress[0].IdReceiver,progress[0].IdSender,progress[0].MessageSent,progress[0].MessageDateTime);
        Db.SendMessage(c);
        Db.Refill(progress[0].IdReceiver,progress[0].MessageSent,progress[0].MessageDateTime);
        //Update list
        list.add(c);
        a = new ChatArrayAdapter(context, R.layout.right,list);
        a.notifyDataSetChanged();
        listview.setAdapter(a);
    }
}
