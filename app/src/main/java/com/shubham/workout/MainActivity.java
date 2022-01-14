package com.shubham.workout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.shubham.workout.adapter.RecyclerViewAdapter;
import com.shubham.workout.data.WorkoutData;
import com.shubham.workout.model.WorkOut;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import me.tankery.lib.circularseekbar.CircularSeekBar;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ImageView filter, filter_save;
    private ConstraintLayout filterView;
//    private static String url = "http://3.108.207.62:3003/api/user/workout/all?category_id=14";
    private RecyclerView recyclerView;
    private List<WorkOut> workOutList;
    private List<WorkOut> filterList;
    private RecyclerViewAdapter adapter;
    private String[] diff = {"Any","Beginner","Intermediate", "Advanced" };
    private ArrayList<String> trainersList;
    private Spinner difficult_spinner;
    private Spinner equipments_spinner;
    private Spinner trainer_spinner;
    private CircularSeekBar circularSeekBar;
    private TextView duration_text;
    int progress = 0;
    String diff_tag ;
    String train_tag ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);


        filter = findViewById(R.id.fil_imageView);
        filterView = findViewById(R.id.filter);
        filter_save = findViewById(R.id.filter_imageView);
        recyclerView = findViewById(R.id.recyclerview);
        difficult_spinner = findViewById(R.id.filter_difficulty_spinner);
        equipments_spinner = findViewById(R.id.filter_equip_spinner);
        trainer_spinner = findViewById(R.id.filter_trainer_spinner);
        circularSeekBar = findViewById(R.id.circularSeekBar);
        duration_text = findViewById(R.id.filter_duration_text);

        workOutList = new WorkoutData().getData(MainActivity.this,
                list -> {
                    Log.d("TAG", "processFinished: "+ workOutList.size());
                    adapter = new RecyclerViewAdapter(workOutList, MainActivity.this);

                    getTrainers(workOutList);
                    setAdaptersforSpinner();
                    Log.d("TAG", "onCreate: recycler" + adapter.getItemCount());
                    GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 3);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);

                });
        equipments_spinner.setOnItemSelectedListener(MainActivity.this);
        difficult_spinner.setOnItemSelectedListener(MainActivity.this);
        trainer_spinner.setOnItemSelectedListener(MainActivity.this);
        duration_text.setText("0");


        duration_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circularSeekBar.setVisibility(View.VISIBLE);
            }
        });

        circularSeekBar.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar circularSeekBar, float progress, boolean fromUser) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        duration_text.setText(MessageFormat.format("{0} Min", String.valueOf((int ) progress)));
                    }
                });

            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar seekBar) {
                progress = (int) seekBar.getProgress();
                circularSeekBar.setVisibility(View.GONE);
//                duration_text.setText(String.valueOf(progress));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        duration_text.setText(MessageFormat.format("{0}Min", String.valueOf(progress)));
                    }
                });

            }

            @Override
            public void onStartTrackingTouch(CircularSeekBar seekBar) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        duration_text.setText(MessageFormat.format("{0}Min", String.valueOf(progress)));
                    }
                });

            }
        });


        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterView.setVisibility(View.VISIBLE);
            }
        });
        filter_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Filter", "onClick: Equip " + equipments_spinner.getTag()
                        + " duration " + progress + " trainer " +
                        trainer_spinner.getTag()+
                        " difficulty " +
                        difficult_spinner.getTag()
                );

                if(train_tag.equals("Any") && diff_tag.equals("Any")){
                    adapter.updateList(workOutList);
                }else {
                    filterList = new ArrayList<>();

                    if (train_tag.equals("Any") && !diff_tag.equals("Any")) {
//                    consider diff_tag
                        for (WorkOut work : workOutList) {
                            if (diff_tag.equals(work.getDifficulty())) {
                                filterList.add(work);
                            }
                        }
                        adapter.updateList(filterList);

                    } else if (!train_tag.equals("Any") && diff_tag.equals("Any")) {
//                    consider train_tag
                        for (WorkOut work : workOutList) {
                            if (train_tag.equals(work.getCoach())) {
                                filterList.add(work);
                            }
                        }
                        adapter.updateList(filterList);

                    } else if (!train_tag.equals("Any") && !diff_tag.equals("Any")) {
//                    consider both
                        for (WorkOut work : workOutList) {
                            if (diff_tag.equals(work.getDifficulty()) && train_tag.equals(work.getCoach())) {
                                filterList.add(work);
                            }
                        }
                        adapter.updateList(filterList);
                    }
                }

                filterView.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void setAdaptersforSpinner() {


        ArrayAdapter diff_adapter = new ArrayAdapter(MainActivity.this,R.layout.support_simple_spinner_dropdown_item, diff);
        diff_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        difficult_spinner.setAdapter(diff_adapter);

//                Equipments
        ArrayAdapter equip_adapter = new ArrayAdapter(MainActivity.this, R.layout.support_simple_spinner_dropdown_item,
                new String[]{"None", "Any" });
        equip_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        equipments_spinner.setAdapter(equip_adapter);

        ArrayAdapter trainer_adapter = new ArrayAdapter(MainActivity.this, R.layout.support_simple_spinner_dropdown_item,
                trainersList);
        trainer_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        trainer_spinner.setAdapter(trainer_adapter);

    }

    private void getTrainers(List<WorkOut> workOutList) {
        if (trainersList == null) {
            trainersList = new ArrayList<>();
            trainersList.add("Any");

            for (int i = 0; i < workOutList.size(); i++) {
                WorkOut workOut = workOutList.get(i);
                if (!trainersList.contains(workOut.getCoach()))
                    trainersList.add(workOut.getCoach());
            }
        }
        trainer_spinner.setTag(trainersList.get(0));
        difficult_spinner.setTag(diff[0]);
        equipments_spinner.setTag("None");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        Make switch and handle those;

        switch (parent.getId()){
            case R.id.filter_difficulty_spinner:
                Log.d("Spinner", "onItemSelected: " + diff[position]);
                difficult_spinner.setTag(diff[position]);
                diff_tag = diff[position];
                break;

            case R.id.filter_equip_spinner:
                Log.d("Spinner", "onItemSelected: " + position);
                equipments_spinner.setTag(position == 0 ? "None": "Any");
                break;

            case R.id.filter_trainer_spinner:
                Log.d("Spinner", "onItemSelected: " + trainersList.get(position));
                trainer_spinner.setTag(trainersList.get(position));
                train_tag = trainersList.get(position);
                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}