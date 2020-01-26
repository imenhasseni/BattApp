package com.example.formation1.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.formation1.R;
import com.google.firebase.auth.FirebaseAuth;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ConstraintLayout second = findViewById(R.id.second);
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FirebaseAuth.getInstance().getUid() == null){
                    startActivity(new Intent(SecondActivity.this,LoginActivity.class));
                    finish();
                }
                else {
                   startActivity(new Intent(SecondActivity.this,HomeActivity.class));
                   finish();

                }

finish();
            }
        });
    }
}
