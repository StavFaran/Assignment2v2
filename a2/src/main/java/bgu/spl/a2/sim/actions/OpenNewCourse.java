package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.LinkedList;

public class OpenNewCourse extends Action {

    private String departmentName;
    private String courseName;
    private int spotsAvailable;
    private LinkedList<String> prerequisites;

    public OpenNewCourse(String departmentName, String courseName,int spots, LinkedList<String> prerequisities){
        actionName = "Open a new Course";
        this.departmentName = departmentName;
        this.courseName = courseName;
        this.spotsAvailable = spots;
        this.prerequisites = prerequisities;
    }
    @Override
    protected void start() {
        LinkedList<Action> listOfActions = new LinkedList<>();

        listOfActions.add(new Action() {
            @Override
            protected void start() {
                then(new LinkedList<>(), ()->{
                    ((CoursePrivateState)actorState).setAvailableSpots(spotsAvailable);
                    ((CoursePrivateState)actorState).setPrequisites(prerequisites);
                    complete(0);
                });
            }
        });

        sendMessage(listOfActions.getFirst(), courseName, new CoursePrivateState());

        then(listOfActions, ()->{
            ((DepartmentPrivateState)actorState).getCourseList().add(courseName);
            complete(0);
        });
    }
}
