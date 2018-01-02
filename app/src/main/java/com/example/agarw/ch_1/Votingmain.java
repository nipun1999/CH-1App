package com.example.agarw.ch_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class Votingmain extends AppCompatActivity {

    private RecyclerView mvotinglist;
    private DatabaseReference mdatabase;
    private DatabaseReference mdatabasevote;
    private DatabaseReference mdatabasevotecount;
    private FirebaseAuth mAuth;
    private int votecount;
    private int votecountnew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votingmain);

        mvotinglist = (RecyclerView)(findViewById(R.id.vote_list));
        mvotinglist.setHasFixedSize(true);
        mvotinglist.setLayoutManager(new LinearLayoutManager(this));
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Voting Options");
        mdatabasevotecount = FirebaseDatabase.getInstance().getReference().child("Voting Count");
        mdatabase.keepSynced(true);
        mdatabasevote = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();










    }



    public static class BlogViewHolder extends RecyclerView.ViewHolder {
        View mview;
        Button dovote;

        public BlogViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
            dovote = (Button)(mview.findViewById(R.id.buttonoption));
        }


        public void setOption(String option ) {
            TextView post_desc = (TextView) (mview.findViewById(R.id.optiontext));
            post_desc.setText(option);
        }

        public void setNumber(String number){
            TextView post_numb = (TextView)(mview.findViewById(R.id.optionnumber));
            post_numb.setText(number);
        }
    }




    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Voteget, BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Voteget, BlogViewHolder>(
                Voteget.class,
                R.layout.votingrow,
                BlogViewHolder.class,
                mdatabase



        ) {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, Voteget model, int position) {
                viewHolder.setOption(model.getOption());
                viewHolder.setNumber(model.getNumber());
                final String post_key = getRef(position).getKey();
                final String user_id = mAuth.getCurrentUser().getUid().toString();

                viewHolder.dovote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        mdatabasevote.child("Voting Data").child(user_id).child("votingoption").setValue(post_key);
                        mdatabasevote.child("Voting status").child(user_id).setValue("true");
                        Intent home = new Intent(Votingmain.this,Home.class);
                        home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(home);

                        mdatabasevotecount.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                votecount = dataSnapshot.child(post_key).getValue(Integer.class);
                                votecountnew = votecount + 1;
                                mdatabasevotecount.child(post_key).setValue(votecountnew);
                                Toast.makeText(Votingmain.this,
                                        "Thank You for voting we love you", Toast.LENGTH_LONG).show();




                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });









                    }
                });




            }
        };

        mvotinglist.setAdapter(firebaseRecyclerAdapter);
    }






}
