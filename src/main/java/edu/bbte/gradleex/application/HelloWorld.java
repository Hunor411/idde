package edu.bbte.gradleex.application;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello " + System.getProperty("name", "stranger"));
    }
}