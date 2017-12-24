package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;

import java.util.LinkedList;

//This action is in the Course actor
public class AddSpaces extends Action {
    private int num;

    public AddSpaces(int num){
        actionName = "Add Spaces";
        this.num = num;
        actorState = actorThreadPool.getActors().get(actorId);
    }
    @Override
    protected void start() {
        CoursePrivateState state = (CoursePrivateState) actorThreadPool.getActors().get(actorId);
        //if the course is closed dont add
        state.setAvailableSpots(state.getAvailableSpots()+num);
        complete(0);
    }
}
