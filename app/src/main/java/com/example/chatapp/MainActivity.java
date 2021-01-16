package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DbHandler Db;
    ListView ListUsers;
    ArrayList<User> listUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListUsers=(ListView)findViewById(R.id.Listuser);
        listUsers =new ArrayList<User>();
        Db= new DbHandler(this);
        for (int i = 0; i < 200; i++) {
            User user = new User();
            user.FullName = "Name" + i;
            user.Image = R.drawable.user;
            Db.AddUsers(user);
        }
        listUsers=Db.GetAllUsers();
        Listitems listitems=new Listitems(this,listUsers);
        ListUsers.setAdapter(listitems);
        ListUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this  , ChatPage.class);
                intent.putExtra("Id",listUsers.get(i).Id );
                intent.putExtra("FullName", listUsers.get(i).FullName);
                startActivity(intent);
           }

       });

    }
}