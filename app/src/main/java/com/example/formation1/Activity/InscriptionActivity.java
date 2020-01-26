package com.example.formation1.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.formation1.R;
import com.example.formation1.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InscriptionActivity extends AppCompatActivity implements View.OnClickListener {

private TextView login ;
private TextView save;
private EditText username;
private EditText Email;
private EditText password;
private EditText phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        login=findViewById(R.id.login);
        save=findViewById(R.id.save);
        username=findViewById(R.id.username);
        Email=findViewById(R.id.Email);
        password=findViewById(R.id.password);
        phone=findViewById(R.id.phone);

        login.setOnClickListener(this);
        save.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case(R.id.login):
                startActivity(new Intent(InscriptionActivity.this, LoginActivity.class));
            break;
            case (R.id.save):
               // startActivity(new Intent(InscriptionActivity.this, HomeActivity.class));

                registerUser();
            break;
        }

    }
    private void registerUser(){
        String email= Email.getEditableText().toString();
        String passwordvalue =password.getEditableText().toString();


        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,passwordvalue)
                .addOnCompleteListener(InscriptionActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                     if (task.isSuccessful()){
                         Toast.makeText(InscriptionActivity.this,"User saved successfully",Toast.LENGTH_LONG).show();
                         saveUserToRealTimeDatabase();
                     }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(InscriptionActivity.this,"Error, there was en error while creating your profile",Toast.LENGTH_LONG).show();
            }
        });


    }

    private void saveUserToRealTimeDatabase (){
        String uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/Users/"+uid);

        String email = Email.getEditableText().toString();
        String name = username.getEditableText().toString();
        String phonevalue =phone.getEditableText().toString();

        UserModel user = new UserModel(uid,email,phonevalue,name);

        ref.setValue(user).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(InscriptionActivity.this,"welcome",Toast.LENGTH_LONG).show();
                startActivity(new Intent(InscriptionActivity.this,HomeActivity.class));
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(InscriptionActivity.this,"error saving user in db",Toast.LENGTH_LONG).show();
                Log.e("INSCRIPTION",e.getMessage());
            }
        });
    }
}
