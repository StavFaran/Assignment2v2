package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.actions.OpenNewCourseActions.SetCourseInitials;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.ArrayList;
import java.util.LinkedList;

//This Action is in the Department actor
public class OpenNewCourse extends Action {

    private String courseName;
    private int availableSpots;
    private LinkedList<String> prerequisites;

    public OpenNewCourse(String courseName, int availableSpots, LinkedList<String> prerequisities){
        actionName = "Open a new Course";
        this.courseName = courseName;
        this.availableSpots = availableSpots;
        this.prerequisites = prerequisities;
    }
    @Override
    protected void start() {
        LinkedList<Action> listOfActions = new LinkedList<>();

        listOfActions.add(new SetCourseInitials(availableSpots, prerequisites));

        sendMessage(listOfActions.getFirst(), courseName, new CoursePrivateState());

        then(listOfActions, ()->{
            ((DepartmentPrivateState)actorState).getCourseList().add(courseName);
            complete(0);
        });
    }
}
