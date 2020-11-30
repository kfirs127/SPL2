package bgu.spl.mics;

import bgu.spl.mics.application.CopyBroadcast;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.passiveObjects.Ewok;
import bgu.spl.mics.application.services.C3POMicroservice;
import bgu.spl.mics.application.services.CopyEvent;
import bgu.spl.mics.application.services.HanSoloMicroservice;
import com.sun.org.apache.xpath.internal.operations.Bool;
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
        future2.resolve(false);
        assertTrue(future1.get() && !future2.get());
    }

    @Test
    public void testComplete(){
        HanSoloMicroservice hanSolo = new HanSoloMicroservice();
        messageBus.register(hanSolo);
        Event<Boolean> event = new AttackEvent();
        Future<Boolean> future = messageBus.sendEvent(event);
        messageBus.complete(event , true);
        assertTrue(future.isDone());
    }

    @Test
    public void testSubscribeEvent() throws InterruptedException {
        HanSoloMicroservice hanSolo = new HanSoloMicroservice();
        C3POMicroservice C3PO = new C3POMicroservice();
        CopyEvent event1 = new CopyEvent("new event");
        messageBus.subscribeEvent(event1.getClass() , hanSolo );
        Future<String> future = hanSolo.sendEvent(event1);
        Message event2 = messageBus.awaitMessage(C3PO);
        assertEquals(event1 , event2);
    }

    @Test
    public void testBroadcast() throws InterruptedException{
        HanSoloMicroservice hanSolo = new HanSoloMicroservice();
        messageBus.register(hanSolo);
        Broadcast broadcast = new CopyBroadcast("new broadcast");
        messageBus.sendBroadcast(broadcast);
        Message message = messageBus.awaitMessage(hanSolo);
        assertEquals(broadcast , message);
    }

    @Test
    public void multiMicroService_MultiEvent(){
        HanSoloMicroservice hanSolo = new HanSoloMicroservice();
        C3POMicroservice C3PO = new C3POMicroservice();
        messageBus.register(hanSolo);
        messageBus.register(C3PO);
        Event<Boolean> event1 = new AttackEvent();
        Event<Boolean> event2 = new AttackEvent();
        Future<Boolean> future1 = messageBus.sendEvent(event1);
        Future<Boolean> future2 = messageBus.sendEvent(event2);
        messageBus.complete(event1 , true);
        messageBus.complete(event2 , true);
        assertTrue(future1.isDone() && future2.isDone());
        assertNotEquals(future1.get(),future2.get());
    }

    @Test
    public void testSubscribeBroadcast()throws InterruptedException{
        HanSoloMicroservice hanSolo = new HanSoloMicroservice();
        C3POMicroservice C3PO = new C3POMicroservice();
        Broadcast broadcast1 = new CopyBroadcast("new broadcast");
        messageBus.subscribeBroadcast(broadcast1.getClass() , C3PO);
        hanSolo.sendBroadcast(broadcast1);
        Message broadcast2 = messageBus.awaitMessage(C3PO);
        assertEquals( broadcast1 , broadcast2);
    }

}
