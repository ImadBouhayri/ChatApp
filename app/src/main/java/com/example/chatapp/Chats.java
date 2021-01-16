package com.example.chatapp;

public class Chats {
    public int IdSender;
    public int IdReceiver;
    public String MessageSent;
    public String MessageDateTime;

    public Chats(int IdSender,int IdReceiver,String MessageSent,String MessageDateTime){
        this.IdSender = IdSender;
        this.IdReceiver = IdReceiver;
        this.MessageSent = MessageSent;
        this.MessageDateTime=MessageDateTime;
    }
}
