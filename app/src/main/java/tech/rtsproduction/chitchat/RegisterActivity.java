package tech.rtsproduction.chitchat;

import android.content.Intent;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class RegisterActivity extends AppCompatActivity {

    Toolbar toolbarRegister;
    Button createBtnRegister;
    EditText nameEdit, emailEdit, passwordEdit;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ProgressBar progressBar;

    public final static String TAG = "REGISTER ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //FIND VIEWS HERE
        toolbarRegister = findViewById(R.id.toolbarRegister);
        createBtnRegister = findViewById(R.id.createBtnRegister);
        nameEdit = findViewById(R.id.displayNameEdit);
        emailEdit = findViewById(R.id.emailEdit);
        passwordEdit = findViewById(R.id.passwordEdit);
        progressBar = findViewById(R.id.progressBarRegister);

        //VARIABLES INITLIZATION HERE
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");

        setSupportActionBar(toolbarRegister);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //CREATE BUTTON SET ON CLICK LISTENER
        createBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEdit.getText().toString();
                String email = emailEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                if (!TextUtils.isEmpty(name) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(name, email, password);
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(RegisterActivity.this, "Please Fill All Details", Toast.LENGTH_SHORT).show();
                }
            }
        });//CREATEBTN ONCLICKLISTENER
    }

    //USER DEFINED FUNCTIONS

    //REGISTERING USER HERE VIA FIREBASE AUTH
    private void registerUser(final String name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            //USER PROFILE CHANGE TO SET NAME OF USER
                            UserProfileChangeRequest nameChange = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                            user.updateProfile(nameChange).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.e("USER PROFILE UPDATE", "Name Changed");
                                    }
                                }
                            });
                            //ADDING DATA TO DB
                            String uid = user.getUid();
                            DatabaseReference userRef = myRef.child(uid);
                            HashMap<String, String> data = new HashMap<>();
                            data.put("Name", name);
                            data.put("Status", "Hey There,I am using ChitChat");
                            data.put("ImageURL", "Not Now");
                            userRef.setValue(data);
                            //PUSHING USER TO MAIN ACTIVITY
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });//CreateUserWithEmailAndPassword
    }//REGISTERUSER
}
