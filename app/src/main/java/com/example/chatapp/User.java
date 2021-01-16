package com.example.chatapp;

public class User {
    public int Id;
    public String FullName;
    public int Image;
    public String LastMessageSent;
    public String LastMessageDateTime;
    public User(int Id, String FullName, int Image,String LastMessageSent,String LastMessageDateTime) {
        this.Id = Id;
        this.FullName = FullName;
        this.Image = Image;
        this.LastMessageSent=LastMessageSent;
        this.LastMessageDateTime=LastMessageDateTime;
    }
    public User(){}
}