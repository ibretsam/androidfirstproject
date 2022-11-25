package com.example.androidfirstproject.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ChatRoom implements Serializable {
    String user1Phone, user2Phone, id;
    ArrayList<String> messageList ;
    String lastMessageId;
    ArrayList<String> userPhoneNumber ;

    public ChatRoom() {
    }

    public ChatRoom(String user1Phone, String user2Phone, ArrayList<String> messageList, String lastMessageId,ArrayList<String> userPhoneNumber) {
        this.user1Phone = user1Phone;
        this.user2Phone = user2Phone;
        this.messageList = messageList;
        this.lastMessageId = lastMessageId;
        this.userPhoneNumber= userPhoneNumber;
    }
    public ChatRoom( ArrayList<String> messageList) {
        this.messageList = messageList;

    }

    public ArrayList<String> getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(ArrayList<String> userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public ArrayList<String> getMessageList() {
        return messageList;
    }

    public void setMessageList(ArrayList<String> messageList) {
        this.messageList = messageList;
    }

    public String getLastMessageId() {
        return lastMessageId;
    }

    public void setLastMessageId(String lastMessageId) {
        this.lastMessageId = lastMessageId;
    }
}
