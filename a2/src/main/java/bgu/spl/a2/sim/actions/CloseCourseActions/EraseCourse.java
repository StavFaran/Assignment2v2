package bgu.spl.a2.sim.actions.CloseCourseActions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.actions.Unregister;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.LinkedList;

public class EraseCourse extends Action {

    public EraseCourse(){
        actorState = actorThreadPool.getPrivaetState(actorId);
    }

    @Override
    protected void start() {
        LinkedList<Action> listOfActions = new LinkedList<>();

        //This will send every student his unregister function
        for(String regStudents: ((CoursePrivateState)actorState).getRegStudents()) {
            Action action = new Unregister(regStudents);
            listOfActions.add(action);
            actorThreadPool.submit(action, regStudents, new StudentPrivateState());
        }

        then(listOfActions, ()->{
            ((CoursePrivateState)actorState).setAvailableSpots(-1);
            complete(0);
        });



    }
}
