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
        CoursePrivateState state = (CoursePrivateState) actorThreadPool.getActors().get(actorId);

        if (state.getRegStudents().contains(studentId)) {
            actorThreadPool.submit(new RemoveFromGrades(actorId), studentId, new StudentPrivateState());
            state.getRegStudents().remove(studentId);
            state.setRegistered(state.getRegistered() - 1);
            state.setAvailableSpots(state.getAvailableSpots() + 1);
        }
        complete(0);
    }
}


