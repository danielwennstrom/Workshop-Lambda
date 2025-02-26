package se.lexicon;

import se.lexicon.data.DataStorage;
import se.lexicon.model.Gender;
import se.lexicon.model.Person;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Exercises {

    private final static DataStorage storage = DataStorage.INSTANCE;

    /*
       1.	TODO: Find everyone that has firstName: “Erik” using findMany().
    */
    public static void exercise1(String message) {
        System.out.println(message);

        List<Person> allFound = storage.findMany(p -> p.getFirstName().equalsIgnoreCase("Erik"));

        allFound.forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        2.	TODO: Find all females in the collection using findMany().
     */
    public static void exercise2(String message) {
        System.out.println(message);

        List<Person> allFound = storage.findMany(p -> p.getGender().equals(Gender.FEMALE));

        allFound.forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        3.	TODO: Find all who are born after (and including) 2000-01-01 using findMany().
     */
    public static void exercise3(String message) {
        System.out.println(message);

        List<Person> allFound = storage.findMany(p -> p.getBirthDate().isAfter(LocalDate.parse("2000-01-01")));

        allFound.forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        4.	TODO: Find the Person that has an id of 123 using findOne().
     */
    public static void exercise4(String message) {
        System.out.println(message);

        Person found = storage.findOne(p -> p.getId() == 123);

        System.out.println(found);
        System.out.println("----------------------");
    }

    /*
        5.	TODO: Find the Person that has an id of 456 and convert to String with following content:
            “Name: Nisse Nilsson born 1999-09-09”. Use findOneAndMapToString().
     */
    public static void exercise5(String message) {
        System.out.println(message);

        Predicate<Person> filter = p -> p.getId() == 456;
        Function<Person, String> personToString = p -> "Name: " + p.getFirstName()
                + " " + p.getLastName() + " born " + p.getBirthDate();
        String result = storage.findOneAndMapToString(filter, personToString);

        System.out.println(result);
        System.out.println("----------------------");
    }

    /*
        6.	TODO: Find all male people whose names start with “E” and convert each to a String using findManyAndMapEachToString().
     */
    public static void exercise6(String message) {
        System.out.println(message);

        Predicate<Person> filter = p -> p.getFirstName().startsWith("E");
        Function<Person, String> personToString = p -> "Name: " + p.getFirstName() + " " + p.getLastName();
        List<String> result = storage.findManyAndMapEachToString(filter, personToString);

        result.forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        7.	TODO: Find all people who are below age of 10 and convert them to a String like this:
            “Olle Svensson 9 years”. Use findManyAndMapEachToString() method.
     */
    public static void exercise7(String message) {
        System.out.println(message);

        Predicate<Person> filter = p -> {
            LocalDate today = LocalDate.now();
            Period age = Period.between(p.getBirthDate(), today);
            return age.getYears() < 10;
        };

        List<String> result = storage.findManyAndMapEachToString(filter, p -> {
            Period age = Period.between(p.getBirthDate(), LocalDate.now());
            return p.getFirstName() + " " + p.getLastName() + " " + age.getYears() + " years";
        });

        result.forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        8.	TODO: Using findAndDo() print out all people with firstName “Ulf”.
     */
    public static void exercise8(String message) {
        System.out.println(message);

        Predicate<Person> filter = p -> p.getFirstName().equals("Ulf");
        storage.findAndDo(filter, p -> {
            System.out.println(p.getFirstName() + " " + p.getLastName());
        });

        System.out.println("----------------------");
    }

    /*
        9.	TODO: Using findAndDo() print out everyone who have their lastName contain their firstName.
     */
    public static void exercise9(String message) {
        System.out.println(message);

        Predicate<Person> filter = p -> p.getLastName().contains(p.getFirstName());
        storage.findAndDo(filter, p -> {
            System.out.println(p.getFirstName() + " " + p.getLastName());
        });

        System.out.println("----------------------");
    }

    /*
        10.	TODO: Using findAndDo() print out the firstName and lastName of everyone whose firstName is a palindrome.
     */
    public static void exercise10(String message) {
        System.out.println(message);

        Predicate<Person> filter = p -> p.getFirstName().toLowerCase()
                .contentEquals(new StringBuilder(p.getFirstName().toLowerCase())
                        .reverse());

        storage.findAndDo(filter, p -> {
            System.out.println(p.getFirstName() + " " + p.getLastName());
        });

        System.out.println("----------------------");
    }

    /*
        11.	TODO: Using findAndSort() find everyone whose firstName starts with A sorted by birthdate.
     */
    public static void exercise11(String message) {
        System.out.println(message);

        List<Person> allFound = storage.findAndSort(p -> p.getFirstName().startsWith("A"),
                Comparator.comparing(Person::getBirthDate));

        allFound.forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        12.	TODO: Using findAndSort() find everyone born before 1950 sorted reversed by lastest to earliest.
     */
    public static void exercise12(String message) {
        System.out.println(message);

        Predicate<Person> filter = p -> p.getBirthDate().isBefore(LocalDate.parse("1950-01-01"));
        List<Person> allFound = storage.findAndSort(filter, Comparator.comparing(Person::getBirthDate).reversed());

        allFound.forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        13.	TODO: Using findAndSort() find everyone sorted in following order: lastName > firstName > birthDate.
     */
    public static void exercise13(String message) {
        System.out.println(message);

        Comparator<Person> comparator = Comparator.comparing(Person::getLastName)
                .thenComparing(Person::getFirstName)
                .thenComparing(Person::getBirthDate);
        List<Person> allSorted = storage.findAndSort(comparator);

        allSorted.forEach(System.out::println);
        System.out.println("----------------------");
    }

}