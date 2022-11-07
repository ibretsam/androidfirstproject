package com.example.androidfirstproject.Models;

public class Message {
    String id;
    User sender, receipient;
    String content;
    String time;
    ChatRoom chatRoom;
    int isMyMessage;

    public Message() {
    }

    public Message(String id, User sender, User receipient, String content, String time, ChatRoom chatRoom, int isMyMessage) {
        this.id = id;
        this.sender = sender;
        this.receipient = receipient;
        this.content = content;
        this.time = time;
        this.chatRoom = chatRoom;
        this.isMyMessage = isMyMessage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsMyMessage() {
        return isMyMessage;
    }

    public void setIsMyMessage(int isMyMessage) {
        this.isMyMessage = isMyMessage;
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
