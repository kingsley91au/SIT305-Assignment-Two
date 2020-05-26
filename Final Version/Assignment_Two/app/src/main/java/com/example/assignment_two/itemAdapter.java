package com.example.assignment_two;

import android.net.sip.SipSession;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class itemAdapter extends RecyclerView.Adapter<itemAdapter.itemViewHolder> implements
        Filterable,
        ItemTouchHelperAdapter   {

    private List<DataSave> ItemList;
    private List<DataSave> ItemListFull;

    //Set up the recyclerView item move
    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(ItemList,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);
    }
    //Set up the recyclerView item swipe
    @Override
    public void onItemSwiped(int position) {
        ItemList.remove(position);
        notifyItemRemoved(position);
    }

    public static class itemViewHolder extends RecyclerView.ViewHolder implements
            //Set the item can be touched
            View.OnTouchListener,
            GestureDetector.OnGestureListener {
        public TextView location;
        public TextView startDate;
        public TextView endDate;
        public TextView adultNumber;
        public TextView childNumber;
        public TextView activity;

        GestureDetector gestureDetector;
        OnItemClickListener onItemClickListener;

        public itemViewHolder(View itemView) {
            super( itemView );
            location = itemView.findViewById( R.id.location_list );
            startDate = itemView.findViewById( R.id.start_date_list );
            endDate = itemView.findViewById( R.id.end_date_list );
            adultNumber = itemView.findViewById( R.id.adult_number_list );
            childNumber = itemView.findViewById( R.id.child_number_list );
            activity = itemView.findViewById( R.id.activity_list );

            gestureDetector = new GestureDetector(itemView.getContext(),this);
            itemView.setOnTouchListener(this);

        }
        //Create the ListView item a interface
        public void setOnItemClickListener(OnItemClickListener listener){
            onItemClickListener = listener;
        }
        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }
        //Able to tap up with a click
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            gestureDetector.onTouchEvent(event);
            return true;
        }
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
    }


    public itemAdapter(List<DataSave> itemList){
        this.ItemList = itemList;
        ItemListFull = new ArrayList<>(itemList);
    }
    @Override
    public itemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.recycler_layout, parent, false );
        itemViewHolder itemViewHolder =new itemViewHolder( view );
        return itemViewHolder;
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
    @Override
    public Filter getFilter() {
        return ItemFilter;
    }
    private Filter ItemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<DataSave> filteredList = new ArrayList<>();

            if(constraint == null||constraint.length()==0){
                filteredList.addAll(ItemListFull);
            }
            else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                //For the search keyword set up
                for(DataSave item: ItemListFull){
                    //Set up only search for location
                    if(item.getLocation().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values =filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //Clear the old ItemList
            ItemList.clear();
            //Add all the search result from the input search bar
            ItemList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
