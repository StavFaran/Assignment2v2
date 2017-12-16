package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;

import java.util.LinkedList;

public class OpenNewCourse extends Action {

    public OpenNewCourse(String Department){
        actionName = "Open a New Course";
        
    }
    @Override
    protected void start() {
        LinkedList<Action> listOfActions = new LinkedList<>();

        then(listOfActions, ()->{
            System.out.println("I love cake");
            complete(0);
        });
    }
}
