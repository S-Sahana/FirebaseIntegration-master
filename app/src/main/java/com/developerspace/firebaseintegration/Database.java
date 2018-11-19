package com.developerspace.firebaseintegration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Database extends Activity implements View.OnClickListener {
    TextView displayView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        displayView = (TextView)findViewById(R.id.textViewDatabase);
        Intent in = getIntent();
        String str = in.getStringExtra("EmailId");
        displayView.setText("Welcome User "+str+ "to firebase example app");
    }

    @Override
    public void onClick(View v) {

    }
}
