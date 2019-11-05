
package com.example.createnote.ui.util;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.createnote.R;
import com.example.createnote.model.Collaborator;
import com.example.createnote.model.Note;
import com.example.createnote.model.NoteDatabaseHandler;
import com.example.createnote.model.User;
import com.example.createnote.sqlite.DatabaseException;

import java.util.ArrayList;
import java.util.List;


/**
 * A dialog showing a list of users from the database
 */
public class AddCollaboratorDialogFragment extends DialogFragment {

    // UI component references
    private RecyclerView collaboratorsRecyclerView;
    private Button doneButton;

    // data source and data adapter
    private List<User> collaborators;
    private List<User> added = new ArrayList<User>();
    private CollaboratorAdapter adapter;
    private long note;

    public AddCollaboratorDialogFragment() {

    }

    public AddCollaboratorDialogFragment(List<User> collaborators, long n) {
        this.collaborators = collaborators;
        this.note=n;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dialog_add_collaborator, container, false);

        //
        collaboratorsRecyclerView = root.findViewById(R.id.collaborators_RecyclerView);
        adapter = new CollaboratorAdapter(added);
        collaboratorsRecyclerView.setAdapter(adapter);
        collaboratorsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        collaboratorsRecyclerView.setHasFixedSize(true);

        doneButton = root.findViewById(R.id.done_Button);
        doneButton.setOnClickListener(v -> dismiss());
        return root;
    }

    /**
     * Curtom adapter to display users
     */
    private class CollaboratorAdapter extends RecyclerView.Adapter<AddCollaboratorDialogFragment.CollaboratorViewHolder> {

        public List<User> allUsers;
        public List<User> added;
        public CollaboratorAdapter(List<User> list){
            allUsers = list;
        }

        @NonNull
        @Override
        public AddCollaboratorDialogFragment.CollaboratorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AddCollaboratorDialogFragment.CollaboratorViewHolder(
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.list_item_add_collaborator, parent, false)
            );
        }

        @Override
        public void onBindViewHolder(@NonNull AddCollaboratorDialogFragment.CollaboratorViewHolder holder, int position) {
            holder.set(collaborators.get(position));
        }

        @Override
        public int getItemCount() {
            return collaborators.size();
        }
    }

    /**
     * Cursomt view holder to display a clickable user
     */
    private class CollaboratorViewHolder extends RecyclerView.ViewHolder {

        private final TextView collaboratorNameTextView;
        private final ImageView avatarImageView;
        private User user;

        public CollaboratorViewHolder(@NonNull View itemView) {
            super(itemView);
            collaboratorNameTextView = itemView.findViewById(R.id.collaboratorName_TextView);
            avatarImageView = itemView.findViewById(R.id.avatar_ImageView);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // remove the clicked item from the list
                    int position = collaborators.indexOf(user);
                    User newCollaborator = collaborators.remove(position);
                    adapter.notifyItemRemoved(position);
                    adapter.allUsers.add(newCollaborator);

                    //add the collaborator that was clicked to the database
                    NoteDatabaseHandler DBhandler = new NoteDatabaseHandler(getContext());
                    Collaborator c = new Collaborator();
                    c.setUserId(newCollaborator.getId());
                    c.setNoteId(note);

                    //add the user to a list that will be passed to the display users fragment
                    //i dont think im using anymore but im too scared ill break something and this assignment is due in a matter of minutes
                    added.add(newCollaborator);


                    try {
                        DBhandler.getcollaboratorTable().create(c);
                    } catch (DatabaseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        /**
         * Set the user of the view holder.
         * @param user
         */
        public void set(User user) {
            this.user = user;
            collaboratorNameTextView.setText(user.getName());
            avatarImageView.setImageBitmap(user.getAvatar());
        }

    }
    public List<User> getUsers()
    {
        return added;
    }
}
