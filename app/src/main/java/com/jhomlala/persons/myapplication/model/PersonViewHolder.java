package com.jhomlala.persons.myapplication.model;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jhomlala.persons.myapplication.R;
import com.jhomlala.persons.myapplication.view.RecyclerViewClickListener;

public class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView mPersonName;
    private RecyclerViewClickListener mRecyclerViewClickListener;

    public PersonViewHolder(View itemView, RecyclerViewClickListener recyclerViewClickListener) {
        super(itemView);
        mPersonName = itemView.findViewById(R.id.person_name);
        mRecyclerViewClickListener = recyclerViewClickListener;
        itemView.setOnClickListener(this);
    }

    public TextView getPersonName() {
        return mPersonName;
    }

    @Override
    public void onClick(View view) {
        Log.i("PersonViewHolder","Clicked on element!");
        mRecyclerViewClickListener.onClick(view, getAdapterPosition());
    }


}
