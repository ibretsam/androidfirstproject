package com.example.androidfirstproject.Models;

public class ChatRoom {
    int id;
    User user1, user2;
    Message message;

    public ChatRoom() {
    }

    public ChatRoom(int id, User user1, User user2, Message message) {
        this.id = id;
        this.user1 = user1;
        this.user2 = user2;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
