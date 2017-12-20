package bgu.spl.a2.sim.actions.ParticipatingInCourseActions;

import bgu.spl.a2.Action;
import bgu.spl.a2.ActorThreadPool;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;
import sun.awt.image.ImageWatched;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class IsValidRegister extends  Action{
    private List<String> prerequisits;
    private Map<String,Integer> grades;

    public IsValidRegister(List<String> prerequisits){
        actionName = "isValidRegister";
        this.prerequisits = prerequisits ;
        this.grades = ((StudentPrivateState)actorThreadPool.getPrivaetState(actorId)).getGrades();
    }
    @Override
    protected void start() {

        then(new LinkedList<>(), ()->{
            boolean isValid = true;
            for(int i=0; i<prerequisits.size() && isValid; i++){
                if(!grades.containsKey(prerequisits.get(i)))
                    isValid = false;
                else{
                    if (grades.get(prerequisits.get(i)) < 56)
                        isValid = false;
                }
            }

            complete(isValid);
        });
    }
}
