package kz.edu.sdu.rauanassabayev.kundelik.Helpers;

import android.app.Activity;
import android.app.Application;
import android.graphics.Typeface;

import java.util.HashMap;

/**
 * Created by rauanassabayev on 1/20/18.
 */

public class FontFactory {
    private  static FontFactory instance;
    public HashMap<String, Typeface> fontMap = new HashMap<String, Typeface>();

    private FontFactory() {}

    public static FontFactory getInstance() {
        if (instance == null){
            instance = new FontFactory();
        }
        return instance;
    }

    public Typeface getFont(Activity pActivity, String font) {
        Typeface typeface = fontMap.get(font);
        if (typeface == null) {
            typeface = Typeface.createFromAsset(pActivity.getResources().getAssets(),font);
            fontMap.put(font, typeface);
        }
        return typeface;
    }
}