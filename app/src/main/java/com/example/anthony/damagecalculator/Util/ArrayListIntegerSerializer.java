package com.example.anthony.damagecalculator.Util;

import android.util.Log;

import com.activeandroid.serializer.TypeSerializer;

import java.util.ArrayList;

/**
 * Created by twong on 5/29/15.
 */
public class ArrayListIntegerSerializer extends TypeSerializer {
    @Override
    public Class<?> getDeserializedType() {
        return ArrayList.class;
    }

    @Override
    public Class<?> getSerializedType() {
        return String.class;
    }

    @Override
    public Object serialize(Object data) {
        Log.d("Serializer Log", "Serializing");
        if (data == null) {
            return null;
        }
        ArrayList<Integer> dataArray = (ArrayList) data;
        String returnString = "";
        for (Integer dataInteger : dataArray) {
            if (returnString.length() == 0) {
                returnString += dataInteger;
            } else {
                returnString += "," + dataInteger;
            }
        }

        return returnString;
    }

    @Override
    public Object deserialize(Object data) {
        Log.d("Serializer Log", "Deserializing");
        if (data == null) {
            return new ArrayList<Integer>();
        }

        ArrayList<Integer> dataArray = new ArrayList<>();
        if (((String) data).length() == 0) {
            return new ArrayList<Integer>();
        }
        String[] splitData = ((String) data).split(",");

        for (int i = 0; i < splitData.length; i++) {
            dataArray.add(Integer.valueOf(splitData[i]));
        }

        return dataArray;
    }
}
