package bgu.spl.a2.sim.actions.CheckAdministrativeCheckActions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

/**
 * Created by Stav on 12/23/2017.
 */
public class GetGrade extends Action {
    @Override
    protected void start() {
        complete(((StudentPrivateState)actorState).getGrades());
    }
}
