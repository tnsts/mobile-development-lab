package com.example.kosholapsteblevets;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Message {

    public String Name;
    public String Text;

    public Message() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Message(String name, String text) {
        this.Name = name;
        this.Text = text;
    }

}