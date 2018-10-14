package com.jhomlala.persons.viewmodel;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.support.annotation.NonNull;

import com.jhomlala.persons.model.Person;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class PersonViewModelTest {

    private PersonViewModel personViewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    @BeforeClass
    public static void setUpRxSchedulers() {
        Scheduler immediate = new Scheduler() {
            @Override
            public Disposable scheduleDirect(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {
                return super.scheduleDirect(run, 0, unit);
            }

            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run);
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
    }

    @Before
    public void initPersonViewModel() {
        personViewModel = new PersonViewModel();
    }

    @Test
    public void personsListSelectedNotNull() {
        assertNotNull(personViewModel.getPersons().getValue());
    }

    @Test
    public void personsListSizeAfterPersonAdd() {
        personViewModel.addPerson(new Person("Test", "Test"));
        assertEquals(personViewModel.getPersons().getValue().size(), 1);
    }

    @Test
    public void personsListSizeAfterPersonRemove() {
        personViewModel.removePerson(0);
        assertEquals(personViewModel.getPersons().getValue().size(), 0);
    }


}
