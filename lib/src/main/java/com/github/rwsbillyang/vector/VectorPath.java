package com.github.rwsbillyang.vector;


import android.graphics.Path;
import android.util.Log;

/**
 *
 * <path
 *       android:pathData="M0,0h1134v1134h-1134z"
 *       android:fillColor="#000000"/>
 * */
public class VectorPath {
    private final static String TAG = "RawVector";

    public String name;

    private String fillColorStr;
    public int fillColor = 0xFF000000;
    private String pathData;
    public Path path;
    //more field...


    public void setPathData(String data){
        pathData = data;
        path = VectorPathParser.createPathFromPathData(pathData);
    }


    public void setFillColor(String color){
        this.fillColorStr = color;

        if(color.startsWith("#")){
            int c = Integer.parseInt(color.substring(1), 16) ;
            fillColor = (0xFFFFFFFF & c) | 0xFF000000;
        }else{
            Log.w(TAG, "invalid color: " + color);
        }
    }
    public void setFillColor(int color){
        fillColor = (0xFFFFFFFF & color) | 0xFF000000;
    }

    public String toString(){
       return  "name=" + name + ", fillColorStr=" + fillColorStr + ", color=" + String.format("%x", fillColor)
               + ", successful to create path=" + (path != null) + ", pathData=" + pathData;
    }
}
