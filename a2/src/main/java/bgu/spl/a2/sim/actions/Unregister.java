package bgu.spl.a2.sim.actions;


import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Unregister extends Action {
        private DepartmentPrivateState myDepState;
        private String courseName;
        private CoursePrivateState courseState;
        private String studentId;
        private StudentPrivateState studentState;

        public Unregister(String courseName, CoursePrivateState courseState,
                          String studentId, StudentPrivateState studentState){
            actionName = "Unregister";
            this.courseName = courseName;
            this.courseState = courseState;
            this.studentId = studentId;
            this.studentState = studentState;
        }
    @Override
    protected void start() {
        LinkedList<Action> listOfActions = new LinkedList<>();



        then(listOfActions, ()->{
            if (studentState.){
                courseState.getRegStudents().add(studentId);
                courseState.setAvailableSpots(courseState.getAvailableSpots()+1);

            }
            complete(0);
        });
    }
}


