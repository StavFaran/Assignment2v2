package bgu.spl.a2;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

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

	private ConcurrentHashMap<String, ConcurrentLinkedQueue<Action>> actorListOfActions;
	private ConcurrentHashMap<String, PrivateState> actorPrivateState;
	private ConcurrentHashMap<String, AtomicBoolean> availableActors;

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
		availableActors = new ConcurrentHashMap<>();
		vm = new VersionMonitor();

		myThreads = new Thread[this.nthreads];
		for(int i=0; i<this.nthreads; i++) {
			myThreads[i] = new Thread(() -> {
				boolean repeat=true;
				while(repeat && (!Thread.currentThread().isInterrupted())) {
					int myversion = vm.getVersion();
					repeat = false;

					for (Map.Entry<String, AtomicBoolean> entry : availableActors.entrySet()) {
						String actor = entry.getKey();
						if (availableActors.get(actor).compareAndSet(true, false)) {
							if (!actorListOfActions.get(actor).isEmpty()) {
								actorListOfActions.get(actor).poll().handle(this, actor, actorPrivateState.get(actor));
							}
							//if (!actorListOfActions.get(actor).isEmpty())
							repeat = repeat || (!actorListOfActions.get(actor).isEmpty());
								availableActors.get(actor).set(true);
						}
//								callAction(entry.getKey());
					}
					if(!repeat) {
						repeat = true;
						try {
							vm.await(myversion);
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							repeat = false;
						}
					}
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
	public void submit(Action<?> action, String actorId, PrivateState actorState) {

//		System.out.println("submit action");
		availableActors.putIfAbsent(actorId, new AtomicBoolean(true));
		actorListOfActions.putIfAbsent(actorId, new ConcurrentLinkedQueue<>());
		actorPrivateState.putIfAbsent(actorId,actorState);
		if (action != null) {
			actorListOfActions.get(actorId).add(action);
			vm.inc();
		}
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
		for(Thread thread: myThreads){
			thread.join();
		}

	}

	/**
	 * start the threads belongs to this thread pool
	 */
	public void start() {
		for(int i=0; i<nthreads; i++)
			myThreads[i].start();
	}

	public void callAction(String actorId){

	}

}