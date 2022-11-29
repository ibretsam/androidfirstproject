package com.example.androidfirstproject.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class ChatRoom implements Serializable {
    String id;
    ArrayList<String> messageList ;
    String lastMessageId,nameUser2;
    ArrayList<String> userPhoneNumber ;

    public ChatRoom() {
    }

    public ChatRoom(String id, ArrayList<String> messageList, String lastMessageId, String nameUser2, ArrayList<String> userPhoneNumber) {
        this.id = id;
        this.messageList = messageList;
        this.lastMessageId = lastMessageId;
        this.nameUser2= nameUser2;
        this.userPhoneNumber= userPhoneNumber;
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

    public String getNameUser2() {
        return nameUser2;
    }

    public void setNameUser2(String nameUser2) {
        this.nameUser2 = nameUser2;
    }
}
