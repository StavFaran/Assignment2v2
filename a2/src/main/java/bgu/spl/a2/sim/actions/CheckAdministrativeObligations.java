package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.Computer;
import bgu.spl.a2.sim.SuspendingMutex;
import bgu.spl.a2.sim.actions.CheckAdministrativeCheckActions.GetGrade;
import bgu.spl.a2.sim.actions.CheckAdministrativeCheckActions.SetSignature;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class CheckAdministrativeObligations extends Action {
    private SuspendingMutex mutex;
    private ArrayList<String>conditions;
    private ArrayList<String> students;
    private String computer;

    public CheckAdministrativeObligations(SuspendingMutex mutex, ArrayList<String> conditions, ArrayList<String>students){
        actionName = "Administrative Check";
        this.mutex = mutex;
        this.conditions = conditions;
        this.students = students;
//        System.out.println(mutex.getComputer());
//        System.out.println(mutex.getComputer().getComputerType());
        this.computer = mutex.getComputer().getComputerType();
    }
    @Override
    protected void start() {
        Promise<Computer> promise = mutex.down();

        promise.subscribe(()->{
            for(String student: students){
                StudentPrivateState studentState = (StudentPrivateState)actorThreadPool.getActors().get(student);
                long sig = promise.get().checkAndSign(conditions, studentState.getGrades());
                actorThreadPool.submit(new SetSignature(sig), student, new StudentPrivateState());
            }
            try {
                mutex.up();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }


}
