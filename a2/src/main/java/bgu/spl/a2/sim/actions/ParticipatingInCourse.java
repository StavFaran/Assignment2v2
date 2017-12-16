package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ParticipatingInCourse extends Action {
    private DepartmentPrivateState myDepState;
    private String courseName;
    private CoursePrivateState courseState;
    private String studentId;
    private StudentPrivateState studentState;

    public ParticipatingInCourse(String courseName, CoursePrivateState courseState,
                                 String studentId, StudentPrivateState studentState){
        actionName = "Participating In Course";
        this.courseName = courseName;
        this.courseState = courseState;
        this.studentId = studentId;
        this.studentState = studentState;
    }
    @Override
    protected void start() {
        LinkedList<Action> listOfActions = new LinkedList<>();

        Map<String, Integer> grades = studentState.getGrades();
        List<String> prerequisites = courseState.getPrequisites();
        listOfActions.add(new IsValidRegister(prerequisites, grades));


        then(listOfActions, ()->{
            if ((boolean) listOfActions.getFirst().getResult().get()){
                courseState.getRegStudents().add(studentId);
                courseState.setAvailableSpots(courseState.getAvailableSpots()-1);
              //  studentState.getGrades()// todo - what add to the student?!
            }
            complete(0);
        });
    }
}
