package tech.rtsproduction.chitchat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddFriendActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText codeEdit;
    Button submitBtn;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String uidReciever = "";
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        toolbar = findViewById(R.id.toolbarAddFriend);
        codeEdit = findViewById(R.id.codeEditAddFriend);
        submitBtn = findViewById(R.id.submitBtnAddFriend);
        progressBar = findViewById(R.id.progressBarAddFriend);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = codeEdit.getText().toString();

                if(TextUtils.isEmpty(code)){
                    Toast.makeText(AddFriendActivity.this, "Please Enter Code", Toast.LENGTH_SHORT).show();
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    findToken(code);
                }
            }
        });
    }

    private void findToken(String code){
        final String toFindcode = code;
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot x:dataSnapshot.getChildren()){
                    if(x.child("CCCode").getValue().toString().equalsIgnoreCase(toFindcode)){
                        Log.e("Found","Code Found");
                        uidReciever = x.getKey();
                        Log.e("UID",uidReciever);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
