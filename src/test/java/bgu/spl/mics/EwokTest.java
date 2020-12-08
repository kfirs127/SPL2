package bgu.spl.mics;

import bgu.spl.mics.application.passiveObjects.Ewok;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;


import static org.junit.jupiter.api.Assertions.*;


public class EwokTest {

    private Ewok ewok;

    @BeforeEach
    public void setUp() {
        ewok = new Ewok(1);
    }

    @Test
    public void testAcquire(){
        ewok.acquire();
        ewok.setSerialNumber(4);
        assertFalse(ewok.getAvailable() && ewok.getSerialNumber() != 4);
    }

    @Test
    public void testRelease(){
        ewok.release();
        ewok.setSerialNumber(4);
        assertTrue(ewok.getAvailable() && ewok.getSerialNumber() == 4);
    }

    @Test
    public void testRelease_Acquire(){
        ewok.release();
        ewok.acquire();
        assertFalse(ewok.getAvailable());
    }

    @Test
    public void testAcquire_Release(){
        ewok.acquire();
        ewok.release();
        assertTrue(ewok.getAvailable());
    }

    @Test
    public void towSetSerialNumber(){
        ewok.setSerialNumber(3);
        ewok.setSerialNumber(4);
        assertEquals(ewok.getSerialNumber(), 4);
    }

    @Test
    public void testMultiEwokBool(){
        Ewok secondEwok = new Ewok(2);
        ewok.acquire();
        secondEwok.release();
        assertTrue(!ewok.getAvailable() && secondEwok.getAvailable());
    }


    @Test
    public void testMultiEwokInt(){
        Ewok secondEwok = new Ewok(2);
        ewok.setSerialNumber(3);
        secondEwok.setSerialNumber(4);
        assertTrue(ewok.getSerialNumber() == 3 && secondEwok.getSerialNumber() == 4);
    }
}
