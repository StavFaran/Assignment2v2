package bgu.spl.a2.sim.actions.UnregisterAction;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.LinkedList;

public class RemoveFromGrades extends Action {
    private String courseName;

    public RemoveFromGrades(String courseName){
        this.courseName = courseName;
    }

    @Override
    protected void start() {
        then(new LinkedList<>(), ()->{
            ((StudentPrivateState)actorState).getGrades().remove(courseName);
            complete(0);
        });



    }
}
