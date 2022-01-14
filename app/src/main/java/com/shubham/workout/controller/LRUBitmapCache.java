package com.shubham.workout.controller;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import com.android.volley.toolbox.ImageLoader;

public class LRUBitmapCache implements ImageLoader.ImageCache {

    public static int getDefaultLruCacheSize(){
        int MaxMemory = (int) (Runtime.getRuntime().maxMemory()/1024);
        int cacheSize = MaxMemory/8;
        return cacheSize;
    }

    public LRUBitmapCache(){
        getDefaultLruCacheSize();
    }


    @Nullable
    @Override
    public Bitmap getBitmap(String url) {
        return getBitmap(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        putBitmap(url, bitmap);
    }
}
