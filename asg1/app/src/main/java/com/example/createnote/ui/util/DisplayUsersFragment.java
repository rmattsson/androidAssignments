package com.example.createnote.ui.util;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.createnote.R;
import com.example.createnote.model.Collaborator;
import com.example.createnote.model.Note;
import com.example.createnote.model.NoteDatabaseHandler;
import com.example.createnote.model.User;
import com.example.createnote.networking.HttpRequest;
import com.example.createnote.networking.HttpRequestTask;
import com.example.createnote.networking.HttpResponse;
import com.example.createnote.networking.OnErrorListener;
import com.example.createnote.networking.OnResponseListener;
import com.example.createnote.sqlite.DatabaseException;
import com.example.createnote.ui.NotesApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Display a horizontal list of users by their avatars.
 */
public class DisplayUsersFragment extends Fragment {


    private long nid;
    private String UUID;

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
    private NotesApplication application;

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
        users  = new ArrayList<>();
        collabs = new ArrayList<>();


        application = (NotesApplication) getActivity().getApplication();
        HttpRequest request = new HttpRequest(application.getURL() + "/user/", HttpRequest.Method.GET);
        HttpRequestTask task = new HttpRequestTask();
        task.setOnResponseListener(new OnResponseListener<HttpResponse>() {
            @Override
            public void onResponse(HttpResponse d) {
                User[] array = User.parseArray(d.getResponseBody());
                for (User u:array) {
                    users.add(u);
                }
            }
        });
        task.setOnErrorListener(new OnErrorListener() {
            @Override
            public void onError(Exception error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        task.execute(request);


        // initialize user
        addUserImageView = root.findViewById(R.id.addUser_ImageView);
        addUserImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onAddUserRequestedListener != null)
                    onAddUserRequestedListener.onAddUserRequested();
                AddCollaboratorDialogFragment addCollaborator = new AddCollaboratorDialogFragment(users, nid);
                addCollaborator.show(getFragmentManager(), "add collaborator");

                setNote(nid);

               usersRecyclerView.getAdapter().notifyDataSetChanged();

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
                holder.set(collabs.get(position));
            }

            @Override
            public int getItemCount() {
                return collabs.size();
            }
        });



        return root;
    }

    public void setUUID(String uuid)
    {
        this.UUID = uuid;
    }

    //get the note
    public void setNote(long noteID)
    {
        this.nid=noteID;


        HttpRequest request = new HttpRequest(application.getURL() + "/note/" + UUID + "/collaborators/", HttpRequest.Method.GET);
        HttpRequestTask task = new HttpRequestTask();
        task.setOnResponseListener(new OnResponseListener<HttpResponse>() {
            @Override
            public void onResponse(HttpResponse d) {
                User[] array = User.parseArray(d.getResponseBody());
                for (User u:array) {
                    collabs.add(u);
                }
            }
        });
        task.setOnErrorListener(new OnErrorListener() {
            @Override
            public void onError(Exception error) {
                //Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        task.execute(request);

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
