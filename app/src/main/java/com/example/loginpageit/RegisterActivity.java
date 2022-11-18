package com.example.loginpageit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Task<Void> Firebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            finish();
            return;
        }
        Button btnRegister = findViewById(R.id.btnReg);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        TextView txtViewSwitchToLogin = findViewById(R.id.tvLogin);
        txtViewSwitchToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToLogin();
            }
        });
    }
    private void registerUser() {
        EditText EtEmail = findViewById(R.id.etEmail);
        EditText EtPassword = findViewById(R.id.etPassword);

        String mailId = EtEmail.getText().toString();
        String password = EtPassword.getText().toString();
        if (password.length() < 6) {
            EtPassword.setError("Min length must be 6 characters");
            EtPassword.requestFocus();
            return;
        }
        if (mailId.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(mailId, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    System.out.println(Objects.requireNonNull(task.getException()));
                    Toast.makeText(RegisterActivity.this, task.getException().toString(), Toast.LENGTH_LONG).show();
                } else {
                    User user = new User(mailId);
                    Firebase = FirebaseDatabase.getInstance().getReference("users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                        showMainActivity();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
//                            Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
//                            showMainActivity();
//                            User user = new User(mailId);
//                            FirebaseDatabase.getInstance().getReference("users")
//                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            showMainActivity();
//                                        }
//                                    });
//                            RegisterActivity.this.startActivity(new Intent(RegisterActivity.this, MainActivity.class));
//                            RegisterActivity.this.finish();
//                        }
//                    }
//                });
//            mAuth.createUserWithEmailAndPassword(mailId, password)
//                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                showMainActivity();
//                            } else {
//                                Toast.makeText(RegisterActivity.this, "Authentication Failed.", Toast.LENGTH_LONG).show();
//                            }
//                            Log.d(TAG, "New user registration: " + task.isSuccessful());

//                            if (!task.isSuccessful()) {
//                                System.out.println(task.getException().toString());
//                                Toast.makeText(RegisterActivity.this, task.getException().toString(), Toast.LENGTH_LONG).show();
//                            } else {
//                                RegisterActivity.this.startActivity(new Intent(RegisterActivity.this, MainActivity.class));
//                                RegisterActivity.this.finish();
//                            }
//
//                        }
//                    });

    private void showMainActivity(){
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void switchToLogin(){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}