package com.example.assignment_two;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class itemAdapter extends RecyclerView.Adapter<itemAdapter.itemViewHolder> {

    private List<DataSave> ItemList;
    public static class itemViewHolder extends RecyclerView.ViewHolder{
        public TextView location;
        public TextView startDate;
        public TextView endDate;
        public TextView adultNumber;
        public TextView childNumber;
        public TextView activity;

        public itemViewHolder(View itemView) {
            super( itemView );
            location = itemView.findViewById( R.id.location_list );
            startDate = itemView.findViewById( R.id.start_date_list );
            endDate = itemView.findViewById( R.id.end_date_list );
            adultNumber = itemView.findViewById( R.id.adult_number_list );
            childNumber = itemView.findViewById( R.id.child_number_list );
            activity = itemView.findViewById( R.id.activity_list );
        }
    }
    public itemAdapter(List<DataSave> itemList){
        ItemList = itemList;
    }
    @Override
    public itemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.recycler_layout, parent, false );
        itemViewHolder evh =new itemViewHolder( v );
        return evh;
    }

    @Override
    public void onBindViewHolder(itemViewHolder holder, int position) {
        DataSave currentItem  = ItemList.get( position );
        holder.location.setText( currentItem.getLocation() );
        holder.startDate.setText( currentItem.getStart_date() );
        holder.endDate.setText( currentItem.getEnd_Date() );
        holder.adultNumber.setText( currentItem.getAdult() );
        holder.childNumber.setText( currentItem.getChild() );
        holder.activity.setText( currentItem.getActivity() );
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }
}
