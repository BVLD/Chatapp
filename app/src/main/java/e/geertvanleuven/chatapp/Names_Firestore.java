package e.geertvanleuven.chatapp;

public class Names_Firestore {

    private String name;



    public Names_Firestore() {
        //Empty constructor needed
    }



    public Names_Firestore(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
