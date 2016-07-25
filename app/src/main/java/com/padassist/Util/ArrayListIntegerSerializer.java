package com.padassist.Util;


import com.activeandroid.serializer.TypeSerializer;
import com.padassist.Data.Element;

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
    public String serialize(Object data) {
//        Log.d("Serialize", "data is: " + data);
        if (data == null) {
            return null;
        }
        ArrayList<Object> dataArray = (ArrayList) data;
        String returnString = "";
        if (dataArray.size() != 0){
            returnString ="" + dataArray.get(0).getClass();
        }
        for (Object dataObject : dataArray) {
            returnString += "," + dataObject;
        }
//        Log.d("Serialize", "Return string is: " + returnString);
        return returnString;
    }

    @Override
    public ArrayList<Object> deserialize(Object data) {
        if (data == null) {
            return new ArrayList<Object>();
        }

        ArrayList<Object> dataArray = new ArrayList<>();
        if (((String) data).length() == 0) {
            return new ArrayList<Object>();
        }
        String[] splitData = ((String) data).split(",");
        if(splitData[0].equals("class java.lang.Integer")) {
            for (int i = 1; i < splitData.length; i++) {
                dataArray.add(Integer.valueOf(splitData[i]));
            }
        }else if (splitData[0].equals("class java.lang.Double")){
            for (int i = 1; i < splitData.length; i++) {
                dataArray.add(Double.valueOf(splitData[i]));
            }
        }else if (splitData[0].equals("class com.example.anthony.damagecalculator.Data.Element")){
            for (int i = 1; i < splitData.length; i++) {
                dataArray.add(Element.valueOf(splitData[i]));
            }
        }else if (splitData[0].equals("class java.lang.Long")) {
            for (int i = 1; i < splitData.length; i++) {
                dataArray.add(Long.valueOf(splitData[i]));
            }
        }else if (splitData[0].equals("class java.lang.Boolean")) {
            for (int i = 1; i < splitData.length; i++) {
                dataArray.add(Boolean.valueOf(splitData[i]));
            }
        }

//        Log.d("Serialize", "dataArray: " + dataArray);
        return dataArray;
    }
}