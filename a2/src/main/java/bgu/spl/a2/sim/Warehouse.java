package bgu.spl.a2.sim;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * represents a warehouse that holds a finite amount of computers
 *  and their suspended mutexes.
 * 
 */
public class Warehouse {
    private HashMap< Computer, SuspendingMutex> computers;

    public Warehouse(List list){
        computers = new HashMap<>();
    }
    public HashMap<Computer, SuspendingMutex> getComputers(){
        return computers;
    }
    public void addComputer(String type, long sigSuccess, long sigFail){
        //computers.put(type, )
    }
	
}
