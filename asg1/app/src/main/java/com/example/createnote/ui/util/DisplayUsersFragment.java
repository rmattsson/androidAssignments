package com.example.createnote.ui.util;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.createnote.R;
import com.example.createnote.model.Collaborator;
import com.example.createnote.model.NoteDatabaseHandler;
import com.example.createnote.model.User;
import com.example.createnote.sqlite.DatabaseException;

import java.util.ArrayList;
import java.util.List;




/**
 * Display a horizontal list of users by their avatars.
 */
public class DisplayUsersFragment extends Fragment {



    /**
     * Listener for the request to add a user event.
     */
    public interface OnAddUserRequestedListener {
        /**
         * Called to indicate the request to add a user.
         */
        void onAddUserRequested();
    }

    // display the users
    private ImageView addUserImageView;
    private List<User> users;
    private List<User> collabs;
    private RecyclerView usersRecyclerView;

    // listeners
    private OnAddUserRequestedListener onAddUserRequestedListener;

    public DisplayUsersFragment() {
        users = new ArrayList<>();
    }

    /**
     * Set the add user listener
     * @param onAddUserRequestedListener
     */
    public void setOnAddUserRequestedListener(OnAddUserRequestedListener onAddUserRequestedListener) {
        this.onAddUserRequestedListener = onAddUserRequestedListener;
    }

    /**
     * Set the users to display.
     * @param users
     */
    public void setUsers(List<User> users) {
        this.users = users;
        usersRecyclerView.getAdapter().notifyDataSetChanged();
    }

    /**
     * Add a user to the fragment
     * @param user
     */
    public void add(User user) {
        users.add(user);
        usersRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_display_users, container, false);

        NoteDatabaseHandler DBhandler = new NoteDatabaseHandler(getContext());
        users = null;
        collabs = null;
        try {

            users = DBhandler.getUserTable().readAll();

        } catch (DatabaseException e) {
            e.printStackTrace();
        }


        // initialize user
        addUserImageView = root.findViewById(R.id.addUser_ImageView);
        addUserImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onAddUserRequestedListener != null)
                    onAddUserRequestedListener.onAddUserRequested();
                AddCollaboratorDialogFragment addCollaborator = new AddCollaboratorDialogFragment(users);
                addCollaborator.show(getFragmentManager(), "add collaborator");
//                try {
//
//                    List<Collaborator> colls = DBhandler.getcollaboratorTable().readAll();
//
//                } catch (DatabaseException e) {
//                    e.printStackTrace();
//                }
                collabs = addCollaborator.getUsers();
                for (User col: collabs)
                {
                    Collaborator c = new Collaborator();
                    c.setUserId(col.getId());

                    //TODO

                    //c.setNoteId();
                    try {
                        DBhandler.getcollaboratorTable().update(c);
                    } catch (DatabaseException e) {
                        e.printStackTrace();
                    }
                }



            }
        });

        // setup the recyclerview
        usersRecyclerView = root.findViewById(R.id.users_RecyclerView);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        usersRecyclerView.setHasFixedSize(true);

        // set the adapter for user recycler view.
        usersRecyclerView.setAdapter(new RecyclerView.Adapter<UserViewHolder>() {

            @NonNull
            @Override
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new UserViewHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.list_item_display_user, parent, false)
                );
            }

            @Override
            public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
                holder.set(users.get(position));
            }

            @Override
            public int getItemCount() {
                return users.size();
            }
        });

        return root;
    }

    // View holder for the users
    private class UserViewHolder extends RecyclerView.ViewHolder {

        private final ImageView avatarImageView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.avatar_ImageView);
        }

        public void set(User user) {
            avatarImageView.setImageBitmap(user.getAvatar());
        }
    }


}
