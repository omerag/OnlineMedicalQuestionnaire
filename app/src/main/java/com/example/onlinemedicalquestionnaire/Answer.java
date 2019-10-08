package com.example.onlinemedicalquestionnaire;

public class Answer {

    int type,result;
    String id;

    public Answer(String id, int type, int res) {
        this.id = id;
        this.type = type;
        result = res;

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

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
