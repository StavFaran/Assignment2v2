/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.spl.a2.sim;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import bgu.spl.a2.Action;
import bgu.spl.a2.ActorThreadPool;
import bgu.spl.a2.PrivateState;
import bgu.spl.a2.sim.actions.AddNewStudent;
import bgu.spl.a2.sim.actions.OpenNewCourse;
import bgu.spl.a2.sim.actions.Unregister;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

/**
 * A class describing the simulator for part 2 of the assignment
 */
public class Simulator {

	
	public static ActorThreadPool actorThreadPool;
	
	/**
	* Begin the simulation Should not be called before attachActorThreadPool()
	*/
    public static void start(){
		//TODO: replace method body with real implementation
		throw new UnsupportedOperationException("Not Implemented Yet.");
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
			//start();

			Warehouse warehouse = new Warehouse((List<Map<String, String>>) data.get("Computers"));

			Phase((List<Map<String, Object>>) data.get("Phase 1"));
			Phase((List<Map<String, Object>>) data.get("Phase 2"));
			Phase((List<Map<String, Object>>) data.get("Phase 3"));

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
		String actor;
		PrivateState actorState;
		switch((String)actionToAdd.get("Action")) {
			case "Open Course":
				actor = (String)actionToAdd.get("Department");
				String courseName = (String)actionToAdd.get("Course");
				int availableSpots = (int)actionToAdd.get("Space");
				LinkedList<String> prerequisits = (LinkedList)actionToAdd.get("Prerequisites");
				actorThreadPool.submit(new OpenNewCourse(courseName, availableSpots, prerequisits), actor, new DepartmentPrivateState());
				break;
			case "Add Student":
				actor = (String)actionToAdd.get("Department");
				String studentName = (String)actionToAdd.get("Student");
				actorThreadPool.submit(new AddNewStudent(studentName), actor, new StudentPrivateState());
				break;
			case "Participate In Course":
				actor = (String)actionToAdd.get("Course");
				actorThreadPool.submit(new AddNewStudent((String)actionToAdd.get("Student")), actor, new CoursePrivateState());
				break;
			case "Unregister":
				actor = (String)actionToAdd.get("Course");
				actorThreadPool.submit(new Unregister((String)actionToAdd.get("Student")), actor, new CoursePrivateState());
				break;
			case "Close Course":
				actor = (String)actionToAdd.get("Course");
				actorThreadPool.submit(new Unregister((String)actionToAdd.get("Student")), actor, new CoursePrivateState());
				break;
		}

	}
}
