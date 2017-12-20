package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.LinkedList;

public class AddNewStudent extends Action{
    private String studentName;

    public AddNewStudent(String studentId){
        actionName = "Add a new Student";
        this.studentName = studentName;
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

        sendMessage(listOfActions.getFirst(), studentName, new StudentPrivateState());

        then(listOfActions, ()->{
            ((DepartmentPrivateState)actorState).getStudentList().add(studentName);
            complete(0);
        });
    }
}
