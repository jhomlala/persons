package com.jhomlala.persons.myapplication.view;


import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jhomlala.persons.myapplication.R;
import com.jhomlala.persons.myapplication.view.adapter.PersonAdapter;
import com.jhomlala.persons.myapplication.model.Person;
import com.jhomlala.persons.myapplication.viewmodel.PersonViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PersonsFragment extends Fragment {
    private final String TAG = "PersonsFragment";
    private PersonViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private PersonAdapter mPersonAdapter;
    private ProgressDialog mProgressDialog;
    private Disposable mRemoveObservable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_persons_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(PersonViewModel.class);
        mViewModel.getPersons().observe(getActivity(), personList-> {
            updateRecycler(personList);
        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupPersonRecycler(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRemoveObservable.dispose();
    }

    private void setupPersonRecycler(View rootView) {
        mRecyclerView = rootView.findViewById(R.id.persons_recycler_view);
        List<Person> initalPersonList = new ArrayList<>();
        RecyclerViewClickListener clickListener = ((view, position) -> {
            removePerson(position);
        });
        mPersonAdapter = new PersonAdapter(initalPersonList,clickListener);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mPersonAdapter);

    }

    private void removePerson(int position) {
        showLoadingDialog();
        mRemoveObservable = io.reactivex.Observable.defer((Callable<ObservableSource<String>>) () -> {
            boolean status = mViewModel.removePerson(position);
            String statusCode = status == true ? "Success": "Error";
            return io.reactivex.Observable.just(statusCode);
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(status -> {
            Log.d(TAG,"Element removed");
            hideLoadingDialog();
            Toast.makeText(getActivity(), "Person removed",
                    Toast.LENGTH_LONG).show();
        });


    }

    private void updateRecycler(List<Person> personList) {
        if (mPersonAdapter != null) {
            Log.d(TAG, "Update recycler");
            mPersonAdapter.updatePersonList(personList);
            mPersonAdapter.notifyDataSetChanged();
        }
    }

    private void showLoadingDialog(){
       mProgressDialog = ProgressDialog.show(getActivity(), "",
                "Loading. Please wait...", true);
    }

    private void hideLoadingDialog(){
        if (mProgressDialog != null){
            mProgressDialog.cancel();
        }
    }
}
