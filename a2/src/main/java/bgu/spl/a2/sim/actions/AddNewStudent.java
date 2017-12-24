package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.LinkedList;

//This Action is in the Department's actor.
public class AddNewStudent extends Action{
    private String studentName;

    public AddNewStudent(String studentName){
        actionName = "Add a new Student";
        this.studentName = studentName;
    }
    @Override
    protected void start() {
        DepartmentPrivateState state = (DepartmentPrivateState) actorThreadPool.getActors().get(actorId);

        state.getStudentList().add(studentName);
        actorThreadPool.submit(null, studentName, new StudentPrivateState());

        complete(0);
    }
}
