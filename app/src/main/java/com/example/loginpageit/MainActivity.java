package com.example.loginpageit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button Map;
    TextView tvDisplayMailId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        tvDisplayMailId = findViewById(R.id.tvDisplayEmailId);

        Button btnLogOut = findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(v -> logoutUser());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("users").child(currentUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    tvDisplayMailId.setText("Mail Id : " + user.mailId);
                    Button bP1= (Button)findViewById(R.id.btnProf1);
                    bP1.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            // click handling code
                            Intent intent = new Intent(MainActivity.this, tP1.class);
                            startActivity(intent);
                        }

                    });
                    Button bP2= (Button)findViewById(R.id.btnProf2);
                    bP2.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            // click handling code
                            Intent intent = new Intent(MainActivity.this, tP2.class);
                            startActivity(intent);
                        }

                    });
                    Button bP3= (Button)findViewById(R.id.btnProf3);
                    bP3.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            // click handling code
                            Intent intent = new Intent(MainActivity.this, tP3.class);
                            startActivity(intent);
                        }

                    });
                    Button bP4= (Button)findViewById(R.id.btnProf4);
                    bP4.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            // click handling code
                            Intent intent = new Intent(MainActivity.this, tP4.class);
                            startActivity(intent);
                        }

                    });
                    Map = findViewById(R.id.btnMap);
                    Map.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            gotoUrl("https://www.google.com/maps/place/National+Institute+of+Technology+Karnataka+Surathkal./@13.0107776,74.7921203,17z/data=!3m1!4b1!4m5!3m4!1s0x3ba35211b768ac8f:0x6b1144ac2d5dadf3!8m2!3d13.0107776!4d74.794309");
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
        private void gotoUrl(String S){
            Uri uri = Uri.parse(S);
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }

        private void logoutUser(){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }