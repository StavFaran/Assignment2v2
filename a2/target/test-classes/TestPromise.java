import junit.framework.Assert;
import junit.framework.TestCase;

public class TestPromise extends TestCase {

	private Promise<T> p;
	
	/**
	 * Setup for a test
	 */
	@Before protected void setUp() throws exception{
		p = new Promise<Integer>();
	}
	
	@Test public void TestResolve(){
		try{
			p.resolve(5);
			try{
				p.resolve(6);
				Assert.fail();
			}catch(IllegalStateException e){
				int x = p.resolve
			}
		}
	}
}
