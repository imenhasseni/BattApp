package com.example.formation1.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.formation1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
   private EditText etEmail;
   private EditText etmotdepasse;
   private Button etlogin;
   private CheckBox etcheckbox;
   private TextView signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etEmail= findViewById(R.id.et_email);
        etmotdepasse= findViewById(R.id.et_motdepasse);
        etlogin=findViewById(R.id.Button_login);
        etcheckbox=findViewById(R.id.checkBox_checkBox1);
        signup=findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 startActivity(new Intent( LoginActivity.this, InscriptionActivity.class));
            }
        });


        etlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (validateInput()){
                        String emailvalue = etEmail.getEditableText().toString();
                        String passwordvalue=etmotdepasse.getEditableText().toString();



                    FirebaseAuth.getInstance().signInWithEmailAndPassword(emailvalue,passwordvalue)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){

                                        Toast.makeText(LoginActivity.this,"welcome",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                    }
                                }
                            }).addOnFailureListener(LoginActivity.this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this,"Error, login",Toast.LENGTH_LONG).show();
                        }
                    });


                }

            }

        });
    }
    private Boolean validateInput (){


    String emailValue = etEmail.getEditableText().toString();
    String passewordvalue =etmotdepasse.getEditableText().toString();
    Boolean checkboxvalue= etcheckbox.isChecked();
    Boolean LoginSucceful=true;
                if(emailValue.isEmpty()){
        LoginSucceful=false;
        Toast.makeText(LoginActivity.this,"Please enter your email", Toast.LENGTH_LONG).show();
    }
                else if(passewordvalue.isEmpty()){
        LoginSucceful=false;
        Toast.makeText(LoginActivity.this,"Please enter your passeword",Toast.LENGTH_LONG).show();
    }
                else if(passewordvalue.length()< 6 ){
        LoginSucceful=false;
        Toast.makeText(LoginActivity.this,"Passeword shoud be at least &character",Toast.LENGTH_LONG).show();
    }
                else if (!checkboxvalue){
        LoginSucceful=false;
        Toast.makeText(LoginActivity.this, "Please accept our terms and conditions",Toast.LENGTH_LONG).show();
    }
                return LoginSucceful;
    }

}

