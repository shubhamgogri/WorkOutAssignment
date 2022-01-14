package com.shubham.workout.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.shubham.workout.model.WorkOut;
import com.shubham.workout.controller.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WorkoutData {
    ArrayList<WorkOut> workOutArrayList = new ArrayList<>();
    private static String url = "http://3.108.207.62:3003/api/user/workout/all?category_id=14";
    Context context;
    public List<WorkOut> getData(Context context, final DataAsyncResponce callback){
        this.context = context;
        JsonObjectRequest document = new JsonObjectRequest(
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("data");

                            for (int i = 0; i < data.length(); i++) {
                                JSONObject each_obj = data.getJSONObject(i);
                                String title = each_obj.getString("name");
                                String image = each_obj.getString("image");
                                String duration = each_obj.getString("duration");
                                JSONArray equipments = each_obj.getJSONArray("equipments");
                                ArrayList<String> equip = new ArrayList<>();
                                if(equipments.length()>0){
                                    for (int j = 0; j <equipments.length() ; j++) {
                                        equip.add(String.valueOf(equipments.get(j)));
                                    }
                                }
                                String trainer = each_obj.getString("trainer_name");
                                String difficulty = each_obj.getString("difficulty_level_name");

                                WorkOut workOut = new WorkOut();
                                workOut.setTitle(title);
                                workOut.setImage(getImage(image));
                                workOut.setDuration(duration);
                                workOut.setDifficulty(difficulty);
                                workOut.setCoach(trainer);
                                workOut.setEquipments(equip);

                                Log.d("MainActivity", "onResponse: List : " + workOut.getTitle() + workOut.getCoach() + workOut.getDifficulty()+
                                        workOut.getImage() + workOut.getDuration());
                                workOutArrayList.add(workOut);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } if (null != callback) callback.processFinished(workOutArrayList);
//                        Toast.makeText(, "Json Request Successful. ", Toast.LENGTH_SHORT).show();
                        Log.d("MainActivity", "onResponse: " + workOutArrayList.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("MainActivity", "onResponse: Failed. " +error);
//                Toast.makeText(MainActivity.this, "Json Request Failed. " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance(context).addToRequestQueue(document);
        return workOutArrayList;
    }
    public Bitmap getImage(String im){
        final Bitmap[] bit = {null};
        ImageRequest imageRequest = new ImageRequest(im,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        bit[0] =response;
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG", "onErrorResponse: " + "imageResponse" + error );
                    }
                });
        AppController.getInstance(context).addToRequestQueue(imageRequest);
        return bit[0];
    }

}
