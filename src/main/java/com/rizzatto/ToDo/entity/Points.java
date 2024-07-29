package com.rizzatto.ToDo.entity;

import com.rizzatto.ToDo.enums.Priority;


public class Points {

    public static int calcPoints(Priority priority){
        if(priority.equals("LOW")) return 2;
        if(priority.equals("MEDIUM")) return 4;
        return 6;
    }


    

    

}
