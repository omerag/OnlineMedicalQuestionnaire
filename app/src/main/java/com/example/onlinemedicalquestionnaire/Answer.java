package com.example.onlinemedicalquestionnaire;

public class Answer {

    int id,type,result;

    public Answer(int id, int type, int res) {
        this.id = id;
        this.type = type;
        result = res;

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

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
