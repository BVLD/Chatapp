package e.geertvanleuven.chatapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    //LAYOUT

    private EditText mET_Email;
    private EditText mET_Name;
    private EditText mET_Password;


    private TextView mTV_LOGIN;


    private Button mBtn_Register;

    //FIREBASE AUTH
    private FirebaseAuth mAuth;


    //FIREBASE USER
    private FirebaseUser mUser;

    //PROGRESSBAR
    private ProgressBar mProgressBar;

    private FirebaseFirestore mFiretore;

    //FIREBASE FIRESTORE
    private FirebaseFirestore mFireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //FIREBASE FIRSTORE
        mFiretore = FirebaseFirestore.getInstance();


        //FIREBASE AUTH
        mAuth = FirebaseAuth.getInstance();

        mFireStore = FirebaseFirestore.getInstance();


        //LAYOUT

        mET_Email = findViewById(R.id.ET_Email_Register);
        mET_Name = findViewById(R.id.ET_Name_Register);
        mET_Password = findViewById(R.id.ET_Password_Register);


        mTV_LOGIN = findViewById(R.id.TV_Login_Register);


        mBtn_Register = findViewById(R.id.Btn_Register_Register);


        //SET_ON_CLICK_LISTENERS

        mBtn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                REGISTER();

            }
        });

        mTV_LOGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();

            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(Register.this, Home.class);
            startActivity(intent);
        }
    }

    // REGISTER NEW USERS

    private void REGISTER() {
        final String name = mET_Name.getText().toString();
        String email = mET_Email.getText().toString();
        String password = mET_Password.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            mUser = FirebaseAuth.getInstance().getCurrentUser();
                            String user_id = mUser.getUid();


                            Map<String, String> userMap = new HashMap<>();

                            userMap.put("name", name);


                            mFiretore.collection("Users").document(user_id).set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            });




                            String token_id = FirebaseInstanceId.getInstance().getToken();
                            String current_id = mAuth.getCurrentUser().getUid();

                            Map<String, Object> tokenMap = new HashMap<>();

                            tokenMap.put("token_id", token_id);



                            mFireStore.collection("Users").document(current_id).update(tokenMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Intent intent = new Intent(Register.this, Home.class);
                                    startActivity(intent);
                                    finish();

                                }
                            });





                        } else {

                            Toast.makeText(Register.this, "Could not register, please try again", Toast.LENGTH_SHORT).show();


                        }

                    }

                });
    }
}
