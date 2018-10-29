package e.geertvanleuven.chatapp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {

    private ViewPager mViewPager;
    private SectionPageAdapter mSectionPagerAdapter;

    private TabLayout mTabLayout;

    //TOOLBAR
    private Toolbar mToolabar;

    //FIREBASE FIRSTORE
    private FirebaseFirestore mFirestore;


    //FIREBASE AUTH
    private FirebaseAuth mAuth;

    //FIREBASE USER
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();


        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("Street runner");


        mFirestore = FirebaseFirestore.getInstance();


        //TABS
        mViewPager = (ViewPager) findViewById(R.id.Viewpager);
        mSectionPagerAdapter = new SectionPageAdapter(getSupportFragmentManager());


        mViewPager.setAdapter(mSectionPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.main_Tabs);


        mTabLayout.setupWithViewPager(mViewPager);





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Logout:
                LOGOUT();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void LOGOUT() {

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(Home.this, Login.class);
        startActivity(intent);
        finish();

        final String mUserId = mUser.getUid();

        Map<String, Object> tokenMapRemove = new HashMap<>();

        tokenMapRemove.put("token_id", FieldValue.delete());

        mFirestore.collection("Users").document(mUserId).update(tokenMapRemove).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {



            }
        });



    }
    }

