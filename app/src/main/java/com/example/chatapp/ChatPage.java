package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatPage extends AppCompatActivity {
TextView id,name;
DbHandler Db;
EditText message;
Button send;
ListView messageview;
ArrayList<Chats> AllChats;
ArrayList<String> AllMessage;
String mess;
ArrayAdapter<String> a;
MyAsyncTask myAsyncTask;
private ChatArrayAdapter chatArrayAdapter;
    private ListView listView;
    int Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);
        Db= new DbHandler(this);
        Intent intent = getIntent();
        String Name = intent.getStringExtra("FullName");
        Id=intent.getIntExtra("Id",0);
        this.setTitle(Name);
        message=(EditText)findViewById(R.id.message);
        messageview=(ListView)findViewById(R.id.messageview);
        send=(Button)findViewById(R.id.send);
        AllChats = Db.GetAllChats(Id);
        AllMessage=new ArrayList<String>();
       if(AllChats.size()!=0)
        {

            for(int i=0;i<AllChats.size();i++)
            {
                AllMessage.add(AllChats.get(i).MessageSent);
            }

           a = new ArrayAdapter<String>(ChatPage.this, android.R.layout.simple_list_item_1,AllMessage);
            messageview.setAdapter(a);

        }

        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.right,AllChats);
        messageview.setAdapter(chatArrayAdapter);

        message.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMessage();
                }
                return false;
            }
        });


        messageview.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        messageview.setAdapter(chatArrayAdapter);

        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                messageview.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        mess=message.getText().toString();
                                        Date now=new Date();
                                        String date = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a").format(now);
                                        Chats chat = new Chats(2000,Id,mess,date);
                                        Db.SendMessage(chat);
                                        sendChatMessage();
                                        Db.Refill(Id,mess,date);
                                        AllMessage.add(chat.MessageSent);
                                        myAsyncTask = new MyAsyncTask(messageview,AllChats,chatArrayAdapter,getApplicationContext());
                                        myAsyncTask.execute(chat);
                                        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.right,AllChats);
                                        chatArrayAdapter.notifyDataSetChanged();
                                        messageview.setAdapter(chatArrayAdapter);
                                        message.setText("");

                                    }
                                }
        );
    }


    private boolean sendChatMessage() {
        Date now=new Date();
        String date = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a").format(now);
        chatArrayAdapter.add(new Chats(2000,Id,message.getText().toString(),date));
        message.setText("");
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backbutton:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}