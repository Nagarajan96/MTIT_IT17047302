package com.example.it17047302_mtit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText editTextEmail;
    private Button Reset;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        editTextEmail = (EditText) findViewById(R.id.email);
        Reset = (Button) findViewById(R.id.forgotPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ResetPassword();

            }
        });


    }

    private void ResetPassword(){

        String email = editTextEmail.getText().toString().trim();


        if(email.isEmpty()){
            editTextEmail.setError("Email Address is Required");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email!");
            editTextEmail.requestFocus();
            return;

        }

        progressBar.setVisibility(View.INVISIBLE);
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this, "Check your Email to Reset your Password", Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(ForgotPassword.this, "try again", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}