package com.kimadrian.assignmentone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kimadrian.assignmentone.models.Auth;

import java.util.Date;

public class SignUpActivity extends AppCompatActivity {

    EditText userEmailEditText, usernameEditText, passwordEditText;
    Button signUpBtn;
    ProgressBar progressBar;

    Auth auth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    private static final String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userEmailEditText = findViewById(R.id.signUpEmail);
        usernameEditText = findViewById(R.id.signUpUsername);
        passwordEditText = findViewById(R.id.signUpPassword);
        signUpBtn = findViewById(R.id.registerBtn);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        mAuth = FirebaseAuth.getInstance();

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                Date date = new Date();
                String updateTime = String.valueOf(date.getTime());

                String userEmail, username, password;
                userEmail = userEmailEditText.getText().toString().trim();
                username = usernameEditText.getText().toString().trim();
                password = passwordEditText.getText().toString().trim();
                auth = new Auth(userEmail,username);

                mAuth.createUserWithEmailAndPassword(userEmail, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Log.d(TAG, "createUserWithEmail: Success");
                                    //Save user details

                                    databaseReference.child(username).setValue(auth).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            progressBar.setVisibility(View.INVISIBLE);
                                            Toast.makeText(getApplicationContext(), "You've been registered successfully", Toast.LENGTH_SHORT).show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressBar.setVisibility(View.INVISIBLE);
                                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                                            Log.w(TAG, "setValue:"+e.toString());
                                        }
                                    });
                                } else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });






            }
        });
    }


}