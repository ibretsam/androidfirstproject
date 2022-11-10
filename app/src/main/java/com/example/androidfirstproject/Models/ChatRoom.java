package com.example.androidfirstproject.Models;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatRoom {
    String user1Phone, user2Phone;
    ArrayList<Message> messageList;
    String lastMessageId;

    public ChatRoom() {
    }

    public ChatRoom(String user1Phone, String user2Phone, ArrayList<Message> messageList, String lastMessageId) {
        this.user1Phone = user1Phone;
        this.user2Phone = user2Phone;
        this.messageList = messageList;
        this.lastMessageId = lastMessageId;
    }

    public String getUser1Phone() {
        return user1Phone;
    }

    public void setUser1Phone(String user1Phone) {
        this.user1Phone = user1Phone;
    }

    public String getUser2Phone() {
        return user2Phone;
    }

    public void setUser2Phone(String user2Phone) {
        this.user2Phone = user2Phone;
    }

    public ArrayList<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(ArrayList<Message> messageList) {
        this.messageList = messageList;
    }

    public String getLastMessageId() {
        return lastMessageId;
    }

    public void setLastMessageId(String lastMessageId) {
        this.lastMessageId = lastMessageId;
    }
}
