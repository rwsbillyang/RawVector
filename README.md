Android Drawable from raw vector xml 

Android VectorDrawable only uses compiled binary vector xml,  when put them in res directory. 

## Features

- Zero dependency,  no android support library, androidx, 3rd dependencies.
- Can modify color of vectors
- Works with any InputStream, including internet connection.

## Usage

Add maven source in settings.gradle:
```
maven { url = uri("https://jitpack.io") }
```

Add dependency:
```
dependencies {
    implementation 'com.github.rwsbillyang:vector:1.0'
}
```

Demo Code:
```
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
```
Notice: Android should NOT be Dark mode. 

## Limitation
 Only support name, fillColor, pathData attributes in vector xml in current version.

