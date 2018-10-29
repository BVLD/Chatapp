package e.geertvanleuven.chatapp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class Friends extends Fragment {

    //FIREBASE
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Users");

    private Friends_Adapter_Class adapter;

    private Toolbar mToolbar;

    //LAYOUT
    private CardView mCV_Item;

    private TextView mTV_NAME;

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;


    public Friends() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_friends, container, false);

        //LAYOUT
        //mCV_Item = (CardView) v.findViewById(R.id.CV_Note_Item);

        final FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        String ID = mUser.getUid();


        notebookRef.document(ID);
        notebookRef.document("name");

        Query query = notebookRef.orderBy("name", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Friends_Model_Class> options = new FirestoreRecyclerOptions.Builder<Friends_Model_Class>()
                .setQuery(query, Friends_Model_Class.class)
                .build();


        adapter = new Friends_Adapter_Class(options);

        RecyclerView recyclerView = v.findViewById(R.id.RECYCLERVIEWTWP);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
        /*
        adapter.setOnItemClickListener(new NoteAdpater.onItemmClickListener() {
            @Override
            public void OnItemClick(DocumentSnapshot documentSnapshot, int position) {
                if (documentSnapshot.exists()) {
                    String name = documentSnapshot.getString("name");
                    String user_id = documentSnapshot.getReference().getId();
                    Intent intent = new Intent(users.this.getContext(), Users_Account.class);
                    intent.putExtra("user_id", user_id );
                    intent.putExtra("name", name);
                    startActivity(intent);



                }
            }
        });
        */


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