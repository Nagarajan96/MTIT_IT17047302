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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register, forgotpassword;
    private EditText editTextemail, editTextpassword;
    private Button login;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);

        editTextemail = (EditText) findViewById(R.id.email);
        editTextpassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        forgotpassword = (TextView) findViewById(R.id.forgotPassword);
        forgotpassword.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.register:
                startActivity(new Intent(this, Register.class));
                break;

            case R.id.login:
                UserLogin();

            case R.id.forgotPassword:
                startActivity(new Intent(MainActivity.this, ForgotPassword.class));
                break;

        }

    }

    private void UserLogin() {

        final String email = editTextemail.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();


        if(email.isEmpty()){
            editTextemail.setError("Email Address is Required");
            editTextemail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextemail.setError("Please provide valid email!");
            editTextemail.requestFocus();
            return;


        }


        if(password.isEmpty()){
            editTextpassword.setError("Password is Required");
            editTextpassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            editTextpassword.setError("Min password length should be 6 characters");
            editTextpassword.requestFocus();
            return;
        }


        progressBar.setVisibility(View.INVISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if(user.isEmailVerified()){

                        startActivity(new Intent(MainActivity.this, JokesView.class));
                    }else{
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "Check your Email to verify your account", Toast.LENGTH_LONG).show();
                    }


                }else{
                    Toast.makeText(MainActivity.this, "failed to login please check your details", Toast.LENGTH_LONG).show();

                }
            }
        });



    }
}