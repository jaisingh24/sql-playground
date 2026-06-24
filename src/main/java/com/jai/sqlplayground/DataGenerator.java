package com.jai.sqlplayground;

import com.github.javafaker.Faker;

import java.io.FileWriter;
import java.io.IOException;

public class DataGenerator {

    public static void main(String[] args) throws IOException {

        Faker faker = new Faker();

        FileWriter writer = new FileWriter("employees.csv");

        writer.append("name,age,city,department,salary\n");

        String[] departments = {"IT", "HR", "Finance", "Sales"};

        for (int i = 0; i < 100000; i++) {

            String name = faker.name().fullName();
            int age = faker.number().numberBetween(20, 60);
            String city = faker.address().city();
            String department =
                    departments[faker.random().nextInt(departments.length)];
            int salary = faker.number().numberBetween(30000, 150000);

            writer.append(String.format(
                    "%s,%d,%s,%s,%d\n",
                    name,
                    age,
                    city,
                    department,
                    salary
            ));
        }

        writer.close();

        System.out.println("100K records generated!");
    }
}