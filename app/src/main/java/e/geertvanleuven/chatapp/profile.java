package e.geertvanleuven.chatapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 */
public class profile extends Fragment {


    public profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        final TextView mTV_Name;

        FirebaseUser mUser = mAuth.getInstance().getCurrentUser();
        String user_id = mUser.getUid();

        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

        mTV_Name = (TextView) view.findViewById(R.id.TV_Name_Profile);

        mFirestore.collection("Users").document(user_id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String name = documentSnapshot.getString("name");

                mTV_Name.setText(name);

            }
        });


        return view;
    }

}
