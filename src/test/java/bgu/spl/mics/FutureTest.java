package bgu.spl.mics;

import bgu.spl.mics.application.services.StringFuture;
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

    private StringFuture future;

    @BeforeEach
    public void setUp() {
        future = new StringFuture();
    }

    @Test
    public void testResolve(){
        String str = "someResult";
        future.resolve(str);
        assertTrue(future.isDone() && str.equals(future.get()));
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
        String result = future.get(1000 , TimeUnit.MILLISECONDS);
        assertNull(result);
        String str = "someResult";
        future.resolve(str);
        result = future.get(1000,  TimeUnit.MILLISECONDS);
        assertNotNull(result);
    }

    @Test
    public void testMultiResolve(){
        String str1 = "firstResult";
        future.resolve(str1);
        String str2 = "secondResult";
        future.resolve(str2);
        String result1 = future.get();
        String result2 = future.get(1000 ,TimeUnit.MILLISECONDS);
        assertTrue(result1.equals(str1) || result1.equals(result2));
    }

    @Test
    public void testMultiFuture(){
        StringFuture secondFuture = new StringFuture();
        String str1 = "firstResult";
        future.resolve(str1);
        String str2 = "secondResult";
        secondFuture.resolve(str2);
        assertEquals(str1, future.get());
        assertEquals(str2, secondFuture.get());
    }
}
