package com.developeralamin.eshop.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.developeralamin.eshop.R;
import com.developeralamin.eshop.databinding.ActivityRegistionBinding;
import com.developeralamin.eshop.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

public class RegistionActivity extends AppCompatActivity {

    private ActivityRegistionBinding binding;
    private String name, email, password, address;

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();


        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        binding.regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = binding.inputName.getText().toString();
                email = binding.inputEmail.getText().toString();
                password = binding.inputPassword.getText().toString();
                address = binding.inputAddress.getText().toString();
                String rePassword = binding.inputReTypePassword.getText().toString();

                if (name.isEmpty()) {
                    binding.inputName.setError("Enter Name");
                    binding.inputName.requestFocus();
                } else if (email.isEmpty()) {
                    binding.inputEmail.setError("Enter Email");
                    binding.inputEmail.requestFocus();
                } else if (password.isEmpty()) {
                    binding.inputPassword.setError("Enter Password");
                    binding.inputPassword.requestFocus();
                } else if (password.length() < 6) {
                    Toast.makeText(RegistionActivity.this, "Password min 6 cha.", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(rePassword)) {
                    Toast.makeText(RegistionActivity.this, "Password not match", Toast.LENGTH_SHORT).show();
                } else if (address.isEmpty()) {
                    binding.inputAddress.setError("Enter Adreess");
                    binding.inputAddress.requestFocus();
                } else {

                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                insertData();
                            }
                        }
                    });
                }
            }
        });
    }

    private void insertData() {

        UserModel userModel = new UserModel(
                name = binding.inputName.getText().toString(),
                email = binding.inputEmail.getText().toString(),
                password = binding.inputPassword.getText().toString(),
                address = binding.inputAddress.getText().toString());

        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("users").document(currentUser).set(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(RegistionActivity.this, MainActivity.class));
                    Toast.makeText(RegistionActivity.this, "Registion successfuly", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}