package com.example.loginactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    final String TAG = "FIRESTORE";
    EditText emailEditText, passwordEditText;
    Button loginButton, createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = findViewById(R.id.editTextTextPassword);
        loginButton = findViewById(R.id.loginButton);
        createAccountButton = findViewById(R.id.createAccountButton);

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String emailInput = emailEditText.getText().toString();
                String passwordInput = passwordEditText.getText().toString();

                if(!emailInput.isEmpty() && !passwordInput.isEmpty()){
                    getUserAccount(emailInput, passwordInput);
                }
                else{
                    Toast.makeText(MainActivity.this, "Please enter email and password!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
                openMainActivity2();
           }
        });
    }

    private void openMainActivity2() {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

    private void getUserAccount(String emailInput, String passwordInput) {
        db.collection("User")
                .whereEqualTo("email",emailInput)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()){
                            Toast.makeText(MainActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                            String savedPassword = document.getString("password");

                            if (passwordInput.equals(savedPassword)) {
                                Toast.makeText(MainActivity.this, "Log in successful!", Toast.LENGTH_SHORT).show();
                            }
                            else { //if password is incorrect
                                Toast.makeText(MainActivity.this, "Invalid email or password!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "No connection", Toast.LENGTH_SHORT).show();

                    }
                });

    }
}