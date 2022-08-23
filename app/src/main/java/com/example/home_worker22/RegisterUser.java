package com.example.home_worker22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterUser extends AppCompatActivity {

    TextView signin;
    EditText user_1, username, password;
    Button loginbtn;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        signin = findViewById(R.id.signin);


        loginbtn = findViewById(R.id.loginbtn);


        user_1 = findViewById(R.id.FullName);
        username = findViewById(R.id.email);
        password = findViewById(R.id.Pass);
        progressDialog=new ProgressDialog(this);



        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perforAuth();
            }
        });

    }

    private void perforAuth() {
        String FullName = user_1.getText().toString().trim();
        String email = username.getText().toString().trim();
        String Pass = password.getText().toString().trim();

        if (FullName.isEmpty()) {
            user_1.setError("full name is required!");
            user_1.requestFocus();
            return;
        }
        if (!email.matches(emailPattern))
        {
            username.setError("Enter Connext Email");
        }else if(Pass.isEmpty() || password.length()<6)
        {
            password.setError("Enter proper password");
        }else if (!password.equals(Pass))
        {
            password.setError("Password Not math Both field");
        }else
        {
            progressDialog.setMessage("Please Wait While Registration...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(RegisterUser.this,"Registaration Successful",Toast.LENGTH_SHORT).show();
                    }else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterUser.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    private void sendUserToNextActivity() {
        Intent intent=new Intent(RegisterUser.this,MainActivity.class);
        startActivity(intent);
    }
}