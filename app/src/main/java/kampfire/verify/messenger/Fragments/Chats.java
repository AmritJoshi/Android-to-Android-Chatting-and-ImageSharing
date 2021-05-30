package kampfire.verify.messenger.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import kampfire.verify.messenger.Adapters.UsersAdapter;
import kampfire.verify.messenger.Models.Users;
import kampfire.verify.messenger.R;
import kampfire.verify.messenger.databinding.ActivityMainBinding;
import kampfire.verify.messenger.databinding.FragmentChatsBinding;

import static android.content.ContentValues.TAG;


public class Chats extends Fragment {

    public Chats() {
        // Required empty public constructor
    }
    ArrayList<Users> list=new ArrayList<Users>();
    FirebaseDatabase database;
    FragmentChatsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentChatsBinding.inflate(inflater,container,false);
        database=FirebaseDatabase.getInstance();
        UsersAdapter adapter=new UsersAdapter(list,getContext());
        binding.chatRecyclerView.setAdapter(adapter);


        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        binding.chatRecyclerView.setLayoutManager(layoutManager);

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Users users=dataSnapshot.getValue(Users.class);
                    if (users != null) {
                        users.setUserId(dataSnapshot.getKey());
                    }
                    list.add(users);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return binding.getRoot();
    }
}