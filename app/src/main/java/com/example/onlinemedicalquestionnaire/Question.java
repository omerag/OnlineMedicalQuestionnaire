package com.example.onlinemedicalquestionnaire;

public class Question {
    int id,type;
    String text;

    public Question(int id, int type, String text) {
        this.id = id;
        this.type = type;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
