package com.jhomlala.persons.data;

import com.jhomlala.persons.model.Person;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataSourceTests {

    private PersonDataSource personDataSource;

    @Before
    public void initPersonDataSource(){
        personDataSource = new PersonDataSource();
    }

    @Test
    public void generatedPersonNotNull(){
        assertNotNull(personDataSource.generateRandomPerson());
    }

    @Test
    public void generatedPersonFieldsNotEmpty(){
        Person person = personDataSource.generateRandomPerson();
        assertNotEquals(person.getName().length(), 0);
        assertNotEquals(person.getSurname().length(), 0);
    }

}