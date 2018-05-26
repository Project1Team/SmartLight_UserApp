package com.uit.thonglee.smartlight_userapp.utils;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import com.uit.thonglee.smartlight_userapp.models.Device;
import com.uit.thonglee.smartlight_userapp.models.Home;
import com.uit.thonglee.smartlight_userapp.models.User;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

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

                //Create device object to add list device
                Device device = new Device();
                // set name for device object
                device.setName((String) ((DBObject) object_device).get("name"));
                //set mac Address for device object
                device.setMacAddr((String) ((DBObject) object_device).get("macAddr"));
                //set color for device object
                device.setColor((String) ((DBObject) object_device).get("color"));
                //add device to device list
                devices.add(device);
            }
            // create home object to add list home
            Home home = new Home();
            // set name for home object
            home.setName((String) ((DBObject) object_home).get("name"));
            // set list device for home object
            home.setDevices(devices);

            // add home object to list homes
            homes.add(home);
        }

        // set list home for user object
        user.setHome(homes);
        return user;
    }
}
