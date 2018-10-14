package com.jhomlala.persons.myapplication.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhomlala.persons.myapplication.view.RecyclerViewClickListener;
import com.jhomlala.persons.myapplication.model.PersonViewHolder;
import com.jhomlala.persons.myapplication.R;
import com.jhomlala.persons.myapplication.model.Person;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonViewHolder> {

    private List<Person> mPersonList;
    private RecyclerViewClickListener mClickListener;

    public PersonAdapter(List<Person> initalPersonList,RecyclerViewClickListener clickListener) {
        this.mPersonList = initalPersonList;
        this.mClickListener = clickListener;
    }


    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.person_list_element, parent, false);
        return new PersonViewHolder(itemView,mClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        Person person = mPersonList.get(position);
        holder.getPersonName().setText(person.getName() + " " + person.getSurname());
    }

    @Override
    public int getItemCount() {
        return mPersonList.size();
    }

    public void updatePersonList(List<Person> personList) {
        this.mPersonList = personList;
    }


}
