package com.coder.guoy.goodtimes.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.graphics.Palette;

/**
 * @Version:
 * @Author:Guoy
 * @CreateTime:2017/10/28
 * @Descrpiton:
 */
public class PaletteUtil implements Palette.PaletteAsyncListener {

    private static PaletteUtil instance;

    private PatternCallBack patternCallBack;

    public static PaletteUtil getInstance() {
        if (instance == null) {
            instance = new PaletteUtil();
        }
        return instance;
    }

    public synchronized void init(Bitmap bitmap, PatternCallBack patternCallBack) {
        Palette.from(bitmap).generate(this);
        this.patternCallBack = patternCallBack;
    }

    public synchronized void init(Resources resources, int resourceId, PatternCallBack patternCallBack) {
        Bitmap bitmap = BitmapFactory.decodeResource(resources, resourceId);
        Palette.from(bitmap).generate(this);
        this.patternCallBack = patternCallBack;
    }

    @Override
    public synchronized void onGenerated(Palette palette) {
        Palette.Swatch a = palette.getVibrantSwatch();
        Palette.Swatch b = palette.getLightVibrantSwatch();
        int colorEasy = 0;
        if (b != null) {
            colorEasy = b.getRgb();
        }
        patternCallBack.onCallBack(a.getTitleTextColor());
    }

    public interface PatternCallBack {
        void onCallBack(int titleColor);
    }

}