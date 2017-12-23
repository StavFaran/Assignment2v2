package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.actions.CloseCourseActions.EraseCourse;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

import java.util.LinkedList;

//This action is in the Department actor
public class CloseCourse extends Action {

    private DepartmentPrivateState depState;
    private String courseName;

    public CloseCourse(String courseName){
        actionName = "Close A Course";
        this.courseName = courseName;
        depState = (DepartmentPrivateState)actorThreadPool.getPrivateState(actorId);
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


