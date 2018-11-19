package com.developerspace.firebaseintegration;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText EmailId;
    EditText Password;
    Button Signup;
    ProgressBar prbar;
    private FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EmailId = (EditText)findViewById(R.id.editTextEmailID);
        Password = (EditText)findViewById(R.id.editTextpassword);
        Signup=(Button)findViewById(R.id.buttonSignUp);
        prbar = (ProgressBar)findViewById(R.id.progressBar);
        prbar.setVisibility(View.GONE);
        fbAuth = FirebaseAuth.getInstance();
        Signup.setOnClickListener(this);
    }

    private void registerUser(){
        final String emailid = EmailId.getText().toString().trim();
        final String password = Password.getText().toString().trim();

        if(TextUtils.isEmpty(emailid)){
            Toast.makeText(this,"Please enter email id ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
            return;
        }

        prbar.setVisibility(View.VISIBLE);
        fbAuth.createUserWithEmailAndPassword(emailid,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Registered user successfully",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this,Database.class);
                            intent.putExtra("EmailId",emailid);
                            intent.putExtra("Password",password);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Failed to register user. Please try again later",Toast.LENGTH_SHORT).show();
                        }
                        prbar.setVisibility(View.GONE);
                    }
                });

    }


    @Override
    public void onClick(View view) {
        registerUser();
    }
}
