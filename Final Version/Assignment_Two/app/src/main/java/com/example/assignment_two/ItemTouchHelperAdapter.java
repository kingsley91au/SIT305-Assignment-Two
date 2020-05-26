package com.example.assignment_two;

public interface ItemTouchHelperAdapter {
    //Set up the RecylerView Item move and swipe condition
    void onItemMove(int fromPosition, int toPosition);
    void onItemSwiped(int position);
}
