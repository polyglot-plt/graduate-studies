package org;

public class A {

    String name;
    double price;
    int age;

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getAge() {
        return age;
    }

    public A(String name, double price, int age) {
        this.name = name;
        this.price = price;
        this.age = age;
    }

    public void m1(int a) {
        age = a;
    }
}
