package com.softvilla.slmparentportal;

/**
 * Created by Malik on 01/08/2017.
 */

public class ChildrenInfo {

    public String name;
    // public String description;
    public int imageId;

    String userName,img,id,promId;
    int text;

    ChildrenInfo(String title, int imageId) {
        this.name = title;
        //this.description = description;
        this.imageId = imageId;
    }

    ChildrenInfo() {

    }
}
