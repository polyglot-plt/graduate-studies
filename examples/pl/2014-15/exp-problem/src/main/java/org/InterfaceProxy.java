package org;

public class InterfaceProxy implements Interface {

    A a;
    B b;

    public InterfaceProxy(String name, double price, int age, String lastName) {
        a = new A(name, price, age);
        b = new B(a, lastName);
    }

    @Override
    public String getLastName() {
        return b.getLastName();
    }

    @Override
    public String getName() {
        return a.getName();
    }

    @Override
    public double getPrice() {
        return a.getPrice();
    }

    @Override
    public int getAge() {
        return a.getAge();
    }

    @Override
    public void m1(int val) {
        a.m1(val);
    }

    @Override
    public void m2(String cad) {
        b.m2(cad);
    }

    @Override
    public int m3() {
        return b.m3();
    }
}
