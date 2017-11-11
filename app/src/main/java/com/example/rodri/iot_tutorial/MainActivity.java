package com.example.rodri.iot_tutorial;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements ValueEventListener,View.OnClickListener {

    private TextView StateText;
    private RadioButton RbOn,RbOff;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference = firebaseDatabase.getReference();
    private DatabaseReference mStateReference = mRootReference.child("lampada");


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            StateText = (TextView) findViewById(R.id.message_text);
            RbOn = (RadioButton) findViewById(R.id.rb_on);
            RbOff = (RadioButton) findViewById(R.id.rb_off);

            RbOn.setOnClickListener(this);
            RbOff.setOnClickListener(this);
        }


    public void onRadioButtonClicked(View view){

        switch (view.getId()){

            case R.id.rb_on:
                mStateReference.setValue("on");
                break;
            case R.id.rb_off:
                mStateReference.setValue("off");
                break;


        }
    }


    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if(dataSnapshot.getValue(String.class) != null){

            String key = dataSnapshot.getKey();

             if(key.equals("lampada")){
                String state = dataSnapshot.getValue(String.class);

                if(state.equals("on")){
                    StateText.setTextColor(ContextCompat.getColor(this,R.color.colorYellow));
                    StateText.setText(state);
                    RbOn.setChecked(true);
                }
                else if(state.equals("off")){
                    StateText.setTextColor(ContextCompat.getColor(this,R.color.colorBlack));
                    StateText.setText(state);
                    RbOff.setChecked(true);
                }

            }
        }

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }



    @Override
    protected void onStart(){
        super.onStart();
        mStateReference.addValueEventListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.rb_on:
                onRadioButtonClicked(view);
                break;
            case R.id.rb_off:
                onRadioButtonClicked(view);
                break;

        }

    }

}
