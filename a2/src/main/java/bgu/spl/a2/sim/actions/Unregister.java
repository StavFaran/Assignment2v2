package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.actions.UnregisterAction.RemoveFromGrades;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.LinkedList;

public class Unregister extends Action {
        private String studentId;

        public Unregister(String studentId){
            actionName = "Unregister";
            this.studentId = studentId;
            actorState = actorThreadPool.getPrivaetState(actorId);
        }
    @Override
    protected void start() {
        LinkedList<Action> listOfActions = new LinkedList<>();

        listOfActions.add (new Action(){
            @Override
            protected void start() {
                then(new LinkedList<>(), ()->{
                    boolean result = ((CoursePrivateState)actorState).getRegStudents().contains(studentId);
                    complete(result);
                });
            }
        });

        then(listOfActions, ()->{
            actorThreadPool.submit(new RemoveFromGrades(actorId), studentId, new StudentPrivateState());
            ((CoursePrivateState)actorState).getRegStudents().add(studentId);
            ((CoursePrivateState)actorState).setAvailableSpots(((CoursePrivateState)actorState).getAvailableSpots()-1);
            complete(0);
        });
    }
}

