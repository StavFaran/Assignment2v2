package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;

import java.util.LinkedList;
import java.util.List;

public class RegisterWithPreferences extends Action{
    private LinkedList<String> preferenceList;

    public RegisterWithPreferences(LinkedList<String> preferenceList){
        actionName = "Register With Preferences";
        this.preferenceList = preferenceList;
    }
    @Override
    protected void start() {
        LinkedList<Action> listOfActions = new LinkedList<>();

        preferenceList.
        for(String preference: preferenceList)
            listOfActions.add(new );

        then(listOfActions, ()->{
            if ((boolean) listOfActions.getFirst().getResult().get()){
                courseState.getRegStudents().add(studentName);
                courseState.setAvailableSpots(courseState.getAvailableSpots()-1);
            }
            complete(0);
        });
    }
}
