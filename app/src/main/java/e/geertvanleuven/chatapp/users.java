package e.geertvanleuven.chatapp;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class users extends Fragment {


    //FIREBASE
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Users");

    private NoteAdpater adapter;

    private Toolbar mToolbar;

    //LAYOUT
    private CardView mCV_Item;

    private TextView mTV_NAME;

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_users, container, false);




        //LAYOUT
        mCV_Item = (CardView) v.findViewById(R.id.CV_Note_Item);

        final FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        String ID = mUser.getUid();


        notebookRef.document(ID);
        notebookRef.document("name");

        Query query = notebookRef.orderBy("name", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Names_Firestore> options = new FirestoreRecyclerOptions.Builder<Names_Firestore>()
                .setQuery(query, Names_Firestore.class)
                .build();

        adapter = new NoteAdpater(options);

        RecyclerView recyclerView = v.findViewById(R.id.Recycler_View);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new NoteAdpater.onItemmClickListener() {
            @Override
            public void OnItemClick(final DocumentSnapshot documentSnapshot, int position) {
                if (documentSnapshot.exists()) {

                    CharSequence options[] = new CharSequence[]{"Open profile", "Send message"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                    builder.setTitle("Select options");
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //CLICK LISTENERS FOR EACH ITEM
                            if (which == 0){
                                String name = documentSnapshot.getString("name");
                                String user_id = documentSnapshot.getReference().getId();
                                Intent intent = new Intent(users.this.getContext(), Users_Account.class);
                                intent.putExtra("user_id", user_id );
                                intent.putExtra("name", name);
                                startActivity(intent);
                            }

                            if (which == 1){

                                Intent intent = new Intent(users.this.getContext(), ChatActivity.class);
                                String name = documentSnapshot.getString("name");
                                String user_id = documentSnapshot.getReference().getId();
                                intent.putExtra("user_id", user_id );
                                intent.putExtra("name", name);
                                startActivity(intent);

                            }

                        }
                    });

                    builder.show();


                }
            }
        });


        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        adapter.startListening();


    }

    @Override
    public void onStop() {
        super.onStop();

        adapter.stopListening();
    }
    }


