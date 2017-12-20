package bgu.spl.a2.sim.actions.ParticipatingInCourseActions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.LinkedList;
import java.util.Map;

public class AddGradeToStudent extends Action{
    private String studentName;
    private String courseName;
    private Map grades;

    public AddGradeToStudent (String studentName, String courseName){
        this.studentName = studentName;
        this.courseName = courseName;
        grades = ((StudentPrivateState)actorThreadPool.getActors().get(studentName)).getGrades();
    }


    @Override
    protected void start() {
        LinkedList<Action> listOfActions = new LinkedList<>();

        listOfActions.add(new IsSpotsAvailable(courseName));
        listOfActions.add(new IsValidRegister(studentName, courseName));


        then(listOfActions, ()->{
            boolean result = (boolean)listOfActions.get(0).getResult().get() && (boolean)listOfActions.get(1).getResult().get();
            if (result)
                grades.put(courseName, 0);
            complete(result);
        });
    }
}
