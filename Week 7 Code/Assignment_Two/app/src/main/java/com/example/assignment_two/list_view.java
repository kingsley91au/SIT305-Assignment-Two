package com.example.assignment_two;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class list_view extends AppCompatActivity {

    private RecyclerView itemRecycleView;
    private itemAdapter mItemAdapter;
    public List<DataSave> item_list = addlist.dataSaveList;
    public DataSave itemSaved = addlist.dataSave;
    private List<DataSave> itemNew_List = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_list_view );

        String location = itemSaved.location;
        String startDate = itemSaved.start_date;
        String endDate = itemSaved.end_date;
        String adultNumber = itemSaved.adult;
        String childNumber = itemSaved.child;
        String activity = itemSaved.activity;

        String[] Location_List = {location};
        String[] StartDate_List = {startDate};
        String[] EndDate_List = {endDate};
        String[] AdultNumber_List = {adultNumber};
        String[] ChildNumber_List = {childNumber};
        String[] Activity_List = {activity};

        ImageButton Add_btn = findViewById( R.id.add_btn );
        itemRecycleView = findViewById( R.id.RecyclerView );
        mItemAdapter = new itemAdapter(item_list);
        itemRecycleView.setAdapter( mItemAdapter );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( this );
        itemRecycleView.setLayoutManager( layoutManager );

        for(int i = 0; i<Location_List.length;i++){
            String Location = Location_List[i];
            String StartDate = StartDate_List[i];
            String EndDate = EndDate_List[i];
            String AdultNumber = AdultNumber_List[i];
            String ChildNumber = ChildNumber_List[i];
            String Activity = Activity_List[i];

            DataSave NewItem = new DataSave();
            NewItem.location = Location;
            NewItem.start_date = StartDate;
            NewItem.end_date = EndDate;
            NewItem.adult = AdultNumber;
            NewItem.child = ChildNumber;
            NewItem.activity = Activity;

            itemNew_List.add( NewItem );
        }
        itemRecycleView.addItemDecoration( new DividerItemDecoration( this, DividerItemDecoration.VERTICAL ));


        Add_btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(),addlist.class );
                startActivity( intent );
            }
        } );

    }
}
