package com.example.remocar.VE;

import org.json.JSONException;
import org.json.JSONObject;

public class CarStatus {
    private int dir;
    private int status;
    private int speed;

    public CarStatus(int dir, int status, int speed){
        this.dir = dir;
        this.status = status;
        this.speed = speed;
    }

    public CarStatus(CarStatus carStatus){
        this.dir = carStatus.getDir();
        this.status = carStatus.getStatus();
        this.speed = carStatus.getSpeed();
    }

    public int getDir() {
        return dir;
    }

    public int getSpeed() {
        return speed;
    }

    public int getStatus() {
        return status;
    }

    public void addDir() {
        this.dir++;
    }

    public void minusDir() {
        this.dir--;
    }

    public void addStatus() {
        this.status++;
    }

    public void minusStatus() {
        this.status--;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void updateStatus(CarStatus carStatus){
        this.dir = carStatus.getDir();
        this.status = carStatus.getStatus();
        this.speed = carStatus.getSpeed();
    }

    public boolean sameStatus(CarStatus carStatus){
        return this.dir == carStatus.getDir()
                && this.status == carStatus.getStatus()
                && this.speed == carStatus.getSpeed();
    }

    public String strMsg(){
        return "dir:"+dir+"; stts:"+status+"; spd:"+speed;
    }

    public JSONObject jsonMsg() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("dir", dir);
        json.put("stts", status);
        json.put("spd",speed);
        return json;
    }
}
