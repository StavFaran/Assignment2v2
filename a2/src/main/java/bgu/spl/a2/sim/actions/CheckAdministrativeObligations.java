package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.Computer;
import bgu.spl.a2.sim.SuspendingMutex;
import bgu.spl.a2.sim.actions.CheckAdministrativeCheckActions.GetGrade;
import bgu.spl.a2.sim.actions.CheckAdministrativeCheckActions.SetSignature;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

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
        this.computer = mutex.getComputer().getComputerType();
    }
    @Override
    protected void start() {
        LinkedList<Action> listOfActions = new LinkedList<>();

        Promise<Computer> promise = mutex.down(computer);

        promise.subscribe(()->{
            for(String student: students){
                Action action = new GetGrade();
                listOfActions.add(action);
                sendMessage(action, student, new StudentPrivateState());
            }
        });
        then(listOfActions, ()->{
            for (int i = 0; i < students.size(); i++) {
                long sig = mutex.getComputer().checkAndSign(conditions, (HashMap) listOfActions.get(i).getResult().get());
                sendMessage(new SetSignature(sig), students.get(i), new StudentPrivateState());
            }
        });

    }


}
