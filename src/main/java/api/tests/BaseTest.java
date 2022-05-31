package api.tests;

import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public class BaseTest {

    @BeforeMethod
    public void beforeMethod(Method m){
        System.out.println("Starting test: " + m.getName());
        System.out.println("Thread id: " +Thread.currentThread().getName());

    }
}
