package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

import java.util.LinkedList;

public class OpenNewCourse extends Action {

    private DepartmentPrivateState myDepState;
    private String courseName;
    private int spotsAvailable;
    private LinkedList<String> prerequisites;

    public OpenNewCourse(String courseName,int spots, LinkedList<String> prerequisities
            , DepartmentPrivateState depState){
        actionName = "Open a new Course";
        this.courseName = courseName;
        myDepState = depState;
        spotsAvailable = spots;
        this.prerequisites = prerequisities;
    }
    @Override
    protected void start() {
        LinkedList<Action> listOfActions = new LinkedList<>();

        then(listOfActions, ()->{
            myDepState.getCourseList().add(courseName);
            CoursePrivateState coursePrivateState = new CoursePrivateState();
            coursePrivateState.setAvailableSpots(spotsAvailable);
            coursePrivateState.setPrequisites(prerequisites);
            complete(0);
        });
    }
}
