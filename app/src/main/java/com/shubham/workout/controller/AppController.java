package com.shubham.workout.controller;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.LruCache;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {
    private static AppController instance;
    private RequestQueue requestQueue;
    private static final String TAG = AppController.class.getSimpleName();
    private ImageLoader imageLoader;
    private  Context context;

    private AppController(Context context){
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized AppController getInstance(Context context) {
        if (instance == null){
            instance = new AppController(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }

    public ImageLoader getImageLoader(){
        getRequestQueue();
        if (imageLoader == null){
            imageLoader = new ImageLoader(requestQueue,new LRUBitmapCache());
        }
        return imageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag){
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }


    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }

}
