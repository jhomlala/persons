package com.jhomlala.persons.data;

import com.jhomlala.persons.model.Person;

import java.util.Random;


public class PersonDataSource {

    private String[] names = {"Jakub", "Alicja", "Jan", "Patryk", "Maciej", "Eliza"};
    private String[] surnames = {"Kowalski", "Nowak", "Robak"};
    private Random random;

    public PersonDataSource() {
        random = new Random();
    }

    public Person generateRandomPerson() {
        return new Person(getRandomString(names), getRandomString(surnames));
    }

    private String getRandomString(String[] array) {
        int randomPosition = new Random().nextInt(array.length);
        return array[randomPosition];
    }


}
