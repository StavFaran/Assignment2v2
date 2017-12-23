package bgu.spl.a2;

import bgu.spl.a2.TestClass.ActionBasic;
import bgu.spl.a2.TestClass.ActionExtended;
import bgu.spl.a2.TestClass.BaseState;
import bgu.spl.a2.sim.actions.AddNewStudent;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;
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

//        Action<String> action = new Action<String>() {
//            @Override
//            protected void start() {
//                System.out.println("Hello Goodbye");
//            }
//        };
//        actorThreadPool.submit(action, "stav", new PrivateState(){});
    }

    @Test
    public void shutdown() throws Exception {
    }

    @Test
    public void start() throws Exception {


        Action<Integer> action = new AddNewStudent("stav");
//        Action<Integer> action2 = new ActionBasic();
//        Action<Integer> action3 = new ActionBasic();
//        Action<Integer> action4 = new ActionExtended();
        actorThreadPool.submit(action, "stav", new StudentPrivateState());
//        actorThreadPool.submit(action2, "natalie", new BaseState());
//        actorThreadPool.submit(action3, "stav", new BaseState());
//        actorThreadPool.submit(action4, "infi", new BaseState());
        actorThreadPool.start();
    }

}