package e.geertvanleuven.chatapp;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<messages> mMessageList;
    private DatabaseReference mUserDatabase;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    //cprivate FirebaseFirestore mFirestore;


    public MessageAdapter(List<messages> mMessageList) {

        this.mMessageList = mMessageList;

    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_single_layout, parent, false);

        return new MessageViewHolder(v);

    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView messageText;
        public TextView displayName;
        //public ImageView messageImage;

        public MessageViewHolder(View view) {
            super(view);

            messageText = (TextView) view.findViewById(R.id.message_text_layout);
            displayName = (TextView) view.findViewById(R.id.TV_Name_message_Single_Layout);
            //messageImage = (ImageView) view.findViewById(R.id.message_image_layout);

        }
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder viewHolder, int i) {

        mAuth = FirebaseAuth.getInstance();




        String current_user_id = mAuth.getCurrentUser().getUid();
        messages c = mMessageList.get(i);

        viewHolder.messageText.setText(c.getMessage());

        mFirestore = FirebaseFirestore.getInstance();

        mFirestore = FirebaseFirestore.getInstance();

        String from_user = c.getFrom();




            mFirestore.collection("Users").document(from_user).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {


                    String name = null;
                    if (documentSnapshot != null) {
                        name = Objects.requireNonNull(documentSnapshot.get("name")).toString();
                    }

                    viewHolder.displayName.setText(name);



                }
            });








        mFirestore = FirebaseFirestore.getInstance();


        //String message_type = c.getType();

        if (from_user.equals(current_user_id)) {

            viewHolder.messageText.setBackgroundColor(Color.WHITE);
            viewHolder.messageText.setTextColor(Color.BLACK);

        } else {

            viewHolder.messageText.setBackgroundResource(R.drawable.message_text_background);
            viewHolder.messageText.setTextColor(Color.WHITE);

        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }


}
