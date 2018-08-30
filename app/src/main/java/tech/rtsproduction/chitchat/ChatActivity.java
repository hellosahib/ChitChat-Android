package tech.rtsproduction.chitchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    ImageButton sendBtn,addBtn;
    Toolbar toolbar;
    EditText chatMsgEdit;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    CircleImageView profileImage;
    TextView profileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        toolbar =  findViewById(R.id.toolbarChat);
        sendBtn = findViewById(R.id.sendBtnChat);
        addBtn = findViewById(R.id.addPhotoBtnChat);
        chatMsgEdit = findViewById(R.id.msgEditChat);
        profileImage = findViewById(R.id.toolbarImageChat);
        profileName = findViewById(R.id.toolbarNameChat);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatActivity.this,SettingActivity.class).putExtra("ChatIntent",true));
            }
        });

    }
}
