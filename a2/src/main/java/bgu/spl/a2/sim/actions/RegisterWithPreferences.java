package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.LinkedList;

//This Action is in the Student's actor.
public class RegisterWithPreferences extends Action{
    private LinkedList<String> preferenceList;

    public RegisterWithPreferences(LinkedList<String> preferenceList){
        actionName = "Register With Preferences";
        this.preferenceList = preferenceList;
    }
    @Override
    protected void start() {
        LinkedList<Action> listOfActions = new LinkedList<>();

        if (!preferenceList.isEmpty() && ((StudentPrivateState) actorState).getGrades().containsKey(preferenceList.getFirst())) {
            int grade = ((StudentPrivateState) actorState).getGrades().get(preferenceList.getFirst());
            listOfActions.add(new ParticipatingInCourse(preferenceList.getFirst(), actorId, grade));

            then(listOfActions, () -> {
                if (!preferenceList.isEmpty() && !listOfActions.isEmpty() && !(boolean) listOfActions.getFirst().getResult().get()) {
                    preferenceList.removeFirst();
                    sendMessage(new RegisterWithPreferences(preferenceList), actorId, new CoursePrivateState());
                }
                complete(0);
            });
        }
        //TODO Recheck this
    }
}
