package bgu.spl.a2.sim.actions.ParticipatingInCourseActions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;

import java.util.LinkedList;

public class IsSpotsAvailable extends Action {

    //Probbably will be changed to atomic
    private CoursePrivateState courseState;
    private int spotsAvailable;
    private int registered;

    public IsSpotsAvailable(String courseName){
        courseState = ((CoursePrivateState)actorThreadPool.getActors().get(courseName));
        this.spotsAvailable = courseState.getAvailableSpots();
        this.registered = courseState.getRegistered();
    }
    @Override
    protected void start() {

        then(new LinkedList<>(), ()->{
            complete(spotsAvailable - registered > 0);
        });
    }
}
