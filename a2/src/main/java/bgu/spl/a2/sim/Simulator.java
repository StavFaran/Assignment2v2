/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.spl.a2.sim;
import java.io.*;
import java.sql.Array;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import bgu.spl.a2.Action;
import bgu.spl.a2.ActorThreadPool;
import bgu.spl.a2.PrivateState;
import bgu.spl.a2.sim.actions.*;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * A class describing the simulator for part 2 of the assignment
 */
public class Simulator {

	
	public static ActorThreadPool actorThreadPool;
	
	/**
	* Begin the simulation Should not be called before attachActorThreadPool()
	*/
    public static void start(){
		actorThreadPool.start();
    }
	
	/**
	* attach an ActorThreadPool to the Simulator, this ActorThreadPool will be used to run the simulation
	* 
	* @param myActorThreadPool - the ActorThreadPool which will be used by the simulator
	*/
	public static void attachActorThreadPool(ActorThreadPool myActorThreadPool){
		actorThreadPool = myActorThreadPool;
	}
	
	/**
	* shut down the simulation
	* returns list of private states
	*/
	public static ConcurrentLinkedQueue<PrivateState> end(){
		//TODO: replace method body with real implementation
		throw new UnsupportedOperationException("Not Implemented Yet.");
	}
	
	
	public static void main(String [] args) {
		//From the args I think we need to call attachThreadPool using a specified pool
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("result.ser"))) {
			Map<String, Object> data = (new Gson()).fromJson(new JsonReader(new FileReader(args[0])), new TypeToken<HashMap<String, Object>>() {}.getType());
			actorThreadPool = new ActorThreadPool(((Double) data.get("threads")).intValue());
			attachActorThreadPool(actorThreadPool);
			start();

			Warehouse warehouse = new Warehouse((List<Map<String, String>>) data.get("Computers"));

			Phase((List<Map<String, Object>>) data.get("Phase 1"));
			Phase((List<Map<String, Object>>) data.get("Phase 2"));
			Phase((List<Map<String, Object>>) data.get("Phase 3"));

			HashMap<String, PrivateState> toWrite = endSimulation();
			oos.writeObject(toWrite);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static  void Phase(List<Map<String,Object>> list){
		//add countdownlatch
		for(Map<String, Object> map: list)
			AddActorToPool(map);
	}
	public static void AddActorToPool(Map<String,Object> actionToAdd){
		Action action;
		String actor;
		PrivateState privateState;

		switch((String)actionToAdd.get("Action")) {
			case "Open Course":
				actor = (String) actionToAdd.get("Department");
				privateState = new DepartmentPrivateState();
				action = new OpenNewCourse(
						(String) actionToAdd.get("Course"),
						Integer.parseInt((String) actionToAdd.get("Space")),
						arrayToLinked((ArrayList) actionToAdd.get("Prerequisites")));
				break;
			case "Add Student":
				actor = (String) actionToAdd.get("Department");
				privateState = new DepartmentPrivateState();
				action = new AddNewStudent(
						(String) actionToAdd.get("Student"));
				break;
			case "Participate In Course":
				actor = (String) actionToAdd.get("Course");
				privateState = new CoursePrivateState();
				action = new ParticipatingInCourse(
								(String) actionToAdd.get("Course"),
								(String) actionToAdd.get("Student"),
								((ArrayList) actionToAdd.get("Grade")).indexOf(0));
				break;
			case "Unregister":
				actor = (String) actionToAdd.get("Course");
				privateState = new CoursePrivateState();
				action = new Unregister(
								(String) actionToAdd.get("Student"));
				break;
			case "Close Course":
				actor = (String) actionToAdd.get("Department");
				privateState = new DepartmentPrivateState();
				action = new CloseCourse(
								(String) actionToAdd.get("Course"));
				break;
			case "Add Spaces":
				actor = (String) actionToAdd.get("Course");
				privateState = new CoursePrivateState();
				action = new AddSpaces(
								Integer.parseInt((String) actionToAdd.get("Space")));
				break;
			case "Register With Preferences":
				actor = (String) actionToAdd.get("Student");
				privateState = new StudentPrivateState();
				action = new RegisterWithPreferences(
								(ArrayList) actionToAdd.get("Preferences"),
								(ArrayList) actionToAdd.get("Grade"));
				break;
			default:
				actor = null;
				action = null;
				privateState = new DepartmentPrivateState();
//			case "Administrative Check":
//				actor = (String)actionToAdd.get("Department");
//				actorThreadPool.submit(
//						new CheckAdministrativeObligations(
//								(ArrayList)actionToAdd.get("Students"),
//								(String)actionToAdd.get("Computer"),
//								(ArrayList)actionToAdd.get("Conditions")),
//						actor,
//						new StudentPrivateState());
//				break;

		}
		if(actor != null) {
			privateState.setName(actor);
			actorThreadPool.submit(action, actor, privateState);
		}



	}
	public static LinkedList<String> arrayToLinked(List<String> arrayList){
		LinkedList<String> linkedList = new LinkedList<>();
		for(String str: arrayList){
			linkedList.add(String.valueOf(str));
		}
		return linkedList;
	}

	public static HashMap<String,PrivateState> endSimulation(){

		ArrayList Departments = new ArrayList();
		actorType(Departments, 0);
		ArrayList Courses = new ArrayList();
		actorType(Courses, 1);
		ArrayList Students = new ArrayList();
		actorType(Students, 2);

		HashMap<String,PrivateState>data = new HashMap<>();
		data.put("stav", new DepartmentPrivateState());
		return data;

//		Object obj;
//		obj = new Object(){
//			ArrayList department = Departments;
//			ArrayList courses = Courses;
//			ArrayList students = Students;
//
//		};
//
//		Gson gson = new Gson();
//		try {
//			gson.toJson(Departments, new FileWriter("results2.ser"));
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	public static void actorType(ArrayList list, int flag) {
		for (Map.Entry<String, PrivateState> actor : actorThreadPool.getActors().entrySet()) {
			if (flag==0) {
				if (actor.getValue() instanceof DepartmentPrivateState) {
					list.add(actor.getValue());
				}
			}
			if (flag==1) {
				if (actor.getValue() instanceof CoursePrivateState) {
					list.add(actor.getValue());
				}
			}
			if (flag==2) {
				if (actor.getValue() instanceof StudentPrivateState) {
					list.add(actor.getValue());
				}
			}
		}
	}
}
