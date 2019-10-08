package com.example.onlinemedicalquestionnaire;

public class Question {
    int type,min,max;
    String text, id;

    public Question(String id, int type, String text) {
        this.id = id;
        this.type = type;
        this.text = text;
    }

    public Question(int type, int min, int max, String text, String id) {
        this.type = type;
        this.min = min;
        this.max = max;
        this.text = text;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
