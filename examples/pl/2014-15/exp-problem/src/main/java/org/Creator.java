package org;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.Proxy;
import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Creator {

    Class type, origin, newClass;

    /**
     *
     * @param type La interfaz que debe cumplir (implementar) la clase del objeto que devuelva el método create
     * @param origin Clase "padre", solamente poseerá un constructor: por lo que una llamada al método
     *               "origin.getConstructors()" devolverá un array de tamaño 1 con este único constructor.
     * @param newClass Clase "hija", tendrá un único constructor cuyo primer parámetro será un objeto de tipo "origin"
     *                 y el resto serán datos particulares que requiera. Su papel será el de extender las funcionalidades
     *                 de "origin" lográndose de esta manera aumentar las funcionalidades de "origin" (expression problem).
     */
    public Creator(Class type, Class origin, Class newClass) {
        this.type = type;
        this.origin = origin;
        this.newClass = newClass;
    }

    /**
     * Constructor de un nuevo objeto que une las funcionalidades de los tipos origin y newClass
     *
     * Posibles maneras de implementarlo:
     *  1. Usando ASM
     *  2. Usando javassist
     *  3. Usando Java Scripting
     *  4. Generando una clase Java (el código fuente Java) y usando las bibliotecas que vienen con el JDK
     *  para compilar, cargar y ejecutar su código: usando reflection, classloaders, etc.
     *
     *  Sugerencias:
     *      - Buscar información sobre el patrón proxy.
     *      - Variante ASM: implica estudiar y conocer detalles de los opcodes del bytecode de Java
     *      - Variante javassist: no se necesita conocer sobre el bytecode (es la variante más simple).
     *      - Variante Java Scripting: implica conocer algún lenguaje con intérprete en Java (Clojure, JavaScript,
     *      JRuby), generar código en el lenguaje escogido y ejecutarlo con el API de Scripting de Java.
     *      - Variante de generar código Java: un tanto pesado (por lo largo y cosas a tener en cuenta),
     *      puede ser atractivo para el que quiera trabajar solamente con el JDK.
     *
     * @param params Datos necesarios para construir los objetos de las calses "origin" y "newClass"
     * @param <T>
     * @return Nuevo objeto
     */
    public <T> T create(Object... params) throws Exception {
        Constructor ctor1 = origin.getConstructors()[0];
        Object []params1 = Arrays.copyOfRange(params, 0, ctor1.getParameterCount());

        Object a = ctor1.newInstance(params1);
        Constructor ctor2 = newClass.getConstructors()[0];
        ArrayList<Object> params2 = new ArrayList<>();
        params2.add(a);
        Collections.addAll(params2, Arrays.copyOfRange(params, ctor1.getParameterCount(), params.length));
        Object b = ctor2.newInstance(params2.toArray());
        ProxyFactory f = new ProxyFactory();
        f.setInterfaces(new Class[]{type});
        Class c = f.createClass();
        MethodHandler mi = new MethodHandler() {
            public Object invoke(Object self, Method overridden, Method forwarder,
                                 Object[] args) throws Throwable {
                Method executer;
                Object target;
                try {
                    executer = origin.getMethod(overridden.getName(), overridden.getParameterTypes());
                    target = a;
                } catch (NoSuchMethodException e) {
                    executer = newClass.getMethod(overridden.getName(), overridden.getParameterTypes());
                    target = b;
                }
                return executer.invoke(target, args);
            }
        };

        T foo = (T) c.newInstance();
        ((Proxy) foo).setHandler(mi);
        return foo;
    }
}