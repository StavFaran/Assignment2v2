package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.LinkedList;

public class AddNewStudent extends Action{
    private String studentId;

    public AddNewStudent(String studentId){
        actionName = "Add a new Student";
        this.studentId = studentId;
    }
    @Override
    protected void start() {
        LinkedList<Action> listOfActions = new LinkedList<>();

        listOfActions.add(new Action() {
            @Override
            protected void start() {
                then(new LinkedList<>(), ()->{
                    complete(0);
                });
            }
        });

        sendMessage(listOfActions.getFirst(), studentId, new StudentPrivateState());

        then(listOfActions, ()->{
            ((DepartmentPrivateState)actorState).getStudentList().add(studentId);
            complete(0);
        });
    }
}
