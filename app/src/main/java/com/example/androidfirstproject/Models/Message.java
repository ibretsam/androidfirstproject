package com.example.androidfirstproject.Models;

public class Message {
    String id;
    String content;
    String time;
    String chatRoomId;
    String phoneUser2;
    String isLastMessage;

    public Message() {
    }

    public String getPhoneUser2() {
        return phoneUser2;
    }

    public void setPhoneUser2(String phoneUser2) {
        this.phoneUser2 = phoneUser2;
    }

    public Message(String content, String time, String chatRoomId, String phoneUser2, String isLastMessage) {
        this.content = content;
        this.time = time;
        this.chatRoomId = chatRoomId;
        this.phoneUser2 = phoneUser2;
        this.isLastMessage = isLastMessage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getIsLastMessage() {
        return isLastMessage;
    }

    public void setIsLastMessage(String isLastMessage) {
        this.isLastMessage = isLastMessage;
    }
}
