package assignment06;

import java.util.Comparator;

/**
 * A class representing a person -- suitable for extending into interesting
 * kinds of people -- like students or truck-drivers.
 *
 * @author Mark Young (A00000000)
 * @author Gianni Belizaire (A00487024)
 *
 */
public class Person implements Comparable<Person> {

    private String name;
    private String sex;
    private int age;
    private int height;
    private int weight;

    /**
     * Create a Person.
     *
     * @param name this Person's name
     * @param sex this Person's sex
     * @param age this Person's age
     * @param height this Person's height
     * @param weight this Person's weight
     */
    public Person(String name, String sex, int age, int height, int weight) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }

    /**
     * Create a Person with a placeholder name, sex, age, height, and weight.
     */
    public Person() {
        this("Baby Human", "", 0, 0, 0);
    }

    /**
     * Change this Person's name.
     *
     * @param newName this Person's new name.
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Return this Person's name.
     *
     * @return this Person's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Return this Person's sex
     *
     * @return this Person's sex
     */
    public String getSex() {
        return this.sex;
    }

    /**
     * Return this Person's age
     *
     * @return this Person's age
     */
    public int getAge() {
        return this.age;
    }

    /**
     * Return this Person's height
     *
     * @return this Person's height
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Return this Person's weight
     *
     * @return this Person's weight
     */
    public int getWeight() {
        return this.weight;
    }

    /**
     * Provide a simple report on this Person.
     */
    public void writeOutput() {
        System.out.println("Name: " + name);
    }

    /**
     * Check whether this Person has the same name as another.
     *
     * @param other the other Person (who possibly has the same name).
     * @return true if these people's names are identical; false otherwise.
     */
    public boolean hasSameName(Person other) {
        if (other == null) {
            return false;
        }

        return name.equalsIgnoreCase(other.name);
    }

    /**
     * Create a String representing this Person.
     *
     * @return a String with this Person's name.
     */
    @Override
    public String toString() {
        return "Person: " + name;
    }

    @Override
    public int compareTo(Person other) {
        return this.name.compareTo(other.name);
    }

    public static final Comparator<Person> BY_NAME = new Comparator<Person>() {
        @Override
        public int compare(Person p1, Person p2) {
            return p1.name.compareTo(p2.name);
        }
    };

    public static final Comparator<Person> BY_AGE = new Comparator<Person>() {
        @Override
        public int compare(Person p1, Person p2) {
            return Integer.compare(p1.age, p2.age);
        }
    };

    public static final Comparator<Person> BY_HEIGHT = new Comparator<Person>() {
        @Override
        public int compare(Person p1, Person p2) {
            return Integer.compare(p1.height, p2.height);
        }
    };
}
