package com.example.agarw.ch_1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private  FirebaseAuth.AuthStateListener mAuthstatelistener;
    private Button signout,voting,pastaward,upcomingaward,complaint,playroom,awards,complaintbutton;
    private DatabaseReference mdatabasevotingstatus;
    private boolean verify = false;
  // private boolean emailVerified = true;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        signout = (Button) (findViewById(R.id.signoutbutton));
        awards = (Button) (findViewById(R.id.button16));
        complaintbutton = (Button) (findViewById(R.id.complaintbutton));

        mAuth = FirebaseAuth.getInstance();


        final FirebaseUser user = mAuth.getCurrentUser();
        // emailVerified = user.isEmailVerified();


        mAuthstatelistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {


                    Intent home = new Intent(Home.this, Login.class);
                    home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(home);


                }

                /**  if(user!=null &&  emailVerified == false){
                 Intent home1 = new Intent(Home.this,Login.class);
                 home1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 startActivity(home1);
                 Toast.makeText(Home.this,
                 "Please verify your email first", Toast.LENGTH_SHORT).show();

                 } **/


            }


        };

        complaintbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent home = new Intent(Home.this, Complaints.class);
                home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home);

            }
        });


        mAuth.addAuthStateListener(mAuthstatelistener);

        awards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent home = new Intent(Home.this, Awards.class);
                home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home);

            }
        });


        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });


    }






private void logout() {
        mAuth.signOut();

    }

}
