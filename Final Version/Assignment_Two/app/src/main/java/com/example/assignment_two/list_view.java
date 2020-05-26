package com.example.assignment_two;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class list_view extends AppCompatActivity {

    private RecyclerView itemRecycleView;
    private itemAdapter mItemAdapter;
    private SearchView mSearchView;
    private TextView Name;
    private ImageView ProfilePhoto;
    public List<DataSave> item_list = addlist.dataSaveList;
    public DataSave itemSaved = addlist.dataSave;
    public DataSave nProfile = MainActivity.NProfile;

    private List<DataSave> itemNew_List = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_list_view );
        loadData();

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
        mSearchView = findViewById( R.id.searchView );
        itemRecycleView = findViewById( R.id.RecyclerView );
        mItemAdapter = new itemAdapter( item_list );
        Name = findViewById( R.id.Profile_name );
        Name.setText( "Hello " + nProfile.name.toUpperCase().trim() );
        ProfilePhoto = findViewById( R.id.profile_image );
        ProfilePhoto.setImageBitmap( nProfile.bitmap );

        ItemTouchHelper.Callback callback = new MyItemTouchHelper( mItemAdapter );
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper( callback );
        itemTouchHelper.attachToRecyclerView( itemRecycleView );

        itemRecycleView.setAdapter( mItemAdapter );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( this );
        itemRecycleView.setLayoutManager( layoutManager );


        for (int i = 0; i < Location_List.length; i++) {
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
        itemRecycleView.addItemDecoration( new DividerItemDecoration( this, DividerItemDecoration.VERTICAL ) );


        Add_btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), addlist.class );
                startActivity( intent );
            }
        } );
        //Set up the search bar can change content when user input letter
        mSearchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mItemAdapter.getFilter().filter( newText );
                return false;
            }
        } );
        itemRecycleView.setBackgroundColor( Color.TRANSPARENT );
    }
    private void loadData(){
        //Use SharedPreferences function
        SharedPreferences sharedPreferences = getSharedPreferences( "shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        //Get saved recycleView item from the SharedPreferences
        String json = sharedPreferences.getString( "task list", null);
        Type type = new TypeToken<ArrayList<DataSave>>(){}.getType();
        item_list = gson.fromJson( json,type );

        if(item_list ==null){
            item_list = new ArrayList<>(  );
        }
    }
}

