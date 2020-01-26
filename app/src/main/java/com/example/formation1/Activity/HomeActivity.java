package com.example.formation1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.formation1.Fragment.AddlocalisationFragment;
import com.example.formation1.Fragment.HomeFragment;
import com.example.formation1.Fragment.LocalisationFragment;
import com.example.formation1.R;
import com.example.formation1.Fragment.ProfileFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_menu_container,new HomeFragment(),"")
                .commit();

        ChipNavigationBar menu=findViewById(R.id.bottom_menu);
        menu.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                switch (id){
                    case (R.id.home):
                        getSupportFragmentManager().beginTransaction().add(R.id.fragment_menu_container,new HomeFragment(),"")
                                .commit();
                    break;
                    case (R.id.partner):
                        getSupportFragmentManager().beginTransaction().add(R.id.fragment_menu_container,new ProfileFragment(),"")
                                .commit();
                    break;
                    case(R.id.localisation):
                        getSupportFragmentManager().beginTransaction().add(R.id.fragment_menu_container,new LocalisationFragment(),"")
                                .commit();
                    break;
                    case(R.id.addlocation):
                        getSupportFragmentManager().beginTransaction().add(R.id.fragment_menu_container,new AddlocalisationFragment(),"")
                                .commit();
                    break;
                }

            }
        });

    }
}
