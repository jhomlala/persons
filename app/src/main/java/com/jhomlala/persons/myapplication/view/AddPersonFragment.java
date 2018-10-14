package com.jhomlala.persons.myapplication.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.jhomlala.persons.myapplication.R;
import com.jhomlala.persons.myapplication.model.Person;
import com.jhomlala.persons.myapplication.viewmodel.PersonViewModel;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;


public class AddPersonFragment extends Fragment {

    private final String TAG = "AddPersonFragment";
    private PersonViewModel mViewModel;
    private int mNameLength;
    private int mSurnameLength;
    private Button mAddPersonButton;
    private List<Disposable> mDisposableList;

    public AddPersonFragment() {
        mDisposableList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_person_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText name = view.findViewById(R.id.edit_person_name);
        EditText surname = view.findViewById(R.id.edit_person_surname);
        mAddPersonButton = view.findViewById(R.id.button_add_person);
        mAddPersonButton.setEnabled(false);

        Disposable nameDisposable = RxTextView.textChanges(name).subscribe(text -> {
            mNameLength = text.length();
            checkAddButtonState();
        });

        mDisposableList.add(nameDisposable);

        Disposable surnameDisposable = RxTextView.textChanges(surname).subscribe(text -> {
            mSurnameLength = text.length();
            checkAddButtonState();
        });
        mDisposableList.add(surnameDisposable);

        Disposable buttonDisposable = RxView.clicks(mAddPersonButton)
                .subscribe(aVoid -> {
                    addPerson(name.getText().toString(), surname.getText().toString());
                });

        mDisposableList.add(buttonDisposable);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(PersonViewModel.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        for (Disposable disposable: mDisposableList){
            disposable.dispose();
        }
    }

    private void checkAddButtonState() {
        Log.d(TAG, "Check add button state: mNameLength: " + mNameLength + " mSurnameLength: " + mSurnameLength);

        if (mNameLength <= 3 || mSurnameLength <= 3) {
            mAddPersonButton.setEnabled(false);
            return;
        }
        if (mNameLength > 3 && mSurnameLength > 3) {
            mAddPersonButton.setEnabled(true);
        }
    }

    private void addPerson(String name, String surname) {
        mViewModel.addPerson(new Person(name, surname));
        Toast.makeText(getActivity(), "Person: " + name + " " + surname + " added.",
                Toast.LENGTH_LONG).show();
    }
}
