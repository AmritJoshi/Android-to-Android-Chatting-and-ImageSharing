package kampfire.verify.messenger.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import kampfire.verify.messenger.Adapters.ImageAdapter;
import kampfire.verify.messenger.Models.Upload;
import kampfire.verify.messenger.R;
import kampfire.verify.messenger.UploadImage;
import kampfire.verify.messenger.databinding.FragmentStatusBinding;

public class Status extends Fragment {

    FragmentStatusBinding binding;
    private ImageAdapter imageAdapter;
    private DatabaseReference mDatabaseReference;
    private List<Upload> mUploads;
    public Status() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentStatusBinding.inflate(inflater,container,false);
        action();
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mUploads=new ArrayList<>();
        mDatabaseReference= FirebaseDatabase.getInstance().getReference("uploads");

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1: snapshot.getChildren() ){
                    Upload upload=snapshot1.getValue(Upload.class);
                    mUploads.add(upload);
                }
                imageAdapter=new ImageAdapter(getContext(),mUploads);

                binding.recyclerView.setAdapter(imageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }

    public void action(){
        binding.upload.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(), UploadImage.class);
                startActivity(i);

            }
        });
    }
}