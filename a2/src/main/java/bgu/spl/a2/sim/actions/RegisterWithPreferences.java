package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;

import java.util.LinkedList;

public class RegisterWithPreferences extends Action{
    private LinkedList<String> preferenceList;

    public RegisterWithPreferences(LinkedList<String> preferenceList){
        actionName = "Register With Preferences";
        this.preferenceList = preferenceList;
    }
    @Override
    protected void start() {
        LinkedList<Action> listOfActions = new LinkedList<>();

        if (!preferenceList.isEmpty())
            listOfActions.add(new ParticipatingInCourse(preferenceList.getFirst(), actorId));

        then(listOfActions, ()->{
            if (!(boolean) listOfActions.getFirst().getResult().get()){
                preferenceList.removeFirst();
                sendMessage(new RegisterWithPreferences(preferenceList), actorId, new CoursePrivateState());
            }
            complete(0);
        });
    }
}
