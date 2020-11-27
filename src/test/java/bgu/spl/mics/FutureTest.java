package bgu.spl.mics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;


import static org.junit.jupiter.api.Assertions.*;


public class FutureTest {

    private Future<String> future;

    @BeforeEach
    public void setUp() {
        future = new Future<>();
    }

    @Test
    public void testResolve(){
        String str = "someResult";
        future.resolve(str);
        assertTrue(future.isDone());
        assertTrue(str.equals(future.get()));
    }

    @Test
    public void testGetT(){
        String str = "someResult";
        future.resolve(str);
        Object result = future.get();
        assertEquals(future , result);
    }

    @Test
    public void testIsDone(){
        assertFalse(future.isDone());
        String str = "someResult";
        future.resolve(str);
        assertTrue(future.isDone());
    }

    @Test
    public void testGetTWV(){
        String result = future.get(1000 ,  TimeUnit.valueOf("500"));
        assertNull(result);
        String str = "someResult";
        future.resolve(str);
        result = future.get(1000,  TimeUnit.valueOf("500"));
        assertNotNull(result);
    }

    @Test
    public void testMulti(){
        String str1 = "firstResult";
        future.resolve(str1);
        String str2 = "secondResult";
        future.resolve(str2);
        String result1 = future.get();
        String result2 = future.get(1000 ,  TimeUnit.valueOf("500"));
        assertTrue(str1.equals(result1) || str1.equals(result2));
        assertTrue( str2.equals(result1) || str2.equals(result2) );
    }
}
