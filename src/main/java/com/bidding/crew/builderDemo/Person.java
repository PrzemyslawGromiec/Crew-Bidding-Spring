package com.bidding.crew.builderDemo;

public class Person {
    private String name;
    private int age;

    private Person() {
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    static PersonBuilder builder() {
        return new PersonBuilder();
    }


    static class PersonBuilder{

        private final Person person = new Person();

        PersonBuilder setName(String name) {
            person.name = name;
            return this;
        }

        PersonBuilder setAge(int age) {
            person.age = age;
            return this;
        }

        Person build() {
            return person;
        }

    }
}
