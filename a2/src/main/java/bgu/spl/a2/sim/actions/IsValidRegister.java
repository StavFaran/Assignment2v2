package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class IsValidRegister extends  Action{
    private List<String> prerequisits;
    private Map<String,Integer> grades;

    public IsValidRegister(List<String> prerequisits, Map<String,Integer> grades){
        actionName = "isValidRegister";
        this.prerequisits = prerequisits;
        this.grades = grades;
    }
    @Override
    protected void start() {
        LinkedList<Action> listOfActions = new LinkedList<>();

        then(listOfActions, ()->{
            boolean isValid = true;
            for(int i=0; i<prerequisits.size() && isValid; i++){
                if(!grades.containsKey(prerequisits.get(i)))
                    isValid = false;
                else{
                    if (grades.get(prerequisits.get(i)) < 56)
                        isValid = false;
                }
            }

            complete(isValid);
        });
    }
}
