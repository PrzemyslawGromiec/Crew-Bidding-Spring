package com.bidding.crew.builderDemo;

public class PersonDemo {
    public static void main(String[] args) {

        Person person2 = Person.builder()
                .setName("Adam")
                .setAge(20)
                .build();
    }
}
