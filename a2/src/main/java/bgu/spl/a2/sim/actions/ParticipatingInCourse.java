package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.actions.ParticipatingInCourseActions.AddGradeToStudent;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ParticipatingInCourse extends Action {
    private String courseName;
    private String studentName;
    private CoursePrivateState courseState;

    public ParticipatingInCourse(String courseName, String studentName){
        actionName = "Participating In Course";
        this.courseName = courseName;
        this.studentName = studentName;
        this.courseState = (CoursePrivateState)actorThreadPool.getPrivaetState(courseName);
    }
    @Override
    protected void start() {
        LinkedList<Action> listOfActions = new LinkedList<>();

        listOfActions.add(new AddGradeToStudent(studentName, courseName));

        then(listOfActions, ()->{
            if ((boolean) listOfActions.getFirst().getResult().get()){
                courseState.getRegStudents().add(studentName);
                courseState.setAvailableSpots(courseState.getAvailableSpots()-1);
            }
            complete(0);
        });
    }
}
