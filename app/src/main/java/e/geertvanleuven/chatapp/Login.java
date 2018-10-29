package e.geertvanleuven.chatapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    //LAYOUT
    private EditText mET_email;
    private EditText mET_Password;

    private TextView mTV_NEW_ACC;

    private Button mBtn_Login;

    //FIREBASE AUTHENTICATION
    private FirebaseAuth mAuth;

    //FIREBASE FIRESTORE
    private FirebaseFirestore mFireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //FIREBASE AUTHENTICATION
        mAuth = FirebaseAuth.getInstance();

        //FIREBASE FIRESTORE
        mFireStore = FirebaseFirestore.getInstance();

        //LAYOUT

        mET_email = (EditText) findViewById(R.id.ET_Email_Login);
        mET_Password = (EditText) findViewById(R.id.ET_Password_Login);

        mTV_NEW_ACC = findViewById(R.id.TV_NEW_ACC_Login);


        mBtn_Login = findViewById(R.id.Btn_Login_Login);


        //ON_CLICK_LISTENERS


        mTV_NEW_ACC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();

            }
        });


        mBtn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LOGIN();

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(Login.this, Home.class);
            startActivity(intent);
        }
    }

    private void LOGIN() {

        String email = mET_email.getText().toString().trim();
        String password = mET_Password.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }



            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                String name = mAuth.getCurrentUser().getDisplayName();


                                String token_id = FirebaseInstanceId.getInstance().getToken();
                                String current_id = mAuth.getCurrentUser().getUid();

                                Map<String, Object> tokenMap = new HashMap<>();

                                tokenMap.put("token_id", token_id);


                                mFireStore.collection("Users").document(current_id).update(tokenMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Intent intent = new Intent(Login.this, Home.class);
                                        startActivity(intent);
                                        finish();

                                    }
                                });

                            /*
                            //Toast.makeText(Login.this, "Login succesfull", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, Home.class);
                            startActivity(intent);
                            finish();
                            */

                            } else {
                                Toast.makeText(Login.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }


                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });






    }
}

