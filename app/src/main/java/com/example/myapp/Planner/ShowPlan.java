package com.example.myapp.Planner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShowPlan extends AppCompatActivity implements HorizontalDaysAdapter.OnHorizontalItemClickListener{

    RecyclerView recyclerView;
    ArrayList<DayModel> arrayList;
    HorizontalDaysAdapter daysAdapter;
    TextView textView, textViewBreakfast, textViewDinner, textViewSupper;
    ImageView imageViewBreakfast, imageViewDinner, imageViewSupper;
    DatabaseReference recipeDatabaseReference, databaseReference, databaseReferenceBreakfast,
                        databaseReferenceDinner, databaseReferenceSupper;
    String[] daysNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_plan);

        recyclerView = findViewById(R.id.recycler_view_show_plan);
        textViewBreakfast = findViewById(R.id.textViewPlannerBreakfastName);
        textViewDinner = findViewById(R.id.textViewPlannerDinnerName);
        textViewSupper = findViewById(R.id.textViewPlannerSupperName);
        imageViewBreakfast = findViewById(R.id.imageViewPlannerBreakfast);
        imageViewDinner = findViewById(R.id.imageViewPlannerDinner);
        imageViewSupper = findViewById(R.id.imageViewPlannerSupper);

        databaseReferenceBreakfast = FirebaseDatabase.getInstance().getReference("Planner").child("day1").child("breakfast");
        databaseReferenceDinner = FirebaseDatabase.getInstance().getReference("Planner").child("day1").child("dinner");
        databaseReferenceSupper = FirebaseDatabase.getInstance().getReference("Planner").child("day1").child("supper");

        databaseReferenceBreakfast.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String key = snapshot.child("key").getValue().toString();
                recipeDatabaseReference = FirebaseDatabase.getInstance().getReference("Recipes").child(key);
                recipeDatabaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = snapshot.child("name").getValue().toString();
                        textViewBreakfast.setText(name);
                        Picasso.with(ShowPlan.this)
                                .load(snapshot.child("imageUrl").getValue().toString())
                                .placeholder(R.mipmap.ic_launcher)
                                .fit()
                                .centerCrop()
                                .into(imageViewBreakfast);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReferenceSupper.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String key = snapshot.child("key").getValue().toString();
                recipeDatabaseReference = FirebaseDatabase.getInstance().getReference("Recipes").child(key);
                recipeDatabaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = snapshot.child("name").getValue().toString();
                        textViewSupper.setText(name);
                        Picasso.with(ShowPlan.this)
                                .load(snapshot.child("imageUrl").getValue().toString())
                                .placeholder(R.mipmap.ic_launcher)
                                .fit()
                                .centerCrop()
                                .into(imageViewSupper);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReferenceDinner.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String key = snapshot.child("key").getValue().toString();
                recipeDatabaseReference = FirebaseDatabase.getInstance().getReference("Recipes").child(key);
                recipeDatabaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = snapshot.child("name").getValue().toString();
                        textViewDinner.setText(name);
                        Picasso.with(ShowPlan.this)
                                .load(snapshot.child("imageUrl").getValue().toString())
                                .placeholder(R.mipmap.ic_launcher)
                                .fit()
                                .centerCrop()
                                .into(imageViewDinner);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //String[] daysNumbers = new String[]{"Dzień 1", "Dzień 2", "Dzień 3", "Dzień 4"};


        databaseReference = FirebaseDatabase.getInstance().getReference("Planner");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long children = snapshot.getChildrenCount();
                Toast.makeText(ShowPlan.this, String.valueOf(children), Toast.LENGTH_SHORT).show();
                daysNumbers = new String[(int)children];

                if (snapshot.hasChild("day1") && !snapshot.hasChild("day2")) {
                    daysNumbers[0] = "Dzień 1";
                } else if (snapshot.hasChild("day1") && snapshot.hasChild("day2")
                        && !snapshot.hasChild("day3")) {
                    daysNumbers[0] = "Dzień 1";
                    daysNumbers[1] = "Dzień 2";
                } else if (snapshot.hasChild("day1") && snapshot.hasChild("day2")
                        && snapshot.hasChild("day3") && !snapshot.hasChild("day4")) {
                    daysNumbers[0] = "Dzień 1";
                    daysNumbers[1] = "Dzień 2";
                    daysNumbers[2] = "Dzień 3";
                } else if (snapshot.hasChild("day1") && snapshot.hasChild("day2")
                        && snapshot.hasChild("day3") && snapshot.hasChild("day4")) {
                    daysNumbers[0] = "Dzień 1";
                    daysNumbers[1] = "Dzień 2";
                    daysNumbers[2] = "Dzień 3";
                    daysNumbers[3] = "Dzień 4";
                }

                arrayList = new ArrayList<>();
                for (int i = 0; i < daysNumbers.length; i++) {
                    DayModel dayModel = new DayModel(daysNumbers[i]);
                    arrayList.add(dayModel);
                }
                LinearLayoutManager layoutManager = new LinearLayoutManager(ShowPlan.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                daysAdapter = new HorizontalDaysAdapter(ShowPlan.this, arrayList, ShowPlan.this);
                recyclerView.setAdapter(daysAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    public void dayClicked(int position) {
        Toast.makeText(ShowPlan.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
        databaseReferenceBreakfast = FirebaseDatabase.getInstance().getReference("Planner")
                .child("day" + (position + 1)).child("breakfast");
        databaseReferenceBreakfast.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String key = snapshot.child("key").getValue().toString();
                recipeDatabaseReference = FirebaseDatabase.getInstance().getReference("Recipes").child(key);
                recipeDatabaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = snapshot.child("name").getValue().toString();
                        textViewBreakfast.setText(name);
                        Picasso.with(ShowPlan.this)
                                .load(snapshot.child("imageUrl").getValue().toString())
                                .placeholder(R.mipmap.ic_launcher)
                                .fit()
                                .centerCrop()
                                .into(imageViewBreakfast);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReferenceDinner = FirebaseDatabase.getInstance().getReference("Planner")
                .child("day" + (position + 1)).child("dinner");
        databaseReferenceDinner.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String key = snapshot.child("key").getValue().toString();
                recipeDatabaseReference = FirebaseDatabase.getInstance().getReference("Recipes").child(key);
                recipeDatabaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = snapshot.child("name").getValue().toString();
                        textViewDinner.setText(name);
                        Picasso.with(ShowPlan.this)
                                .load(snapshot.child("imageUrl").getValue().toString())
                                .placeholder(R.mipmap.ic_launcher)
                                .fit()
                                .centerCrop()
                                .into(imageViewDinner);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReferenceSupper = FirebaseDatabase.getInstance().getReference("Planner")
                .child("day" + (position + 1)).child("supper");
        databaseReferenceSupper.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String key = snapshot.child("key").getValue().toString();
                recipeDatabaseReference = FirebaseDatabase.getInstance().getReference("Recipes").child(key);
                recipeDatabaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = snapshot.child("name").getValue().toString();
                        textViewSupper.setText(name);
                        Picasso.with(ShowPlan.this)
                                .load(snapshot.child("imageUrl").getValue().toString())
                                .placeholder(R.mipmap.ic_launcher)
                                .fit()
                                .centerCrop()
                                .into(imageViewSupper);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}