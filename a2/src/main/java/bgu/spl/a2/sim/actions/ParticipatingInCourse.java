package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.actions.ParticipatingInCourseActions.AddGradeToStudent;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.List;
import java.util.Map;

//This action is in the Course Actor
public class ParticipatingInCourse extends Action {
    private String courseName;
    private String studentName;
    private int courseGrade;
    private StudentPrivateState studentState;

    public ParticipatingInCourse(String courseName, String studentName, int courseGrade){
        actionName = "Participating In Course";
        this.courseName = courseName;
        this.studentName = studentName;
        this.courseGrade = courseGrade;

    }
    @Override
    protected void start() {
        CoursePrivateState state = (CoursePrivateState) actorThreadPool.getActors().get(actorId);
        studentState = (StudentPrivateState) actorThreadPool.getActors().get(studentName);

        if (state.getAvailableSpots() > 0) {
            boolean isValid = isValidRegister(state.getPrequisites(), studentState.getGrades());

            if (isValid) {
                state.getRegStudents().add(studentName);
                state.setAvailableSpots(state.getAvailableSpots() - 1);
                state.setRegistered(state.getRegistered()+1);
                actorThreadPool.submit(new AddGradeToStudent(courseName, courseGrade), studentName, new StudentPrivateState());
            }
            complete(0);
        }
    }

    public boolean isValidRegister(List<String> prerequisits, Map<String,Integer> grades){
        boolean isValid = true;
        for(int i=0; i<prerequisits.size() && isValid; i++){
            if(!grades.containsKey(prerequisits.get(i)))
                isValid = false;
            else{
                if (grades.get(prerequisits.get(i)) < 56)
                    isValid = false;
            }
        }
        return isValid;
    }
}
