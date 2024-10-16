package com.globalhackathon.nextlyf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.globalhackathon.nextlyf.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {


    ActivityLoginBinding loginBinding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        currentUser = mAuth.getCurrentUser();

        loginBinding.loginLayout.setVisibility(View.GONE);

        loginBinding.loginCheckProgressBar.setIndeterminate(true);
        loginBinding.loginCheckProgressBar.setVisibility(View.GONE);

        loginBinding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginBinding.emailFieldText.getText().toString();
                String password = loginBinding.passwordFieldText.getText().toString();

                if (email.isEmpty()) {
                    loginBinding.emailField.setError("Email is required");
                } else if (password.isEmpty()) {
                    loginBinding.passwordField.setError("Password is required");
                } else {
                    loginBinding.loginConnectProgressBar.setVisibility(View.VISIBLE);
                    loginBinding.loginConnectProgressBar.setIndeterminate(true);
                    loginBinding.emailField.setError(null);
                    loginBinding.passwordField.setError(null);
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

                                        FirebaseUser user = mAuth.getCurrentUser();

//                                        getUserDetails(user);

                                        //updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        loginBinding.emailField.setError("Please enter correct email..");
                                        loginBinding.passwordField.setError("Please enter correct password..");
                                        loginBinding.loginConnectProgressBar.setVisibility(View.GONE);
                                        loginBinding.loginConnectProgressBar.setIndeterminate(false);
                                    }
                                }
                            });
                }
            }
        });

        loginBinding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            Toast.makeText(this, "User already logged in", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//            finish();
        } else {
            loginBinding.loginLayout.setVisibility(View.VISIBLE);
        }
    }


}