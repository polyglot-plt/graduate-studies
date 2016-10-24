package org;

public class Main {

    public static void main(String[] args) throws Exception {

        Creator creator = new Creator(Interface.class, A.class, B.class);

        Interface obj = creator.create("hola", 23.0, 11, "Ana");
//        Interface obj = new InterfaceProxy("hola", 23.0, 11, "Ana");


        System.out.println(obj.getName());

        obj.m2("otro hola");

        System.out.println(obj.getName());

        obj.m1(12);

        System.out.println(obj.getAge());

        System.out.println(obj.getLastName());
    }

}
