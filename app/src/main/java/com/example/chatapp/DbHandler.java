package com.example.chatapp;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

public class DbHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ChatApp";
    private static final String TABLE_CHATS = "Chats";
    private static final String TABLE_USERS = "Users";

    public DbHandler(Context c) {
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase Db) {
        String CREATE_USERS_TABLE =
                "CREATE TABLE " + TABLE_USERS + "("+"Id"+" INTEGER PRIMARY KEY autoincrement," +
                        "FullName" + " TEXT," +"Image"+ " INTEGER," + "LastMessageSent" + " TEXT,"+" LastMessageDateTime" + " TEXT"+ ")";
        Db.execSQL(CREATE_USERS_TABLE);
        String CREATE_CHATS_TABLE =
                "CREATE TABLE " + TABLE_CHATS + "("+"Id"+" INTEGER PRIMARY KEY autoincrement," +
                        "IdSender" + " INTEGER,"+
                        "IdReceiver" + " INTEGER," +
                        "MessageSent" + " TEXT,"+
                        "MessageDateTime" + " TEXT"+ ")";
        Db.execSQL(CREATE_CHATS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase Db, int oldVersion, int newVersion) {
        Db.execSQL("DROP TABLE IF EXISTS "+TABLE_CHATS);
        Db.execSQL("DROP TABLE IF EXISTS "+TABLE_USERS);
        // Create tables again
        onCreate(Db);
    }
    void AddUsers(User user)
    {
        SQLiteDatabase Db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        String query = "SELECT * FROM "+TABLE_USERS;
        Cursor cursor = Db.rawQuery(query, null);
        if (cursor != null) {
            if (cursor.getCount() != 200) {
                values.put("FullName", user.FullName);
                values.put("Image", user.Image);
                Db.insert(TABLE_USERS, null, values);
                Db.close();
            } else {
            }
        }
    }
    //Get All Users
    ArrayList<User> GetAllUsers () {
        SQLiteDatabase Db = this .getReadableDatabase ();
        String query="SELECT * FROM "+TABLE_USERS+" ORDER BY LastMessageDateTime DESC" ;
        Cursor cursor = Db.rawQuery (query , null );
        ArrayList<User> users = new ArrayList <User>();
        if( cursor.moveToFirst ()){
            do {
                User getuser = new User( cursor.getInt (0), cursor.getString (1), cursor.getInt (2),cursor.getString (3),cursor.getString (4));
                users.add(getuser);
            }while (cursor.moveToNext ());
        }
        Db.close ();
        return users;
    }
    //Get chats for specific users
    ArrayList<Chats> GetAllChats (int id) {
        SQLiteDatabase Db = this .getReadableDatabase ();
        String query="SELECT * FROM "+ TABLE_CHATS+" WHERE IdSender="+id+" OR IdReceiver="+id  ;
        ArrayList<Chats> chats = new ArrayList <Chats>();
        Cursor cursor = Db.rawQuery (query , null );
        if( cursor.moveToFirst ()){
            do {
                Chats c = new Chats(cursor.getInt (1),
                        cursor.getInt (2), cursor.getString (3),cursor.getString (4));
                chats.add(c);
            }while (cursor.moveToNext ());
        }
        Db.close ();
        return chats;
    }
    //send message from user to another
    void SendMessage(Chats chat)
    {
        System.out.println(chat.IdReceiver);
        SQLiteDatabase Db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("IdSender",chat.IdSender);
        values.put("IdReceiver",chat.IdReceiver);
        values.put("MessageSent",chat.MessageSent);
        values.put("MessageDateTime",chat.MessageDateTime);
        Db.insert(TABLE_CHATS,null,values);
        Db.close();
    }
    //to update the last message sent info for each user
    public void Refill(int Id, String LastMessageSent, String LastMessageDateTime)
    {
        SQLiteDatabase Db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("LastMessageSent",LastMessageSent);
        values.put("LastMessageDateTime",LastMessageDateTime);
        Db.update(TABLE_USERS,values,"Id=?",new String[]{String.valueOf(Id)});
        Db.close();
    }
}