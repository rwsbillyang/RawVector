package com.github.rwsbillyang.vector;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * raw vector xml file parser
 * <vector xmlns:android="http://schemas.android.com/apk/res/android"
 *     android:width="1134dp"
 *     android:height="1134dp"
 *     android:viewportWidth="1134"
 *     android:viewportHeight="1134">
 *   <path
 *       android:pathData="M0,0h1134v1134h-1134z"
 *       android:fillColor="#000000"/>
 *   <path
 *       android:pathData="M187,156h760v31h-760z"
 *       android:fillColor="#ff0000"/>
 * </vector>
 * */
public class RawVectorXmlParser {
    public final static String TAG = "RawVector";
    public static VectorXmlContent parse(InputStream inputStream){
        try {
            // 创建一个DocumentBuilder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // 解析XML文件
            Document document = builder.parse(inputStream);

            VectorXmlContent xmlContent = new VectorXmlContent();
            // 获取根元素
            Element vector = document.getDocumentElement();
            xmlContent.width = Integer.parseInt(removeSuffix(vector.getAttribute("android:width"), "dp"));
            xmlContent.height = Integer.parseInt(removeSuffix(vector.getAttribute("android:height"), "dp"));
            xmlContent.viewportWidth = Integer.parseInt(vector.getAttribute("android:viewportWidth"));
            xmlContent.viewportHeight = Integer.parseInt(vector.getAttribute("android:viewportHeight"));


            NodeList nodeList = vector.getElementsByTagName("path");
            List<VectorPath> pathList = new ArrayList(nodeList.getLength());

            Log.d(TAG, "nodeList.size="+ nodeList.getLength());

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element p = (Element) nodeList.item(i);
                VectorPath vp = new VectorPath();
                vp.name = p.getAttribute("android:name");
                vp.setPathData(p.getAttribute("android:pathData"));
                vp.setFillColor(p.getAttribute("android:fillColor"));
                pathList.add(vp);
            }
            xmlContent.pathList = pathList;

            Log.d(TAG, xmlContent.toString());
            return xmlContent;
        } catch (Exception e) {
            Log.w(TAG,"parse inputStream Exception: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private static String removeSuffix(String s, String suffix){
        if(suffix == null || suffix == "")
            return s;
        if(suffix.length() >= s.length())
            return "";
        int end = s.length() - suffix.length();
        return s.substring(0, end);
    }
}
