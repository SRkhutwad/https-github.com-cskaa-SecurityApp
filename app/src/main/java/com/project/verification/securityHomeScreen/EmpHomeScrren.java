package com.project.verification.securityHomeScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.project.verification.R;
import com.project.verification.securityRegistration.Emp_User;
import com.project.verification.securityRegistration.myAdapter;

public class EmpHomeScrren extends AppCompatActivity
{
    RecyclerView recview;
    myAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_home_scrren);

        recview = findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users"),Model.class)
                        .build();

        myAdapter = new myAdapter(getApplicationContext(),options);
        recview.setAdapter(myAdapter);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        myAdapter.startListening();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        myAdapter.stopListening();
    }
}