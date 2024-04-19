package com.github.rwsbillyang.vectorDemo;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.PictureDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.github.rwsbillyang.vector.RawVector;
import com.github.rwsbillyang.vector.VectorXmlContent;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends Activity {
    private final static  String TAG = "RawVectorDemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView imageView = new ImageView(this);

        setContentView(imageView);

        PictureDrawable drawable = loadRawVector(R.raw.svg_rg_balance);
        if(drawable != null)
            imageView.setImageDrawable(drawable);
        else
            imageView.setImageResource(R.drawable.ic_launcher);

    }


    public PictureDrawable loadRawVector(int resId){
        //InputStream inputStream = getAssets().open("svg/svg_rg_balance.xml");
        InputStream inputStream = getResources().openRawResource(resId);
        return loadRawVector(inputStream);
    }

    public PictureDrawable loadRawVector(InputStream inputStream){
        try {
            //String color1 = "#ffff00";
            //String color2 = "#ff00ff";
            //TokenReplacingStream inputStream2 = new TokenReplacingStream(new TokenReplacingStream(inputStream, "#ff0000", color1), "#00ff00", color2);

            RawVector rv = new RawVector();
            VectorXmlContent c = rv.parse(inputStream);
            if(c != null){
                //option2: need provide name of path in vector xml
                boolean f = c.setFillColor("red1", Color.YELLOW);
                Log.d(TAG, "modify red1:" + f);
                f = c.setFillColor("green1", Color.BLUE);
                Log.d(TAG, "modify green1:" + f);

                PictureDrawable drawable = rv.createPictureDrawable(Color.TRANSPARENT);
                if(drawable != null){
                    return drawable;
                }else{
                    Log.w(TAG, "fail to parse from stream");
                }
            }else{
                Log.w(TAG, "fail to create PictureDrawable from stream");
            }
            inputStream.close();
        } catch (IOException e) {
            Log.w(TAG, "fallback use png: open svg IOException=" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
