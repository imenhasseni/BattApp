package com.example.formation1.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.formation1.Activity.LoginActivity;
import com.example.formation1.R;
import com.example.formation1.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment {

    private Button btnlogout;
    private TextView tvEmail;
    private TextView tvPhone;
    private TextView tvUsername;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnlogout = view.findViewById(R.id.tv_logout);
        tvEmail = view.findViewById(R.id.tv_adresse);
        tvPhone = view.findViewById(R.id.tv_number);
        tvUsername = view.findViewById(R.id.tv_name);

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }

        });

        fetchCurrentUser();
    }

    private void fetchCurrentUser (){
        String uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/Users/"+uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel currentUser = dataSnapshot.getValue(UserModel.class);
                tvEmail.setText(currentUser.getEmail());
                tvPhone.setText(currentUser.getPhone());
                tvUsername.setText(currentUser.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),"Error",Toast.LENGTH_LONG).show();

            }
        });
    }
}