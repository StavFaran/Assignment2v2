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

	public Promise<Computer> down(){
		Promise<Computer> promise = new Promise<>();
		if (!isLocked.compareAndSet(false, true)){
			promiseQueue.enqueue(promise);
		}else {
			promise.resolve(_computer);
		}
		return promise;
	}

	public void up() throws InterruptedException {
		if (!promiseQueue.isEmpty())
			promiseQueue.dequeue().resolve(_computer);
		else
			isLocked.set(false);
	}

	public Computer getComputer(){
		return _computer;
	}
}
