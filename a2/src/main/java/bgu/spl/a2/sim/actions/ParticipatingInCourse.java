package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.actions.ParticipatingInCourseActions.AddGradeToStudent;
import bgu.spl.a2.sim.actions.ParticipatingInCourseActions.IsValidRegister;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.LinkedList;

//This action is in the Course Actor
public class ParticipatingInCourse extends Action {
    private String courseName;
    private String studentName;
    private CoursePrivateState courseState;
    private int courseGrade;

    public ParticipatingInCourse(String courseName, String studentName, int courseGrade){
        actionName = "Participating In Course";
        this.courseName = courseName;
        this.studentName = studentName;
        this.courseGrade = courseGrade;

    }
    @Override
    protected void start() {
        LinkedList<Action> listOfActions = new LinkedList<>();

        this.courseState = (CoursePrivateState)actorThreadPool.getPrivateState(courseName);

        if (courseState.getAvailableSpots()-courseState.getRegistered() > 0) {
            listOfActions.add(new IsValidRegister(courseState.getPrequisites()));

            sendMessage(listOfActions.getFirst(), studentName, new StudentPrivateState());

            then(listOfActions, () -> {
                if ((boolean) listOfActions.getFirst().getResult().get()) {
                    courseState.getRegStudents().add(studentName);
                    courseState.setAvailableSpots(courseState.getAvailableSpots() - 1);
                    sendMessage(new AddGradeToStudent(courseName, courseGrade), studentName, new StudentPrivateState());
                }
                complete(0);
            });
        }else complete(0);
    }
}
