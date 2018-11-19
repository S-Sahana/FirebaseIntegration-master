package com.developerspace.firebaseintegration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.print.PrinterId;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mListener;
    EditText Emailid;
    EditText password;
    Button login;
    ProgressBar pbar;
    FirebaseUser fbuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Emailid = (EditText)findViewById(R.id.editTextEmailid);
        password = (EditText)findViewById(R.id.editTextpassword);
        login=(Button)findViewById(R.id.buttonLogin);
        pbar=(ProgressBar)findViewById(R.id.progressBarLogin);
        pbar.setVisibility(View.GONE);
        login.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
        mListener = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                fbuser = firebaseAuth.getCurrentUser();
                if(fbuser!=null){
                    Toast.makeText(Login.this,"Already logged in "+fbuser.getEmail(),Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Login.this,"user not logged in",Toast.LENGTH_SHORT).show();
                }
            }
        };


    }
    @Override
    public void onStart(){
        super.onStart();
       // firebaseAuth.addAuthStateListener(mListener);



    }
    @Override
    public void onStop() {
        super.onStop();
        if (mListener != null) {
            firebaseAuth.removeAuthStateListener(mListener);
        }
    }

    private void verifyUser(){
        final String email = Emailid.getText().toString().trim();
        final String pass = password.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Enter emailId",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this,"enter password",Toast.LENGTH_SHORT).show();
            return;
        }
        pbar.setVisibility(View.VISIBLE);

        firebaseAuth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this,"Login success....please wait redirected to Database page",Toast.LENGTH_SHORT).show();
                            Intent intent =  new Intent(Login.this,Database.class);
                            intent.putExtra("EmailId",email);
                            intent.putExtra("Password",pass);
                            startActivity(intent);
                            }
                            else {
                           // Toast.makeText(Login.this,"Login failed , please verify email id and password and retry",Toast.LENGTH_SHORT).show();
                           // Toast.makeText(Login.this,"if not registered, please signup",Toast.LENGTH_SHORT).show();
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidUserException e) {
                                Toast.makeText(Login.this,"Invalid Emaild Id",Toast.LENGTH_SHORT).show();
                               // mStatusTextView.requestFocus();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(Login.this,"email :" + email,Toast.LENGTH_SHORT).show();
                              Toast.makeText(Login.this,"Invalid Password",Toast.LENGTH_SHORT).show();
                               // mStatusTextView.requestFocus();
                            } catch (FirebaseNetworkException e) {
                                Toast.makeText(Login.this,"error_message_failed_sign_in_no_network",Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Toast.makeText(Login.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(Login.this,"signInWithEmail:failed", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(LoginActivity.this, R.string.login_error,
                                  //  Toast.LENGTH_SHORT).show();
                            //updateUI(null);

                        }
                        pbar.setVisibility(View.GONE);
                    }

                });



    }

    @Override
    public void onClick(View v) {
        verifyUser();
    }
}
