package com.example.studentmsnew;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private EditText login_emailid;
    private EditText login_password;
    FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        progressDialog =new ProgressDialog(this);

        button=(Button) findViewById(R.id.button);
        login_emailid=(EditText) findViewById(R.id.login_emailid);
        login_password=(EditText) findViewById(R.id.login_password);

        button.setOnClickListener(this);
        login_emailid.setOnClickListener(this);
        login_password.setOnClickListener(this);
    }

    private void registerUser(){
       String email= login_emailid.getText().toString().trim();
       String password= login_password.getText().toString().trim();

       if(TextUtils.isEmpty(email)){
           Toast.makeText(this,"Please enter Email", Toast.LENGTH_SHORT).show();
           return;
       }
       if(TextUtils.isEmpty(password)){
           Toast.makeText(this,"Please enter Password", Toast.LENGTH_SHORT).show();
           return;
       }
       progressDialog.setMessage("Register user..");
       progressDialog.show();

       firebaseAuth.createUserWithEmailAndPassword(email,password)
               .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SigninActivity.this,"Register Success",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SigninActivity.this,"Register fail",Toast.LENGTH_SHORT).show();

                        }
                   }
               });
    }


    @Override
    public void onClick(View v) {
        if (v == button) {
            registerUser();
        }
    }
}
