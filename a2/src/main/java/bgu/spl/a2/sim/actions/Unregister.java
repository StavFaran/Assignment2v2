package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.actions.UnregisterAction.RemoveFromGrades;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.LinkedList;

//This action is in course actor
public class Unregister extends Action {
        private String studentId;

        public Unregister(String studentId){
            actionName = "Unregister";
            this.studentId = studentId;

        }
    @Override
    protected void start() {
        LinkedList<Action> listOfActions = new LinkedList<>();

        actorState = actorThreadPool.getPrivateState(actorId);

        if (((CoursePrivateState)actorState).getRegStudents().contains(studentId)) {

            then(listOfActions, () -> {
                actorThreadPool.submit(new RemoveFromGrades(actorId), studentId, new StudentPrivateState());
                ((CoursePrivateState) actorState).getRegStudents().add(studentId);
                ((CoursePrivateState) actorState).setAvailableSpots(((CoursePrivateState) actorState).getAvailableSpots() - 1);
                complete(0);
            });
        }else{complete(0);}
    }
}


