package com.uit.thonglee.smartlight_userapp.utils;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import com.uit.thonglee.smartlight_userapp.activities.LoginActivity;
import com.uit.thonglee.smartlight_userapp.models.Device;
import com.uit.thonglee.smartlight_userapp.models.Fire;
import com.uit.thonglee.smartlight_userapp.models.Gas;
import com.uit.thonglee.smartlight_userapp.models.Home;
import com.uit.thonglee.smartlight_userapp.models.Switch;
import com.uit.thonglee.smartlight_userapp.models.SwitchBoard;
import com.uit.thonglee.smartlight_userapp.models.Temperature;
import com.uit.thonglee.smartlight_userapp.models.User;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Templates;

public class UserConverter {
    public static User toUser(DBObject dbObject){
        // initial user object
        User user = new User();
        // create list homes object ro add user
        List<Home> homes = new ArrayList<Home>();

        // set _id for user object
        user.setId((ObjectId) dbObject.get("_id"));
        // set name for user object
        user.setName((String) dbObject.get("name"));
        // set password for user object
        user.setPassword((String) dbObject.get("password"));

        // use DBList of home to query DBObject of home
        BasicDBList basicDBList_home = (BasicDBList) dbObject.get("home");

        // get DBObject of home from DBList of home
        for(Object object_home : basicDBList_home){
            // get and use DBList of deive to query DBObject of device from DBObject of home
            BasicDBList basicDBList_device = (BasicDBList) ((DBObject) object_home).get("device");
            // Create list device object to add homes
            List<Device> devices = new ArrayList<Device>();
            // get DBObject of device from DBList of device
            for(Object object_device: basicDBList_device){
                switch ((String)((DBObject) object_device).get("type")){
                    case "temperature":
                        Temperature temperature = new Temperature((String)((DBObject) object_device).get("name"),
                                (String)((DBObject) object_device).get("type"),
                                (String)((DBObject) object_device).get("value"));
                        devices.add(temperature);
                        break;
                    case "gas":
                        Gas gas = new Gas((String)((DBObject) object_device).get("name"),
                                (String)((DBObject) object_device).get("type"),
                                (String)((DBObject) object_device).get("status"));
                        devices.add(gas);
                        break;
                    case "fire":
                        Fire fire = new Fire((String)((DBObject) object_device).get("name"),
                                (String)((DBObject) object_device).get("type"),
                                (String)((DBObject) object_device).get("status"));
                        devices.add(fire);
                        break;
                    case "switch":
                        BasicDBList basicDBList_switch = (BasicDBList) ((DBObject) object_device).get("switch");
                        List<Switch> switches = new ArrayList<Switch>();
                        for(Object object_switch : basicDBList_switch){
                            boolean turnOn = false;
                            if(((String)((DBObject) object_switch).get("status")).equals("on"))
                                turnOn = true;
                            Switch aSwitch = new Switch((String)((DBObject) object_switch).get("name"), turnOn);
                            switches.add(aSwitch);
                        }
                        SwitchBoard switchBoard = new SwitchBoard((String)((DBObject) object_device).get("name"),
                                (String)((DBObject) object_device).get("type"),
                                (String)((DBObject) object_device).get("index"), switches);
                        devices.add(switchBoard);
                        break;
                }
            }
            // create home object to add list home
            Home home = new Home((String)((DBObject) object_home).get("name"),
                    (String)((DBObject) object_home).get("macAddr"), devices);
            // add home object to list homes
            homes.add(home);
        }
        // set list home for user object
        user.setHomes(homes);
        return user;
    }
//    public static boolean updateColor(String macAddr, String color){
//        for(Home home : LoginActivity.user.getHomes()){
//            for(Device device : home.getDevices()){
//                if(device.getMacAddr().equals(macAddr)){
//                    device.setColor(color);
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//    public static boolean updateBrightness(String macAddr, String brightness){
//        for(Home home : LoginActivity.user.getHomes()){
//            for(Device device : home.getDevices()){
//                if(device.getMacAddr().equals(macAddr)){
//                    device.setBrightness(brightness);
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//    public static boolean updateStatus(String macAddr, String status){
//        for(Home home : LoginActivity.user.getHomes()){
//            for(Device device : home.getDevices()){
//                if(device.getMacAddr().equals(macAddr)){
//                    if (status.equals("on"))
//                        device.setStatus(1);
//                    else
//                        device.setStatus(0);
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

    public static String getTeamperatureValue(String macAddr){
        for(Home home : LoginActivity.user.getHomes()){
            if(home.macAddr.equals(macAddr))
            {
                for (Device device : home.getDevices()){
                    if(device.getType().equals("temperature"))
                    {
                        Temperature temperature = (Temperature) device;
                        return temperature.getValue();
                    }
                }
            }
        }
        return "";
    }

    public static String getFireStatus(String macAddr){
        for(Home home : LoginActivity.user.getHomes()){
            if(home.macAddr.equals(macAddr))
            {
                for (Device device : home.getDevices()){
                    if(device.getType().equals("fire"))
                    {
                        Fire fire = (Fire) device;
                        return fire.getStatus();
                    }
                }
            }
        }
        return "";
    }

    public static String getGasStatus(String macAddr){
        for(Home home : LoginActivity.user.getHomes()){
            if(home.macAddr.equals(macAddr))
            {
                for (Device device : home.getDevices()){
                    if(device.getType().equals("gas"))
                    {
                        Gas gas = (Gas) device;
                        return gas.getStatus();
                    }
                }
            }
        }
        return "";
    }

    public static String getNameSwitch(String macAddr, String indexBoard, int indexSwitch){
        for(Home home : LoginActivity.user.getHomes()){
            if(home.macAddr.equals(macAddr))
            {
                for (Device device : home.getDevices()){
                    if(device.getType().equals("switch"))
                    {
                        SwitchBoard switchBoard = (SwitchBoard) device;
                        if(switchBoard.getIndex().equals(indexBoard))
                            return switchBoard.getSwitchs().get(indexSwitch).getName();
                    }
                }
            }
        }
        return "";
    }

    public static boolean getStatusSwitch(String macAddr, String indexBoard, int indexSwitch){
        for(Home home : LoginActivity.user.getHomes()){
            if(home.macAddr.equals(macAddr))
            {
                for (Device device : home.getDevices()){
                    if(device.getType().equals("switch"))
                    {
                        SwitchBoard switchBoard = (SwitchBoard) device;
                        if(switchBoard.getIndex().equals(indexBoard))
                            return switchBoard.getSwitchs().get(indexSwitch).isStatus();
                    }
                }
            }
        }
        return false;
    }
}
