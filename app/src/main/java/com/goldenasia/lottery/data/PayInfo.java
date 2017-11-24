package com.goldenasia.lottery.data;

/**
 * Created by ACE-PC on 2017/5/12.
 */

public class PayInfo {
    private String username;
    private int user_id;

    public PayInfo(){
    }

    public PayInfo(String username,int user_id){
        this.username=username;
        this.user_id=user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
