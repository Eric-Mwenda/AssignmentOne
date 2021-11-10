package com.kimadrian.assignmentone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kimadrian.assignmentone.models.Auth;

public class LoginActivity extends AppCompatActivity {

    EditText usernameEditText, passwordEditText;
    Button loginBtn;
    TextView register;
    ProgressBar progressBar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.loginUserName);
        passwordEditText = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginBtn);
        register = findViewById(R.id.registerHere);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                databaseReference.child(username).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Log.d(TAG, "onDataChange: "+ snapshot.child("userEmail").getValue());
                            String email = (String) snapshot.child("userEmail").getValue();
                        //Log.d(TAG, "onDataChange: "+ snapshot.child("userEmail").getValue());
                            //Login
                            mAuth.signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()){
                                                progressBar.setVisibility(View.INVISIBLE);
                                                //Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(intent);
                                            } else {
                                                progressBar.setVisibility(View.INVISIBLE);
                                                Toast.makeText(getApplicationContext(), "Error logging in", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getApplicationContext(), "Error:"+e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
//                        } else {
//                            progressBar.setVisibility(View.INVISIBLE);
//                            Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_SHORT).show();
//                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d(TAG, "onCancelled: "+error.toString());
                        Toast.makeText(getApplicationContext(), "Error:" +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

                
                



            }
        });
    }
}