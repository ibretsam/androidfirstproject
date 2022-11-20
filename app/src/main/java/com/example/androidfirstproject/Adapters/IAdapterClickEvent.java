package com.example.androidfirstproject.Adapters;

import com.example.androidfirstproject.Models.User;

import java.util.ArrayList;

public interface IAdapterClickEvent {
    public void onEditClick(ArrayList<String> phoneBookUserId);
    public void onDeleteClick(ArrayList<String> phoneBookUserId);
}

