package com.example.admin.login.domain;

/**
 * Created by Admin on 2017/2/15.
 */

public class Message {
    public String getBody() {
        return body;
    }

    public Message setBody(String body) {
        this.body = body;
        return this;
    }

    public String getData() {
        return data;
    }

    public Message setData(String data) {
        this.data = data;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Message setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getType() {
        return type;
    }

    public Message setType(String type) {
        this.type = type;
        return this;
    }


    public Message(String body, String data, String address, String type) {
        this.body = body;
        this.data = data;
        this.address = address;
        this.type = type;
    }

    private String body;
    private String data;
    private String address;
    private String type;

}
