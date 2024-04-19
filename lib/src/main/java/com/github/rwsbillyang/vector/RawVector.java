package com.github.rwsbillyang.vector;


import android.graphics.Canvas;

import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.drawable.PictureDrawable;
import android.util.Log;

import java.io.InputStream;

public class RawVector{
    public final static String TAG = "RawVector";
    /**
     * The parsed Picture object.
     */
    private Picture picture;

    private Canvas canvas;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private VectorXmlContent vector;

    /**
     * @return VectorXmlContent if successfully, else null
     * */
    public VectorXmlContent parse(InputStream inputStream){
        vector = RawVectorXmlParser.parse(inputStream);
        return vector;
    }
    /**
     * create VectorXmlContent programmatically
     * then get PictureDrawable using createPictureDrawable
     * */
    public void setVectorXmlContent(VectorXmlContent vector){
        this.vector = vector;
    }
    public VectorXmlContent getVectorXmlContent(){
        return vector;
    }

    /**
     * Before call createPictureDrawable, caller can modify VectorXmlContent
     * @return if parse successfully and has path data, return PictureDrawable, or else return null
     * */
    public PictureDrawable createPictureDrawable(int backgroundColor) {
        if(vector != null && vector.pathList.size() > 0 ){
            picture = new Picture();
            //bounds = new RectF(0, 0, vector.width, vector.height);

            canvas = picture.beginRecording(vector.width, vector.height);

            //设置背景色
            canvas.drawColor(backgroundColor);//0xffffffff

            paint.setAlpha(0xff);
            paint.setStyle(Paint.Style.FILL);
            //paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));

            for (int i = 0; i < vector.pathList.size(); i++) {
                VectorPath vp = vector.pathList.get(i);
                paint.setColor(vp.fillColor);

                //doLimits(vp.path);
                Log.d(TAG, "draw path:" + vp.toString());

                canvas.drawPath(vp.path, paint);
            }

            picture.endRecording();

            return new PictureDrawable(picture);
        }

        return null;
    }




//    private void doLimits(float x, float y) {
//        if (x < limits.left) {
//            limits.left = x;
//        }
//        if (x > limits.right) {
//            limits.right = x;
//        }
//        if (y < limits.top) {
//            limits.top = y;
//        }
//        if (y > limits.bottom) {
//            limits.bottom = y;
//        }
//    }
//
//    private void doLimits(float x, float y, float width, float height) {
//        doLimits(x, y);
//        doLimits(x + width, y + height);
//    }
//
//    private void doLimits(Path path) {
//        path.computeBounds(bounds, false);
//        doLimits(bounds.left, bounds.top);
//        doLimits(bounds.right, bounds.bottom);
//    }

}
