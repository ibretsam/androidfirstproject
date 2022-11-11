package com.example.androidfirstproject.Models;

public class Message {
    String id;
    String content;
    String time;
    String chatRoomId;
    int isMyMessage;
    int isLastMessage;

    public Message() {
    }

    public Message(String id, String content, String time, String chatRoomId, int isMyMessage, int isLastMessage) {
        this.id = id;
        this.content = content;
        this.time = time;
        this.chatRoomId = chatRoomId;
        this.isMyMessage = isMyMessage;
        this.isLastMessage = isLastMessage;
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

    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public int getIsLastMessage() {
        return isLastMessage;
    }

    public void setIsLastMessage(int isLastMessage) {
        this.isLastMessage = isLastMessage;
    }
}
