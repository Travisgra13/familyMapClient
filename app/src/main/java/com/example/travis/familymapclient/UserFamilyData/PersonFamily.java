package com.example.travis.familymapclient.UserFamilyData;

import com.example.travis.familymapclient.Model.Person;

import java.util.ArrayList;

public class PersonFamily {
    private Person associatedPerson;
    private ArrayList<Person> allFamily;
    private ImmediateFamily immediateFamily;
    private ImmediateFamilyFemales immediateFamilyFemales;
    private ImmediateFamilyMales immediateFamilyMales;

    private FatherSideMales fatherSideMales;
    private FatherSideFemales fatherSideFemales;

    private MotherSideMales motherSideMales;
    private MotherSideFemales motherSideFemales;

    public PersonFamily(Person associatedPerson, ArrayList<Person> allFamily) {
        this.associatedPerson = associatedPerson;
        this.allFamily = allFamily;
    }

    public void partitionFamily() {
        for (int i = 0; i < allFamily.size(); i++) {
            Person currPerson = allFamily.get(i);

        }
    }

    public Person getAssociatedPerson() {
        return associatedPerson;
    }

    public void setAssociatedPerson(Person associatedPerson) {
        this.associatedPerson = associatedPerson;
    }

    public ArrayList<Person> getAllFamily() {
        return allFamily;
    }

    public void setAllFamily(ArrayList<Person> allFamily) {
        this.allFamily = allFamily;
    }

    private void partitionImmediateFamily(int index) {
        final int offsetIndex = index + 1;
        if (index == 0) {

        }
        else if (offsetIndex % 3 == 0) {
            //child
        }
        else if (offsetIndex % 3 == 1) {
            //mother
        }
        else if (offsetIndex % 3 == 2) {
            //father

        }

    }
}
