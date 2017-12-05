package bgu.spl.a2;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VersionMonitorTest {
    /**Class Under a Test*/
    private VersionMonitor vm;
    /**
     *
     * Set up for a test.
     */
    @Before
    public void setUp() throws Exception {
        vm = new VersionMonitor();
    }

    /**
     * tears down the test subject
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        vm = null;
    }

    /**
     * Tests The Method getVersion
     * @Pre: none
     * @Post: getVersion = x+1
     * */
    @Test
    public void TestGetVersion(){
        try{
            int x = vm.getVersion();
            assertTrue( x >= 0);
        }catch(Exception e){
            Assert.fail();
        }
    }


    /**
     * Tests The Method Inc()
     * @Pre: none
     * @Post: getVersion = x+1
     *
     * */
    @Test
    public void TestInc(){
        try{
            int x = vm.getVersion();
            try{
                vm.inc();
                int y = vm.getVersion();
                assertEquals(x+1, y);
            }
            catch(Exception e){
                Assert.fail();
            }
        }catch(Exception e){
            Assert.fail();
        }
    }

    /**
     * Tests The Method Await()
     * @Pre: none
     * @Post: getVersion = x+1
     *
     * */
    @Test
    public void TestAwait(){
        final int currentVersion = vm.getVersion();
        Thread t1 = new Thread(()-> {
            try {
                vm.await(currentVersion);
            } catch (InterruptedException e) {
                Assert.fail();
            }
        });
        t1.start();
        vm.inc();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertNotSame((Integer)currentVersion, vm.getVersion());
    }
}

