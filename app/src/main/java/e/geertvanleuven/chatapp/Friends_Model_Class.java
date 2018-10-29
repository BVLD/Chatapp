package e.geertvanleuven.chatapp;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class Friends_Model_Class {

    String date;

    public Friends_Model_Class(){

    }

    public Friends_Model_Class(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
