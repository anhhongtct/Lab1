package com.example.fall2019;

public class Message {
    String mess;
    boolean sendOrRec;
    long id;
    public Message(String mes, boolean sR, long id) {
        this.mess = mes;
        this.sendOrRec = sR;
        this.id = id;
    }

    public boolean getSOR (){
        return sendOrRec;
    }

    public String getMessage() {
        return mess;
    }

    public long getId() {return id; }

}