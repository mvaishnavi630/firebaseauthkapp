package com.example.fbapp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText firstName,LastName,emailAd,password;
   Button Register;
   Button login;
   FirebaseAuth fAuth;
   FirebaseFirestore fStore;
   String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firstName=findViewById(R.id.FirstName);
        LastName=findViewById(R.id.LastName);
        emailAd=findViewById(R.id.EmailAddress);
        password=findViewById(R.id.TextPassword);
        Register=findViewById(R.id.Register);
        login = findViewById(R.id.Login);
        fAuth =FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        Register.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                String email=emailAd.getText().toString();
                String pass = password.getText().toString();
                String fName=firstName.getText().toString();
                String lName=LastName.getText().toString();
                String fullName = fName+" "+lName;

                if(TextUtils.isEmpty(email))
                {
                    emailAd.setError("Requires Email");
                    return ;
                }
                if(TextUtils.isEmpty(pass))
                {
                    password.setError("Requires Password");
                    return ;
                }
                if(fAuth.getCurrentUser()!=null)
                {
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();

                }

                  if(pass.length()<6)
                  {
                      password.setError("requires more than 6 digits");
                      return ;
                  }
                  fAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {


                            Toast.makeText(Register.this,"User Created.",Toast.LENGTH_SHORT).show();
                            userID=fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference=fStore.collection("users").document(userID);
                            Map<String,Object> user=new HashMap<String,Object>();
                            user.put("fullName",fullName);
                            user.put("Email",email);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("Tag","user created for"+userID);
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else
                        {
                            Toast.makeText(Register.this,"User Not Created.",Toast.LENGTH_SHORT).show();
                        }

                      }
                  });

            }
        });



        login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {

                startActivity(new Intent(getApplicationContext(),Login.class));
                 finish();
            }
        });


    }



}