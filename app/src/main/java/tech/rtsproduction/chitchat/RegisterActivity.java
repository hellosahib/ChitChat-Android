package tech.rtsproduction.chitchat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class RegisterActivity extends AppCompatActivity {

    Toolbar toolbarRegister;
    Button createBtnRegister;
    EditText nameEdit, emailEdit, passwordEdit;
    FirebaseAuth mAuth;

    public final static String TAG = "REGISTER ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbarRegister = findViewById(R.id.toolbarRegister);
        createBtnRegister = findViewById(R.id.createBtnRegister);
        nameEdit = findViewById(R.id.displayNameEdit);
        emailEdit = findViewById(R.id.emailEdit);
        passwordEdit = findViewById(R.id.passwordEdit);

        mAuth = FirebaseAuth.getInstance();

        setSupportActionBar(toolbarRegister);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        createBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser(nameEdit.getText().toString(), emailEdit.getText().toString(), passwordEdit.getText().toString());
            }
        });


    }

    private void registerUser(String name, String email, String password) {
        final String userName = name;
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            UserProfileChangeRequest nameChange = new UserProfileChangeRequest.Builder().setDisplayName(userName).build();
                            user.updateProfile(nameChange).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Log.e("USER PROFILE UPDATE","Name Changed");
                                    }
                                }
                            });
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });//CreateUserWithEmailAndPassword
    }
}
