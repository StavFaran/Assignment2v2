package bgu.spl.a2;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * represents an actor thread pool - to understand what this class does please
 * refer to your assignment.
 *
 * Note for implementors: you may add methods and synchronize any of the
 * existing methods in this class *BUT* you must be able to explain why the
 * synchronization is needed. In addition, the methods you add can only be
 * private, protected or package protected - in other words, no new public
 * methods
 */
public class ActorThreadPool {

	private int nthreads;
	private Thread[] myThreads;
	private AtomicInteger actorsAvailable;

	private ConcurrentHashMap<String, LinkedList<Action>> actorListOfActions;
	private ConcurrentHashMap<String, PrivateState> actorPrivateState;
	private ConcurrentHashMap<String, AtomicBoolean> actorIsLocked;

	private VersionMonitor vm;

	/**
	 * creates a {@link ActorThreadPool} which has nthreads. Note, threads
	 * should not get started until calling to the {@link #start()} method.
	 *
	 * Implementors note: you may not add other constructors to this class nor
	 * you allowed to add any other parameter to this constructor - changing
	 * this may cause automatic tests to fail..
	 *
	 * @param nthreads
	 *            the number of threads that should be started by this thread
	 *            pool
	 */
	public ActorThreadPool(int nthreads){
		this.nthreads = nthreads;
		actorListOfActions = new ConcurrentHashMap<>();
		actorPrivateState = new ConcurrentHashMap<>();
		actorIsLocked = new ConcurrentHashMap<>();
		vm = new VersionMonitor();

		myThreads = new Thread[this.nthreads];
		for(int i=0; i<this.nthreads; i++) {
			myThreads[i] = new Thread(() -> {
				while(true) {
					int myversion = vm.getVersion();

					for (Map.Entry<String, AtomicBoolean> entry : actorIsLocked.entrySet()) {
						if (!Thread.interrupted())
							if (entry.getValue().get() == false) {
								String actor = entry.getKey();
								callAction(actor, actorPrivateState.get(actor));
							}
							else break;

					}

					try {
						vm.await(myversion);
					} catch (InterruptedException e) {}
				}
			});
		}
	}

	/**
	 * getter for actors
	 * @return actors
	 */
	public Map<String, PrivateState> getActors(){
		return actorPrivateState;
	}

	/**
	 * getter for actor's private state
	 * @param actorId actor's id
	 * @return actor's private state
	 */
	public PrivateState getPrivateState(String actorId){
		if (actorPrivateState.contains(actorId))
			return actorPrivateState.get(actorId);
		else
			return null;
	}

	/**
	 * submits an action into an actor to be executed by a thread belongs to
	 * this thread pool
	 *
	 * @param action
	 *            the action to execute
	 * @param actorId
	 *            corresponding actor's id
	 * @param actorState
	 *            actor's private state (actor's information)
	 *
	 *            This function should be synchronized to keep it thread safe
	 */
	public synchronized void submit(Action<?> action, String actorId, PrivateState actorState) {
		if (!actorListOfActions.containsKey(actorId))
			addActorToPool(actorId, actorState);
		actorListOfActions.get(actorId).add(action);
		vm.inc();
		notifyAll();
	}

	/**
	 * closes the thread pool - this method interrupts all the threads and waits
	 * for them to stop - it is returns *only* when there are no live threads in
	 * the queue.
	 *
	 * after calling this method - one should not use the queue anymore.
	 *
	 * @throws InterruptedException
	 *             if the thread that shut down the threads is interrupted
	 */
	public void shutdown() throws InterruptedException {
		for(Thread thread: myThreads){
			thread.interrupt();
		}
	}

	/**
	 * start the threads belongs to this thread pool
	 */
	public void start() {
		for(int i=0; i<nthreads; i++)
			myThreads[i].start();
	}

	public synchronized void callAction(String actorId, PrivateState actorState){
		if (!actorListOfActions.get(actorId).isEmpty()) {
			beforeFunctionCall(actorId);
			actorListOfActions.get(actorId).removeFirst().handle(this, actorId, actorState);
			afterFunctionCall(actorId);
		}
	}

	public void beforeFunctionCall(String actorId){
		actorIsLocked.get(actorId).compareAndSet(false, true);
	}
	public synchronized void afterFunctionCall(String actorId) {
		actorIsLocked.get(actorId).compareAndSet(true, false);
		vm.inc();
		notifyAll();
	}

	public synchronized void addActorToPool(String actorId, PrivateState actorState){
		actorIsLocked.putIfAbsent(actorId, new AtomicBoolean(false));
		actorListOfActions.putIfAbsent(actorId, new LinkedList<>());
		actorPrivateState.putIfAbsent(actorId,actorState );
	}
	public void removeActorFromPool(String actorId){
		actorIsLocked.remove(actorId);
		actorListOfActions.remove(actorId);
		actorPrivateState.remove(actorId);
	}

}
