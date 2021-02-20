package com.example.fbapp;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText mEmail,mPass;
    Button Login,Register;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.password);

        mPass = findViewById(R.id.email);

        Login = findViewById(R.id.Login);

        Register = findViewById(R.id.Register);

        Login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                String email=mEmail.getText().toString();
                String pass = mPass.getText().toString();
                if(TextUtils.isEmpty(email))
                {
                    mEmail.setError("Requires Email");
                    return ;
                }
                if(TextUtils.isEmpty(pass))
                {
                    mPass.setError("Requires Password");
                    return ;
                }
                if(fAuth.getCurrentUser()!=null)
                {
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();

                }

                if(pass.length()<6)
                {
                    mPass.setError("requires more than 6 digits");
                    return ;
                }

                fAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Login.this,"Logged In.",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else
                        {
                            Toast.makeText(Login.this,"Not.",Toast.LENGTH_SHORT).show();
                        }

                    }
                });



            }



        });

            Register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
           });


    }
}









