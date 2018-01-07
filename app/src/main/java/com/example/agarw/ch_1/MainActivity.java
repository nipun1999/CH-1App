package com.example.agarw.ch_1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private   EditText name,password,email,roomno;
    private Button register;
    private DatabaseReference mdatabaseref;
    private FirebaseAuth mAuth;

    private ProgressDialog mProgress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText)(findViewById(R.id.nameedit));
        password = (EditText)(findViewById(R.id.passwordedit));
        email = (EditText)(findViewById(R.id.emailedit));
        roomno = (EditText)(findViewById(R.id.rooomedit));
        register = (Button)(findViewById(R.id.registerbutton));
        mProgress = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();



        mdatabaseref = FirebaseDatabase.getInstance().getReference().child("Users");

        name.requestFocus();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startregistration();
            }
        });




    }

    private void startregistration() {
        mProgress.setMessage("Registering");
        mProgress.show();



        final String namevalue = name.getText().toString().trim();
        final String emailvalue = email.getText().toString().trim().toLowerCase();
        final String passwordvalue = password.getText().toString().trim();
        final String roomvalue = roomno.getText().toString().trim();

        if(!TextUtils.isEmpty(namevalue) && !TextUtils.isEmpty(passwordvalue) && !TextUtils.isEmpty(emailvalue) && !TextUtils.isEmpty(roomvalue)){

            mAuth.createUserWithEmailAndPassword(emailvalue,passwordvalue).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        mProgress.dismiss();
                        String user_id = mAuth.getCurrentUser().getUid();
                       DatabaseReference currentuser =  mdatabaseref.child(user_id);
                        currentuser.child("name").setValue(namevalue);
                        currentuser.child("room no").setValue(roomvalue);

                        final FirebaseUser user = mAuth.getCurrentUser();

                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){

                                    Toast.makeText(MainActivity.this,
                                            "Verification email sent to " + user.getEmail() + " Please verify it, to proceed further",
                                            Toast.LENGTH_SHORT).show();
                                }else{

                                    Toast.makeText(MainActivity.this,
                                            "Failed to send verification email id. Please try again", Toast.LENGTH_LONG).show();

                                }

                            }
                        });



                        Intent home = new Intent(MainActivity.this,Home.class);
                        home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(home);










                    }else{
                        Toast.makeText(MainActivity.this,
                                "Failed.Please try again", Toast.LENGTH_LONG).show();
                        mProgress.dismiss();

                    }
                }
            });

        }
    }
}
