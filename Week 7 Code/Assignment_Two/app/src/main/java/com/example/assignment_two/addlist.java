package com.example.assignment_two;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
                String location = parent.getItemAtPosition( position ).toString();
                Toast.makeText( parent.getContext(),location+" 1 ", Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
        spinner2.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String adult_Number = parent.getItemAtPosition( position ).toString();
                Toast.makeText( parent.getContext(),adult_Number+" 2 ", Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
        spinner3.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String child_Number = parent.getItemAtPosition( position ).toString();
                Toast.makeText( parent.getContext(),child_Number+" 3 ", Toast.LENGTH_SHORT ).show();
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

                DatePickerDialog datePickerDialog = new DatePickerDialog( addlist.this, android.R.style.Theme_Holo_Light_Dialog, start_dateSetListener, year, month, day );
                datePickerDialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
                datePickerDialog.show();
            }
        } );
        start_dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Toast.makeText( getApplicationContext(), " Year: " + year + " Month: " + month + " Day: " + dayOfMonth, Toast.LENGTH_LONG ).show();
                startDate.setText( year + "/" + month + "/" + dayOfMonth );
                compareDate.start_year = year;
                compareDate.start_month = month;
                compareDate.start_day = dayOfMonth;
            }
        };
        endDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get( Calendar.YEAR );
                int month = calendar.get( Calendar.MONTH );
                int day = calendar.get( Calendar.DAY_OF_MONTH );

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

                    Intent intent = new Intent( getApplicationContext(), list_view.class );
                    startActivity( intent );
                }
            }
        } );

    }
}
