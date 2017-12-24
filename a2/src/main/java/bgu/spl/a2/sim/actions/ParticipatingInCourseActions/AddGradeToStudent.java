package bgu.spl.a2.sim.actions.ParticipatingInCourseActions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.LinkedList;
import java.util.Map;

//This action is in the Student's actor
public class AddGradeToStudent extends Action{
    private String courseName;
    private int grade;
    private Map grades;

    public AddGradeToStudent (String courseName, int grade){
        this.courseName = courseName;
        this.grade = grade;

    }


    @Override
    protected void start() {
        grades = ((StudentPrivateState)actorThreadPool.getActors().get(actorId)).getGrades();
        grades.put(courseName, grade);
        complete(0);
    }
}
