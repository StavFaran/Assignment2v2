package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.ArrayList;
import java.util.LinkedList;

//This Action is in the Student's actor.
public class RegisterWithPreferences extends Action{
    private ArrayList<String> preferences;
    private ArrayList<Integer> grades;

    public RegisterWithPreferences(ArrayList<String> preferences, ArrayList<Integer> grades){
        actionName = "Register With Preferences";
        this.preferences = preferences;
        this.grades = grades;
    }
    @Override
    protected void start() {
        StudentPrivateState state = (StudentPrivateState) actorThreadPool.getActors().get(actorId);

        LinkedList<Action> listOfActions = new LinkedList<>();

        if (!preferences.isEmpty() && state.getGrades().containsKey(preferences.get(0))) {
            listOfActions.add(new ParticipatingInCourse(preferences.get(0), actorId, grades.get(0)));

            then(listOfActions, () -> {
                if (!preferences.isEmpty() && !listOfActions.isEmpty() && !(boolean) listOfActions.getFirst().getResult().get()) {
                    preferences.remove(0);
                    grades.remove(0);
                    sendMessage(new RegisterWithPreferences(preferences, grades), actorId, new CoursePrivateState());
                }
                complete(0);
            });
        }
    }
}
