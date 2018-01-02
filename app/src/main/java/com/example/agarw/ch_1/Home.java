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
    private Button signout,voting,pastaward,upcomingaward;
    private DatabaseReference mdatabasevotingstatus;
    private boolean verify = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        signout = (Button)(findViewById(R.id.signoutbutton));
        voting = (Button)(findViewById(R.id.votingbutton));
        pastaward = (Button)(findViewById(R.id.pastawardbutton));
        upcomingaward = (Button)(findViewById(R.id.upcomingbutton));


        mAuth = FirebaseAuth.getInstance();






        final FirebaseUser user = mAuth.getCurrentUser();


        mAuthstatelistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null || user.isEmailVerified() == verify){
                    Intent home = new Intent(Home.this,Login.class);
                    home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(home);



                }





            }




        };





        mAuth.addAuthStateListener(mAuthstatelistener);

        voting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkuservote();



            }
        });




        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        pastaward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent home = new Intent(Home.this,PastAwards.class);
                home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home);

            }
        });


        upcomingaward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent home = new Intent(Home.this,UpcomingAwards.class);
                home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home);

            }
        });










    }


    private void checkuservote() {

        String user_id = mAuth.getCurrentUser().getUid();

        mdatabasevotingstatus = FirebaseDatabase.getInstance().getReference().child("Voting status").child(user_id);


        mdatabasevotingstatus.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String value = dataSnapshot.getValue(String.class);
                if(value!= null && value.equals("true")){
                    Toast.makeText(Home.this,
                            "You have already voted", Toast.LENGTH_SHORT).show();


                }else{

                    Intent home = new Intent(Home.this,Votingmain.class);
                    home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(home);



                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(Home.this,
                        "Some error occured, please try again later", Toast.LENGTH_SHORT).show();

            }
        });






    }






private void logout() {
        mAuth.signOut();

    }

}
