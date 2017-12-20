package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.actions.CloseCourseActions.EraseCourse;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

import java.util.LinkedList;

public class CloseCourse extends Action {

    private DepartmentPrivateState depState;
    private String courseName;
    private String departmentName;

    public CloseCourse(String departmentName, String courseName){
        actionName = "Close A Course";
        this.courseName = courseName;
        this.departmentName = departmentName;
        depState = (DepartmentPrivateState)actorThreadPool.getPrivaetState(departmentName);
    }
    @Override
    protected void start() {
        LinkedList<Action> listOfActions = new LinkedList<>();

        listOfActions.add(new EraseCourse());

        then(listOfActions, ()->{
            depState.getCourseList().remove(courseName);
            complete(0);
        });
    }


}


