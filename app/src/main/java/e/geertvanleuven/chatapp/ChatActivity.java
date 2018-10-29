package e.geertvanleuven.chatapp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private String mChatUser;
    private String name;

    private Toolbar mToolbar;

    private RecyclerView mMessagesList;

    //private TextView mLastSeenView;

    private DatabaseReference mRootref;
    private DatabaseReference mDataRef;


    private FirebaseAuth mAuth;
    private String mCurrentUserId;


    private ImageButton mIB_Send;
    private EditText mET_Message;

    private final List<messages> messagesList = new ArrayList<>();
    private LinearLayoutManager mLinearLayout;
    private MessageAdapter mAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mMessagesList = (RecyclerView) findViewById(R.id.messageList);
        mLinearLayout = new LinearLayoutManager(this);

        mMessagesList.setHasFixedSize(true);
        mMessagesList.setLayoutManager(mLinearLayout);

        mAdapter = new MessageAdapter(messagesList);

        mMessagesList.setAdapter(mAdapter);

        mIB_Send = (ImageButton) findViewById(R.id.IV_SEND_Chat);
        mET_Message = (EditText) findViewById(R.id.ET_Message_Chat);

        mDataRef = FirebaseDatabase.getInstance().getReference().child("messages");

        mAuth = FirebaseAuth.getInstance();
        mCurrentUserId = mAuth.getCurrentUser().getUid();

        mRootref = FirebaseDatabase.getInstance().getReference();

        mToolbar = (Toolbar) findViewById(R.id.chat_app_BAR);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mChatUser = getIntent().getStringExtra("user_id");
        name = getIntent().getStringExtra("name");

        loadMessages();

        getSupportActionBar().setTitle(name);

        // --- Custem Action Bar Items ---------
        //mLastSeenView = (TextView) findViewById(R.id.TV_Last_Seen_View_Toolbar);

        mMessagesList = (RecyclerView) findViewById(R.id.messageList);


        mRootref.child("Chat").child(mCurrentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!dataSnapshot.hasChild(mChatUser)) {

                    Map chatAddMap = new HashMap();
                    chatAddMap.put("seen", false);
                    chatAddMap.put("timestamp", ServerValue.TIMESTAMP);

                    Map chatUserMap = new HashMap();
                    chatUserMap.put("Chat/" + mCurrentUserId + "/" + mChatUser, chatAddMap);
                    chatUserMap.put("Chat/" + mChatUser + "/" + mCurrentUserId, chatAddMap);

                    mRootref.updateChildren(chatUserMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                            if (databaseError != null) {

                                //DISPLAY ERROR IN LOGCAT

                            }

                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //ONCLICKLISTENERS
        mIB_Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SendMessage();

            }
        });


    }

    private void loadMessages() {

        mRootref.child("messages").child(mCurrentUserId).child(mChatUser).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                messages message = dataSnapshot.getValue(messages.class);

                messagesList.add(message);
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void SendMessage() {

        String message = mET_Message.getText().toString();

        if (!TextUtils.isEmpty(message)) {

            String current_user_ref = "messages/" + mCurrentUserId + "/" + mChatUser;
            String chat_user_ref = "messages/" + mChatUser + "/" + mCurrentUserId;

            DatabaseReference user_message_push = mRootref.child("messages").child(mCurrentUserId).child(mChatUser).push();


            String pushID = user_message_push.getKey();

            Map messageMap = new HashMap();
            messageMap.put("message", message);
            messageMap.put("seen", false);
            messageMap.put("type", "text");
            messageMap.put("time", ServerValue.TIMESTAMP);
            messageMap.put("from", mCurrentUserId);// OPGELET!!!!!!!!!!!

            Map messageUserMap = new HashMap();
            messageUserMap.put(current_user_ref + "/" + pushID, messageMap);
            messageUserMap.put(chat_user_ref + "/" + pushID, messageMap);

            mET_Message.setText("");

            mRootref.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {


                }
            });

        }

    }
}
