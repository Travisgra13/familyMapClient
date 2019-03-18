package com.example.travis.familymapclient.UserFamilyData;

import com.example.travis.familymapclient.Model.Person;

public class ImmediateFamily {
    private Person child;
    private Person spouse;

    public ImmediateFamily(Person child, Person spouse) {
        this.child = child;
        this.spouse = spouse;
    }

    public Person getChild() {
        return child;
    }

    public void setChild(Person child) {
        this.child = child;
    }

    public Person getSpouse() {
        return spouse;
    }

    public void setSpouse(Person spouse) {
        this.spouse = spouse;
    }
}
