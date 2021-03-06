package com.example.studentmsnew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText login_emailid, login_password;
    private Button loginBtn;
    TextView tvSignUp;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener AuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth= FirebaseAuth.getInstance();
        loginBtn = (Button) findViewById(R.id.loginBtn);
        login_emailid = (EditText) findViewById(R.id.login_emailid);
        login_password = (EditText) findViewById(R.id.login_password);
        tvSignUp=findViewById(R.id.textView);

        AuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    Toast.makeText(LoginActivity.this, "you are logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, SigninActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(LoginActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        };

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = login_emailid.getText().toString();
                String password = login_password.getText().toString();

                if (email.isEmpty()) {
                    login_emailid.setError("Please enter email id");
                    login_emailid.requestFocus();
                } else if (password.isEmpty()) {
                    login_password.setError("Please enter email password");
                    login_password.requestFocus();

                } else if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Find are empty", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && password.isEmpty())) {
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Login error,Please Login Again", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intToHome = new Intent(LoginActivity.this, SigninActivity.class);
                                startActivity(intToHome);
                            }
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this,"Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intSignUp= new Intent(LoginActivity.this,SigninActivity.class);
                startActivity(intSignUp);
            }
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
        firebaseAuth.addAuthStateListener(AuthStateListener);
    }
}






