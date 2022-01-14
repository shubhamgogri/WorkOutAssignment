package com.shubham.workout.data;

import com.shubham.workout.model.WorkOut;

import java.util.ArrayList;

public interface DataAsyncResponce {
    void processFinished(ArrayList<WorkOut> list);
}
