package com.example.assignment_two;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class addlist extends AppCompatActivity {

    private Spinner spinner1;
    private Spinner spinner2;
    private Spinner spinner3;
    private EditText editText;
    private TextView startDate;
    private TextView endDate;
    private Button AddContent;
    private TextView A_change;
    private TextView C_change;
    private DatePickerDialog.OnDateSetListener start_dateSetListener;
    private DatePickerDialog.OnDateSetListener end_dateSetListener;

    public static List<DataSave> dataSaveList = new ArrayList<>(  );
    public static DataSave dataSave = new DataSave();
    DataSave compareDate = new DataSave();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_addlist );

        spinner1 = findViewById( R.id.location_spinner );
        spinner2 = findViewById( R.id.adult_spinner );
        spinner3 = findViewById( R.id.child_spinner );
        editText = findViewById( R.id.activity );
        C_change = findViewById(R.id.C_text);
        A_change = findViewById(R.id.A_text);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource( this, R.array.location,android.R.layout.simple_spinner_item );
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource( this, R.array.number,android.R.layout.simple_spinner_item );
        adapter1.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        adapter2.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinner1.setAdapter( adapter1 );
        spinner2.setAdapter( adapter2 );
        spinner3.setAdapter( adapter2 );
        spinner1.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        spinner2.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Check if user select the result is 0 or 1, the text will change
                if(position != 0 && position !=1){
                    A_change.setText("Adults: ");
                }
                else{
                    A_change.setText("Adult: ");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
        spinner3.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Check if user select the result is 0 or 1, the text will change
                if(position != 0 && position !=1){
                    C_change.setText("Children: ");
                }
                else{
                    C_change.setText("Child: ");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        startDate = findViewById( R.id.start_date );
        endDate = findViewById( R.id.end_date );

        startDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get( Calendar.YEAR );
                int month = calendar.get( Calendar.MONTH );
                int day = calendar.get( Calendar.DAY_OF_MONTH );
                //Set up the datapickerdialog Mode
                DatePickerDialog datePickerDialog = new DatePickerDialog( addlist.this, android.R.style.Theme_Holo_Light_Dialog, start_dateSetListener, year, month, day );
                datePickerDialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
                datePickerDialog.show();
            }
        } );

        start_dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //The result is -1, therefore we need to +1
                month = month + 1;
                compareDate.start_year = year;
                compareDate.start_month = month;
                compareDate.start_day = dayOfMonth;
                startDate.setText( year + "/" + month + "/" + dayOfMonth );
            }
        };

        endDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get( Calendar.YEAR );
                int month = calendar.get( Calendar.MONTH );
                int day = calendar.get( Calendar.DAY_OF_MONTH );
                //Set up the datapickerdialog Mode
                DatePickerDialog datePickerDialog = new DatePickerDialog( addlist.this, android.R.style.Theme_Holo_Light_Dialog, end_dateSetListener, year, month, day );
                datePickerDialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
                datePickerDialog.show();
            }
        } );
        end_dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                if (startDate.toString() != "Select Date") {
                    int startYear = compareDate.getStart_year();
                    int startMonth = compareDate.getStart_month();
                    int startDay = compareDate.getStart_day();

                    AlertDialog.Builder dialog = new AlertDialog.Builder( addlist.this );
                    dialog.setTitle( "Error" );
                    dialog.setMessage( "Your End Date must be after the Start Date!" );
                    dialog.setPositiveButton( "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    } );
                    final AlertDialog alertDialog = dialog.create();

                    //Check if the the end data is before the start data, it need to reselect the end data.
                    if (year < startYear) {
                        alertDialog.show();
                    } else if (year == startYear && month < startMonth) {
                        alertDialog.show();
                    } else if (year == startYear && month == startMonth && dayOfMonth < startDay) {
                        alertDialog.show();
                    } else {
                        endDate.setText( year + "/" + month + "/" + dayOfMonth );
                    }
                } else {
                    endDate.setText( year + "/" + month + "/" + dayOfMonth );
                }
            }
        };


        AddContent = findViewById( R.id.addContent );
        AddContent.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataSave newData = new DataSave();
                String Activity = editText.getText().toString().trim();
                String Location = spinner1.getSelectedItem().toString().trim();
                String Adult_Number = spinner2.getSelectedItem().toString().trim() ;
                String Child_Number = spinner3.getSelectedItem().toString().trim() ;
                String Start_Date = startDate.getText().toString().trim();
                String End_Date = endDate.getText().toString().trim();


                AlertDialog.Builder dialog = new AlertDialog.Builder( addlist.this );
                dialog.setTitle( "Error" );
                dialog.setMessage( "You need to fill in all the activity above!" );
                dialog.setPositiveButton( "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                } );
                final AlertDialog alertDialog = dialog.create();

                //show dialog if the activity edittext is empty
                if(Activity.isEmpty()){
                    alertDialog.show();
                }
                else{
                    newData.activity = Activity;
                    newData.location = Location;
                    newData.adult = Adult_Number;
                    newData.child = Child_Number;
                    newData.start_date = Start_Date;
                    newData.end_date = End_Date;
                    dataSave = newData;
                    dataSaveList.add(newData);
                    saveData();

                    Intent intent = new Intent( getApplicationContext(), list_view.class );
                    startActivity( intent );
                }
            }
        } );
    }

    private void saveData(){
        //Run SharedPreferences function
        SharedPreferences sharedPreferences = getSharedPreferences( "shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //Use Gson pagage
        Gson gson = new Gson();
        //save the dataSaveList as string
        String json = gson.toJson( dataSaveList );
        editor.putString( "task list",json );
        editor.apply();
    }


}
