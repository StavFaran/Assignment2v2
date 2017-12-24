package bgu.spl.a2.sim;
import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import sun.misc.Queue;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 
 * this class is related to {@link Computer}
 * it indicates if a computer is free or not
 * 
 * Note: this class can be implemented without any synchronization. 
 * However, using synchronization will be accepted as long as the implementation is blocking free.
 *
 */
public class SuspendingMutex {
	private Computer _computer;
	private AtomicBoolean isLocked;
	private Queue<Promise> promiseQueue;

	public SuspendingMutex(Computer computer){
		_computer = computer;
		isLocked = new AtomicBoolean(false);
		promiseQueue = new Queue<>();
	}
	
	/**
	 * Computer acquisition procedure
	 * Note that this procedure is non-blocking and should return immediatly
	 * 
	 * @param computerType
	 * 					computer's type
	 * 
	 * @return a promise for the requested computer
	 */
	public Promise<Computer> down(String computerType){
		if (!isLocked.compareAndSet(false, true)){
			Promise<Computer> promise = new Promise<>();
			promiseQueue.enqueue(promise);
			return promise;
		}else return null;
	}
	/**
	 * Computer return procedure
	 * releases a computer which becomes available in the warehouse upon completion
	 * 
	 * @param computer
	 */
	public void up(Computer computer){
		isLocked.compareAndSet(true, false);
		//Go over the Queue and activate the callbacks?
	}

	public Computer getComputer(){
		return _computer;
	}
}
