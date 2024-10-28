package com.globalhackathon.nextlyf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.globalhackathon.nextlyf.databinding.ActivityLoginBinding;
import com.globalhackathon.nextlyf.model.UserSignUpData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {


    ActivityLoginBinding loginBinding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    Gson gson;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();
        gson = new Gson();

        loginBinding.loginLayout.setVisibility(View.GONE);

        loginBinding.loginCheckProgressBar.setIndeterminate(true);
        loginBinding.loginCheckProgressBar.setVisibility(View.VISIBLE);

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

        loginBinding.loginLayout.setVisibility(View.GONE);

        currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            loginBinding.loginLayout.setVisibility(View.GONE);
            Toast.makeText(this, "User already logged in", Toast.LENGTH_SHORT).show();
            db.collection("users").document(currentUser.getUid()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        Log.d("LoginActivity", "User data found as: " + task.getResult().toString());
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("userSignUpData", gson.toJson(task.getResult().toObject(UserSignUpData.class)));
                        intent.putExtra("isFromSignUp", false);
                        startActivity(intent);
                        finish();
                    } else {
                        Log.d("LoginActivity", "User data not found");
                        loginBinding.loginLayout.setVisibility(View.VISIBLE);
                    }
                } else {
                    Log.d("LoginActivity", "Error getting user data: ", task.getException());
                    loginBinding.loginLayout.setVisibility(View.VISIBLE);
                }
            });

        } else {
            loginBinding.loginLayout.setVisibility(View.VISIBLE);
            loginBinding.loginCheckProgressBar.setVisibility(View.GONE);
        }
    }


}