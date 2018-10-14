package com.jhomlala.persons.myapplication.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import com.jhomlala.persons.myapplication.data.PersonDataSource;
import com.jhomlala.persons.myapplication.model.Person;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PersonViewModel extends ViewModel {
    private final String TAG = "PersonViewModel";

    private MutableLiveData<List<Person>> mPersons;
    private PersonDataSource mPersonDataSource;
    private Disposable mGeneratorObservable;

    public PersonViewModel() {
        mPersonDataSource = new PersonDataSource();
        mGeneratorObservable = Observable.interval(5, TimeUnit.SECONDS).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                t -> {
                    Log.i(TAG, "Generating person");
                    addPerson(mPersonDataSource.generateRandomPerson());
                }, Throwable::printStackTrace
        );
    }

    public MutableLiveData<List<Person>> getPersons() {
        if (mPersons == null) {
            mPersons = new MutableLiveData<>();
            mPersons.setValue(new ArrayList<>());
            loadInitialData();
        }
        return mPersons;
    }

    private void loadInitialData() {
        for (int i = 0; i < 5; i++) {
            mPersons.getValue().add(mPersonDataSource.generateRandomPerson());
        }
    }

    public void addPerson(Person person) {
        Log.i(TAG, "Add new person: " + person);
        List<Person> tempList = mPersons.getValue();
        tempList.add(person);
        mPersons.postValue(tempList);
    }

    public Boolean removePerson(int position) {
        try {
            Log.d(TAG,"Remove person on position: " + position);
            List<Person> tempList = mPersons.getValue();
            tempList.remove(position);
            //long task
            Thread.sleep(1000);
            mPersons.postValue(tempList);
            return true;
        } catch (Exception exc) {
            exc.printStackTrace();
            return false;
        }

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mGeneratorObservable.dispose();
    }
}
