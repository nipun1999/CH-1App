package com.example.agarw.ch_1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private Button login,registerpage;
    private EditText emailtext,passwordtext;
    private FirebaseAuth mAuth;
    private ProgressDialog mprogress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailtext = (EditText)(findViewById(R.id.emailtext));
        passwordtext = (EditText)(findViewById(R.id.passwordtext));
        login = (Button)(findViewById(R.id.loginbutton));
        mAuth = FirebaseAuth.getInstance();
        mprogress = new ProgressDialog(this);
        registerpage = (Button)(findViewById(R.id.registerbutton));

        registerpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent home = new Intent(Login.this,MainActivity.class);
                home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home);

            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checklogin();

            }
        });
    }

    private void checklogin() {
        mprogress.setMessage("Logging in");
        mprogress.show();

      String  emailvalue = emailtext.getText().toString().trim();
       String passwordvalue = passwordtext.getText().toString().trim();

        if(!TextUtils.isEmpty(emailvalue) && !TextUtils.isEmpty(passwordvalue)){
            mAuth.signInWithEmailAndPassword(emailvalue,passwordvalue).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Intent home = new Intent(Login.this,Home.class);
                        home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(home);
                        mprogress.dismiss();

                    }else{
                        Toast.makeText(Login.this,
                                "Failed.Please try again", Toast.LENGTH_LONG).show();
                        mprogress.dismiss();

                    }
                }
            });

        }else{
            Toast.makeText(Login.this,
                    "Please, fill all the fields carefully", Toast.LENGTH_LONG).show();
            mprogress.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        Intent home = new Intent(Login.this,Login.class);
        home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home);

        // Do Here what ever you want do on back press;
    }



}
