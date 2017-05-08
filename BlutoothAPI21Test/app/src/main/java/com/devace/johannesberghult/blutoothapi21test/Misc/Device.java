package com.devace.johannesberghult.blutoothapi21test.Misc;

/**
 * Created by ketch on 2017-03-29.
 */

public class Device {

    private String address;
    private int rssi=-100;
    private String name;
    private String currentComposition;
    private String userName;

    public Device(String inputName, int inputRssi, String inputAddress) {
        name = inputName;
        rssi = inputRssi;
        address = inputAddress;

    }

    public void setUserName(String name){
        userName = name;
    }

    public String getUserName() {
        return userName;
    }

    public int getRssi() {
        return rssi;
    }

    public String getAddress() {
        return address;
    }

    public String getCurrentComposition() {
        currentComposition = name + " => " + rssi + " dBm  Addr: " + address;
        return currentComposition;
    }
}
