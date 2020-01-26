package com.example.formation1.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.formation1.R;
import com.example.formation1.model.ZoneModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddlocalisationFragment extends Fragment {
  private TextView btnSave;
  private EditText username;
  private EditText adresse;
  private EditText date;
  private Spinner etat;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_addlocalisation, container, false);


        }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnSave=view.findViewById(R.id.savezone);
        username=view.findViewById(R.id.username);
        adresse=view.findViewById(R.id.Adresse);
        date=view.findViewById(R.id.date);
        etat=view.findViewById(R.id.spinneretat);
        initCalendar();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendar();
            }
        });

        etat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selecteditem = parent.getItemAtPosition(position).toString();
                Toast.makeText(getActivity(),selecteditem,Toast.LENGTH_LONG).show();
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parentView)
            {

            }
        });
        btnSave.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                String etatValue = etat.getSelectedItem().toString();
                String usernameValue = username.getEditableText().toString();
                String adresseValue =adresse.getEditableText().toString();
                String dateValue= date.getEditableText().toString();

                String id= FirebaseAuth.getInstance().getUid();


                ZoneModel zone  = new ZoneModel(id,adresseValue,dateValue,etatValue,usernameValue);



                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("/ZONES/"+id);

                reference.setValue(zone).addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getActivity(), "DATA SAVED SUCCESSFLY",Toast.LENGTH_LONG).show();

                    }
                }).addOnFailureListener(getActivity(), new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(getActivity(),"ERROR,DATA DIDN'T GET SAVED",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


    }
    private SimpleDateFormat getDateFormat(){
        return new SimpleDateFormat  ("yyy.mm.dd",Locale.getDefault());
    }
    private void showCalendar(){
        final Calendar mDateCalendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener mDatePickerDialogListenner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mDateCalendar.set(Calendar.YEAR,year);
                mDateCalendar.set(Calendar.MONTH,month);
                mDateCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                date.setText(getDateFormat().format(mDateCalendar.getTime()));
            }
        };

        DatePickerDialog mDatePickerDialog = new DatePickerDialog(getActivity(),R.style.DatePickerTheme,mDatePickerDialogListenner,mDateCalendar.get(Calendar.YEAR),mDateCalendar.get(Calendar.MONTH),mDateCalendar.get(Calendar.DAY_OF_MONTH));
        mDatePickerDialog.getDatePicker().setMaxDate(mDateCalendar.getTimeInMillis());
        mDatePickerDialog.setCancelable(false);
        mDatePickerDialog.show();

    }
    private void initCalendar (){
        String currentDate =getDateFormat().format(new Date());
       date.setText(currentDate);
    }
}





