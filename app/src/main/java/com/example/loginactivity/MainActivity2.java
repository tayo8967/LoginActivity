package com.example.loginactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class MainActivity2 extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText nameEditText, emailEditText, passwordEditText;
    Button registerButton, returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        nameEditText = findViewById(R.id.editTextTextName);
        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = findViewById(R.id.editTextTextPassword);
        registerButton = findViewById(R.id.registerButton);
        returnButton = findViewById(R.id.returnButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                String nameInput = nameEditText.getText().toString();
                String emailInput = emailEditText.getText().toString();
                String passwordInput = passwordEditText.getText().toString();

                if(!nameInput.isEmpty() && !emailInput.isEmpty() && !passwordInput.isEmpty()){
                    RegisterUser registerUser = new RegisterUser(nameInput, emailInput, passwordInput);
                    new Thread(registerUser).start();
                }
                else{
                    Toast.makeText(MainActivity2.this, "Please check for empty fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
               finish();
           }
        });
    }

    class RegisterUser implements Runnable {
        String nameInput, emailInput, passwordInput;

        RegisterUser(String nameInput, String emailInput, String passwordInput){
            this.nameInput = nameInput;
            this.emailInput = emailInput;
            this.passwordInput = passwordInput;
        }

        @Override
        public void run(){
            HashMap<String, Object> user = new HashMap<>();
            user.put("name", nameInput);
            user.put("email", emailInput);
            user.put("password", passwordInput);

            db.collection("User")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference){
                            Toast.makeText(MainActivity2.this, "Successfully created account!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity2.this, "No connection!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}