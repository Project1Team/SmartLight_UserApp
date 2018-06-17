package com.uit.thonglee.smartlight_userapp.utils;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import com.uit.thonglee.smartlight_userapp.activities.LoginActivity;
import com.uit.thonglee.smartlight_userapp.models.Device;
import com.uit.thonglee.smartlight_userapp.models.Room;
import com.uit.thonglee.smartlight_userapp.models.User;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class UserConverter {
    public static User toUser(DBObject dbObject){
        // initial user object
        User user = new User();
        // create list homes object ro add user
        List<Room> rooms = new ArrayList<Room>();

        // set _id for user object
        user.setId((ObjectId) dbObject.get("_id"));
        // set name for user object
        user.setName((String) dbObject.get("name"));
        // set password for user object
        user.setPassword((String) dbObject.get("password"));

        // use DBList of home to query DBObject of home
        BasicDBList basicDBList_room = (BasicDBList) dbObject.get("room");

        // get DBObject of home from DBList of home
        for(Object object_room : basicDBList_room){
            // get and use DBList of deive to query DBObject of device from DBObject of home
            BasicDBList basicDBList_device = (BasicDBList) ((DBObject) object_room).get("device");
            // Create list device object to add homes
            List<Device> devices = new ArrayList<Device>();
            // get DBObject of device from DBList of device
            for(Object object_device: basicDBList_device){

                //Create device object to add list device
                Device device = new Device();
                // set name for device object
                device.setName((String) ((DBObject) object_device).get("name"));
                //set mac Address for device object
                device.setMacAddr((String) ((DBObject) object_device).get("macAddr"));
                //set color for device object
                device.setColor((String) ((DBObject) object_device).get("color"));
                device.setBrightness((String) ((DBObject) object_device).get("brightness"));
                //set status of light (0: off, 1: on)
                if (((DBObject) object_device).get("status").equals("on"))
                    device.setStatus(1);
                else
                    device.setStatus(0);
                //add device to device list
                devices.add(device);
            }
            // create home object to add list home
            Room room = new Room();
            // set name for home object
            room.setName((String) ((DBObject) object_room).get("name"));
            // set list device for home object
            room.setDevices(devices);

            // add home object to list homes
            rooms.add(room);
        }

        // set list home for user object
        user.setRooms(rooms);
        return user;
    }
    public static boolean updateColor(String macAddr, String color){
        for(Room room : LoginActivity.user.getRooms()){
            for(Device device : room.getDevices()){
                if(device.getMacAddr().equals(macAddr)){
                    device.setColor(color);
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean updateBrightness(String macAddr, String brightness){
        for(Room room : LoginActivity.user.getRooms()){
            for(Device device : room.getDevices()){
                if(device.getMacAddr().equals(macAddr)){
                    device.setColor(brightness);
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean updateStatus(String macAddr, String status){
        for(Room room : LoginActivity.user.getRooms()){
            for(Device device : room.getDevices()){
                if(device.getMacAddr().equals(macAddr)){
                    if (status.equals("on"))
                        device.setStatus(1);
                    else
                        device.setStatus(0);
                    return true;
                }
            }
        }
        return false;
    }
}
