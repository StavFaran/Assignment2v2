package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.ArrayList;
import java.util.LinkedList;

//This Action is in the Department actor
public class OpenNewCourse extends Action<Boolean> {

    private CoursePrivateState courseState;
    private String courseName;

    public OpenNewCourse(String courseName, int availableSpots, LinkedList<String> prerequisities){
        actionName = "Open a new Course";
        this.courseName = courseName;
        courseState = new CoursePrivateState();
        courseState.setName(courseName);
        courseState.setAvailableSpots(availableSpots);
        courseState.setPrequisites(prerequisities);
    }
    @Override
    protected void start() {
        DepartmentPrivateState state = (DepartmentPrivateState) actorThreadPool.getActors().get(actorId);

        actorThreadPool.submit(null, courseName, courseState);
        state.getCourseList().add(courseName);
        complete(true);
    }
}
