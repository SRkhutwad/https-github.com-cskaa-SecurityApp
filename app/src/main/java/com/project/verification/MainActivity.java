package com.project.verification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    Button next;
    TextView name,address,state,city,pin,phone,email,pass;

    private ProgressBar progressBar;

    //FirebaseAuth fAuth;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        next = findViewById(R.id.button3);
        next.setOnClickListener(this);

        name = findViewById(R.id.editTextTextPersonName);
        address = findViewById(R.id.inputEmail);
        city = findViewById(R.id.editTextTextPersonName3);
        state = findViewById(R.id.state);
        pin = findViewById(R.id.editTextTextPersonName4);
        phone = findViewById(R.id.editTextTextPersonName2);
        email = findViewById(R.id.editTextTextPersonName5);
        pass = findViewById(R.id.editTextTextPersonName7);


        progressBar = findViewById(R.id.progressBar);

    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {

            case R.id.button3:
                sub();
                break;
        }
    }

    private void sub()
    {
//        TextView name,address,state,city,pin,phone,email,pass;

        final String uname = name.getText().toString().trim();
        final String addrs = address.getText().toString().trim();
        final String states = state.getText().toString().trim();
        final String citys = city.getText().toString().trim();
        final String pins = pin.getText().toString().trim();
        final String contact = phone.getText().toString().trim();
        final String mail = email.getText().toString().trim();
        String password = pass.getText().toString().trim();

        if (uname.isEmpty())
        {
            name.setError("Full name is required");
            name.requestFocus();
            return;
        }

        if (addrs.isEmpty())
        {
            address.setError("Address is required");
            address.requestFocus();
            return;
        }

        if (states.isEmpty())
        {
            state.setError("State is required");
            state.requestFocus();
            return;
        }

        if (citys.isEmpty())
        {
            city.setError("City is required");
            city.requestFocus();
            return;
        }

        if (pins.isEmpty())
        {
            pin.setError("Pin is required");
            pin.requestFocus();
            return;
        }

        if (pins.length()<6)
        {
            pin.setError("Min lenght should be 6 digits");
            pin.requestFocus();
            return;
        }

        if (contact.isEmpty())
        {
            phone.setError("Phone is required");
            phone.requestFocus();
            return;
        }

        if (contact.length()<10)
        {
            phone.setError("Min lenght should be 10 digits");
            phone.requestFocus();
            return;
        }

        if (mail.isEmpty())
        {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches())
        {
            email.setError("Please provide valid email!");
            email.requestFocus();
            return;
        }

        if (password.isEmpty())
        {
            pass.setError("Password name is required");
            pass.requestFocus();
            return;
        }

        if (password.length()<6)
        {
            pass.setError("Min password lenght should be 6 char");
            pass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                {
//                    final String uname = name.getText().toString().trim();
//                    final String addrs = address.getText().toString().trim();
//                    final String states = state.getText().toString().trim();
//                    final String citys = city.getText().toString().trim();
//                    final String pins = pin.getText().toString().trim();
//                    final String contact = phone.getText().toString().trim();
//                    final String mail = email.getText().toString().trim();
//                    String password = pass.getText().toString().trim();

                    User user = new User(uname,addrs,states,citys,pins,contact,mail);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful())
                            {
                                Toast.makeText(MainActivity.this, "User Has been registred successfull!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),ClientLogin.class));
                                finish();
                                progressBar.setVisibility(View.GONE);

                                //
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "Failed to register! Try again!", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }

                        }
                    });
                }
                else
                {
                    //Toast.makeText(MainActivity.this, "Failed to register! Try again!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }

    public void alreadyAcc(View view)
    {
        Intent intent=new Intent(getApplicationContext(),ClientLogin.class);
        startActivity(intent);
    }
}