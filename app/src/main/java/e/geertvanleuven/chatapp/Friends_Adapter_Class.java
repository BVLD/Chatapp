package e.geertvanleuven.chatapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class Friends_Adapter_Class extends FirestoreRecyclerAdapter<Friends_Model_Class, Friends_Adapter_Class.ViewhHolder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Friends_Adapter_Class(@NonNull FirestoreRecyclerOptions<Friends_Model_Class> options) {
        super(options);
    }

    @NonNull
    @Override
    public ViewhHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_item, viewGroup, false);
        return new ViewhHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewhHolder holder, int position, @NonNull Friends_Model_Class model) {
        holder.Name_Friend.setText(model.getDate());
    }

    class ViewhHolder extends RecyclerView.ViewHolder {

        private TextView Name_Friend;


        public ViewhHolder(@NonNull View itemView) {
            super(itemView);

            Name_Friend = (TextView) itemView.findViewById(R.id.text_view_name);
        }
    }{

    }
}
