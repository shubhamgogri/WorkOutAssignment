package com.shubham.workout.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shubham.workout.R;
import com.shubham.workout.model.WorkOut;

import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<WorkOut> workOutArrayList;
    public RecyclerViewAdapter(List<WorkOut> workOuts, Context context) {
        this.context = context;
        workOutArrayList = workOuts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WorkOut workOut = workOutArrayList.get(position);
        holder.title.setText(workOut.getTitle());
        holder.trainer.setText(MessageFormat.format("with {0}", workOut.getCoach()));
        holder.difficulty.setText(workOut.getDifficulty());
        long sec = Long.parseLong(workOut.getDuration());
        long min = TimeUnit.SECONDS.toMinutes(sec);
        holder.duration.setText(MessageFormat.format("{0} Min", min));

        Log.d("TAG", "onBindViewHolder: Somethings working");
    }

    public void updateList(List<WorkOut> filteredList){
        workOutArrayList = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return workOutArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView trainer;
        public TextView duration;
        public TextView difficulty;
        public ImageView image;
        public Context context;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;

            title = itemView.findViewById(R.id.card__title);
            trainer = itemView.findViewById(R.id.card_trainer);
            duration = itemView.findViewById(R.id.card_duration);
            difficulty = itemView.findViewById(R.id.card_difficulty);
            image = itemView.findViewById(R.id.card_image);
        }
    }
}
