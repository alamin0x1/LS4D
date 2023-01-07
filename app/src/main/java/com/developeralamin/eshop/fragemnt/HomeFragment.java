package com.developeralamin.eshop.fragemnt;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developeralamin.eshop.R;
import com.developeralamin.eshop.adatper.UserAdapter;
import com.developeralamin.eshop.databinding.FragmentHomeBinding;
import com.developeralamin.eshop.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private FirebaseAuth auth;

    private List<UserModel> list;
    private UserAdapter adapter;

    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());

        auth = FirebaseAuth.getInstance();

        list = new ArrayList<>();

        db = FirebaseFirestore.getInstance();

        adapter = new UserAdapter(list, getContext());

        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        UserModel userModel = documentSnapshot.toObject(UserModel.class);
                        list.add(userModel);
                        binding.userRecylerView.setAdapter(adapter);
                        binding.progressBar.setVisibility(View.GONE);

                        Log.d("SOFTLAB", "User data " + userModel.getName());
                        Log.d("SOFTLAB", "User data " + userModel.getEmail());
                        Log.d("SOFTLAB", "User data " + userModel.getAddress());
                    }
                }
            }
        });


        return binding.getRoot();
    }
}