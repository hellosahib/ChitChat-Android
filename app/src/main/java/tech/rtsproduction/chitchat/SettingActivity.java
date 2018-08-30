package tech.rtsproduction.chitchat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity {

    CircleImageView profileImage;
    Button changeImageBtn, changeStatusBtn;
    TextView userName, userStatus;
    FirebaseDatabase database;
    DatabaseReference myref;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        profileImage = findViewById(R.id.userImageSetting);
        changeImageBtn = findViewById(R.id.changeImageBtnSetting);
        changeStatusBtn = findViewById(R.id.changeStatusBtnSetting);
        userName = findViewById(R.id.userNameTextSetting);
        userStatus = findViewById(R.id.userStatusTextSetting);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        boolean chatIntent = getIntent().getBooleanExtra("ChatIntent",false);
        editableUI(!chatIntent);
        /*
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userName.setText(dataSnapshot.child("Name").getValue().toString());
                userStatus.setText(dataSnapshot.child("Status").getValue().toString());
                //TODO PARSE THE URL AND UPDATE IMAGE
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        */



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void editableUI(boolean editable){
        if(editable){
            myref = database.getReference("Users").child(mAuth.getCurrentUser().getUid());
            changeStatusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(SettingActivity.this, StatusActivity.class));
                }
            });
        }else{
            changeImageBtn.setVisibility(View.INVISIBLE);
            changeStatusBtn.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Chat Intent Recieved", Toast.LENGTH_SHORT).show();
        }
    }


}
