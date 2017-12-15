package bgu.spl.a2;

import bgu.spl.a2.TestClass.ActionBasic;
import bgu.spl.a2.TestClass.BaseState;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class ActorThreadPoolTest {
    ActorThreadPool actorThreadPool;
    @Before
    public void setUp() throws Exception {
        actorThreadPool = new ActorThreadPool(10);
    }

    @After
    public void tearDown() throws Exception {
        actorThreadPool = null;
    }

    @Test
    public void submit() throws Exception {

        Action<String> action = new Action<String>() {
            @Override
            protected void start() {
                System.out.println("Hello Goodbye");
            }
        };
        actorThreadPool.submit(action, "stav", new PrivateState(){});
    }

    @Test
    public void shutdown() throws Exception {
    }

    @Test
    public void start() throws Exception {

        Action<Integer> action = new ActionBasic();
        actorThreadPool.submit(action, "stav", new BaseState());
        actorThreadPool.start();

//        submit(new Action() {
//
//            @Override
//            protected void start() {
//                System.out.println("Action 1 of Actor a was called");
//            }
//        }, "stav", new PrivateState());
//
//        Actor b = new Actor("Bob");
//        b.addActionToList(new Action() {
//            @Override
//            protected void start() {
//                System.out.println("Action 1 of Actor b was called");
//            }
//        });
//
//        Actor c = new Actor("Caleb");
//        c.addActionToList(new Action() {
//            @Override
//            protected void start() {
//                System.out.println("Action 1 of Actor c was called");
//            }
//        });
//
//        b.addActionToList(new Action() {
//            @Override
//            protected void start() {
//                System.out.println("Action 2 of Actor b was called");
//            }
//        });
//
//        actorThreadPool.addToList(a);
//        actorThreadPool.addToList(b);
//        actorThreadPool.addToList(c);
//
//        actorThreadPool.start();
//
//        a.addActionToList(new Action() {
//            @Override
//            protected void start() {
//                System.out.println("Action 2 of Actor a was called");
//            }
//        });
//        actorThreadPool.addToList(a);
//    }
    }

}