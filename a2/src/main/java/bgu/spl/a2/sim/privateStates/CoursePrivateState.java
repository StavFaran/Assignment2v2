package bgu.spl.a2.sim.privateStates;

import java.util.LinkedList;
import java.util.List;

import bgu.spl.a2.PrivateState;

/**
 * this class describe course's private state
 */
public class CoursePrivateState extends PrivateState{

	private String Course;
	private Integer availableSpots;
	private Integer registered;
	protected List<String> regStudents;
	private List<String> prequisites;
	
	/**
 	 * Implementors note: you may not add other constructors to this class nor
	 * you allowed to add any other parameter to this constructor - changing
	 * this may cause automatic tests to fail..
	 */
	public CoursePrivateState() {
		history = new LinkedList<>();
		registered = 0;
		regStudents = new LinkedList<>();
		prequisites = new LinkedList<>();
	}

	public Integer getAvailableSpots() {
		return availableSpots;
	}

	public Integer getRegistered() {
		return registered;
	}

	public List<String> getRegStudents() {
		return regStudents;
	}

	public List<String> getPrequisites() {
		return prequisites;
	}

	public void setPrequisites(LinkedList<String> prequisites){
		this.prequisites = prequisites;
	}

	public void setAvailableSpots(int spots){
		this.availableSpots = spots;
	}

	public void setRegistered(int reg){this.registered = reg;}

	public void setName(String name){
		Course = name;
	}
	public String getName(){
		return Course;
	}
}
