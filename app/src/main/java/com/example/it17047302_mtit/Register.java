package com.example.it17047302_mtit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity implements View.OnClickListener {
     private TextView head;
    private EditText editTextFullName, editTextEmail, editTextPassword, editTextAge;
    private ProgressBar progressBar;
    private Button RegisterUser;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        head = (TextView) findViewById(R.id.head);
        head.setOnClickListener(this);

        RegisterUser = (Button) findViewById(R.id.RegisterUser);
        RegisterUser.setOnClickListener(this);

        editTextFullName = (EditText) findViewById(R.id.name);
        editTextAge = (EditText) findViewById(R.id.age);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.head:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.RegisterUser:
                RegisterUser();
        }

    }

    private void RegisterUser() {

        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        final String name = editTextFullName.getText().toString().trim();
        final String age = editTextAge.getText().toString().trim();


        if(name.isEmpty()){

            editTextFullName.setError("Full Name is Required");
            editTextFullName.requestFocus();
            return;
        }


        if(age.isEmpty()){

            editTextAge.setError("Age is Required");
            editTextAge.requestFocus();
            return;
        }


        if(email.isEmpty()){

            editTextEmail.setError("Email is Required");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email!");
            editTextEmail.requestFocus();
            return;


        }

        if(password.isEmpty()){

            editTextPassword.setError("Password is Required");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            editTextPassword.setError("Min password length should be 6 characters");
             editTextPassword.requestFocus();
             return;
        }

       progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Users users = new Users(name, age, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(Register.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.VISIBLE);
                                    }else {
                                        Toast.makeText(Register.this, "failed to register try again", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

                        }else{
                            Toast.makeText(Register.this, "failed to register", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });

    }
}


