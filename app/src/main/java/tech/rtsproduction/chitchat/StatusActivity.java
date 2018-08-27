package tech.rtsproduction.chitchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatusActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText statusEdit;
    Button submitBtn;
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        toolbar = findViewById(R.id.toolbarStatus);
        setSupportActionBar(toolbar);
        statusEdit = findViewById(R.id.statusEdit);
        submitBtn = findViewById(R.id.submitBtnStatus);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        myRef = database.getReference("Users").child(mAuth.getCurrentUser().getUid());

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(statusEdit.getText().toString())) {
                    myRef.child("Status").setValue(statusEdit.getText().toString());
                    finish();
                }
            }
        });
    }
}
