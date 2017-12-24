package bgu.spl.a2.sim.privateStates;

import java.util.LinkedList;
import java.util.List;

import bgu.spl.a2.PrivateState;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.sun.xml.internal.ws.developer.Serialization;

/**
 * this class describe department's private state
 */
public class DepartmentPrivateState extends PrivateState{
	private String Department;
	private List<String> courseList;
	private List<String> studentList;
	
	/**
 	 * Implementors note: you may not add other constructors to this class nor
	 * you allowed to add any other parameter to this constructor - changing
	 * this may cause automatic tests to fail..
	 */
	public DepartmentPrivateState() {
		history = new LinkedList<>();
		courseList = new LinkedList<>();
		studentList = new LinkedList<>();
	}

	public List<String> getCourseList() {
		return courseList;
	}

	public List<String> getStudentList() {
		return studentList;
	}

	public void setName(String name){
		Department = name;
	}
	public String getName(){
		return Department;
	}
	
}
