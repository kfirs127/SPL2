package bgu.spl.mics;

import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.passiveObjects.Ewok;
import bgu.spl.mics.application.services.HanSoloMicroservice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

import org.junit.jupiter.api.Test;
import sun.plugin2.jvm.RemoteJVMLauncher;

import java.util.concurrent.TimeUnit;


import static org.junit.jupiter.api.Assertions.*;


public class MessageBusTest {
    private MessageBusImpl messageBus;

    @BeforeEach
    public void setUp() {
       messageBus = new MessageBusImpl();
    }

    @Test
    public void testSendEvent() {
        HanSoloMicroservice hanSolo = new HanSoloMicroservice();
        messageBus.register(hanSolo);
        Event<Boolean> event = new AttackEvent();
        Future<Boolean> future1 = messageBus.sendEvent(event);
        Future<Boolean> future2 = messageBus.sendEvent(event);
        future1.resolve(true);
        future2.resolve(true);
        assertTrue(future1.get() && !future2.get());
    }

    @Test
    public void  testSendBroadcast() throws InterruptedException {
        HanSoloMicroservice hanSolo = new HanSoloMicroservice();
        messageBus.register(hanSolo);
        Event<Boolean> event = new AttackEvent();
        Future<Boolean> future = messageBus.sendEvent(event);
        Message message = messageBus.awaitMessage(hanSolo);
        assertEquals(event , message);
    }

}
