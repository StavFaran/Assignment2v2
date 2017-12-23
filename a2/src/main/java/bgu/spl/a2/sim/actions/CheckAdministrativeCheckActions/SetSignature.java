package bgu.spl.a2.sim.actions.CheckAdministrativeCheckActions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class SetSignature extends Action {
    private long sig;
    public SetSignature(long sig){
        this.sig = sig;
    }
    @Override
    protected void start() {
        ((StudentPrivateState)actorState).setSignature(sig);
        complete(0);
    }
}
