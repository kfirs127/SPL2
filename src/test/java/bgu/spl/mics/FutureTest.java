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
    public void setUp(){
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
        TimeUnit time = TimeUnit.valueOf("500");
        String result = future.get(1000, time);
        assertNull(result);
        String str = "someResult";
        future.resolve(str);
        result = future.get(1000, time);
        assertNotNull(result);
    }

}
