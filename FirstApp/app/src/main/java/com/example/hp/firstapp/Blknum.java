package com.example.hp.firstapp;

import java.util.Arrays;

/**
 * Created by Hp on 8/5/2016.
 */
public class Blknum {
    static String []number;
    static Boolean status;
    public Blknum(String[] number,int size,Boolean status) {
        this.number=new String[size];
        this.number= Arrays.copyOf(number,number.length);
        this.status=status;
    }

    public static String[] getSuite()
    {
        return number;
    }
    public static Boolean getStatus()
    {
        return status;
    }
}
