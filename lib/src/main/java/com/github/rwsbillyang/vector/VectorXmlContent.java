package com.github.rwsbillyang.vector;


import java.util.List;

public class VectorXmlContent {
    public int width;
    public int height;

    public int viewportWidth;
    public int viewportHeight;

    public List<VectorPath> pathList;

    //more fields...

    public boolean setFillColor(String pathName, int color){
        if(pathName == null || pathName.isEmpty()){
            return false;
        }
        if(pathList != null && pathList.size() > 0){
            for(VectorPath vp: pathList){
                if(pathName.equals(vp.name)){
                    vp.setFillColor(color);
                    return true;
                }
            }
        }
        return false;
    }

    public String toString(){
        StringBuffer sb = new StringBuffer("width=" + width + ", height=" + height + ", viewportWidth=" + viewportWidth
                + ", viewportHeight=" + viewportHeight);

//          if(pathList == null){
//              sb.append("no path list");
//          }else{
//              sb.append("\npathList: \n");
//              for(VectorPath p: pathList){
//                  sb.append(p.toString() + "\n");
//              }
//          }
        return sb.toString();
    }

}
