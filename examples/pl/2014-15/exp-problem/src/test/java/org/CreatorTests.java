package org;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CreatorTests {

    Interface obj;

    @Before
    public void createTheObjects() throws Exception {
        Creator creator = new Creator(Interface.class, A.class, B.class);
        obj = creator.create("hola", 23.0, 11, "Ana");
    }

    @Test
    public void test_m2() {
        assertTrue(obj.getName().equals("hola"));
        obj.m2("otro hola");
        assertTrue(obj.getName().equals("otro hola"));
    }

    @Test
    public void test_m1() {
        assertTrue(obj.getAge() == 11);
        obj.m1(24);
        assertTrue(obj.getAge() == 24);
    }

    @Test
    public void test_lastName() {
        assertTrue(obj.getLastName().equals("Ana"));
    }


}
