package com.developerspace.firebaseintegration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LaunchActivity extends AppCompatActivity implements View.OnClickListener {
    TextView messageDisp;
    Button signup;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        signup =(Button)findViewById(R.id.buttonSign);
        login = (Button)findViewById(R.id.buttonlogin);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaunchActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  Intent intent = new Intent(LaunchActivity.this,Login.class);
                  startActivity(intent);
            }
        });
        messageDisp = (TextView)findViewById(R.id.textViewLaunchScreen);
        messageDisp.setText("Welcome to Firebase example. If you have registered, please login , else register using signup");
    }

    @Override
    public void onClick(View v) {

    }
}
