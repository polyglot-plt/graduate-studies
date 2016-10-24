package org;

public class B {

    A pal;
    String lastName;

    public String getLastName() {
        return lastName;
    }

    public B(A pal, String lastName) {
        this.pal = pal;
        this.lastName = lastName;
    }

    public void m2(String cad) {
        pal.name = cad;
    }

    public int m3() {
        return pal.age;
    }
}
