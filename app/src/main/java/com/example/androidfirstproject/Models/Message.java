package com.example.androidfirstproject.Models;

public class Message {
    User sender, receipient;
    String content;
    String time;
    ChatRoom chatRoom;
    int isMyMessage;

    public Message() {
    }

    public Message(User sender, User receipient, String content, String time, ChatRoom chatRoom) {
        this.sender = sender;
        this.receipient = receipient;
        this.content = content;
        this.time = time;
        this.chatRoom = chatRoom;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceipient() {
        return receipient;
    }

    public void setReceipient(User receipient) {
        this.receipient = receipient;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }
}
