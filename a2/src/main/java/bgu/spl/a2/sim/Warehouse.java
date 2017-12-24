package bgu.spl.a2.sim;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * represents a warehouse that holds a finite amount of computers
 *  and their suspended mutexes.
 * 
 */
public class Warehouse {
    private Map< String, SuspendingMutex> computerList;

    public Warehouse(List<Map<String,String>> list){
        computerList = new HashMap<>();
        for(Map<String, String> map: list){
            Computer computer = new Computer(map.get("Type"));
            computerList.put( map.get("Type"), new SuspendingMutex(computer));
        }
    }
    public Map<String, SuspendingMutex> getComputers(){
        return computerList;
    }
//    public void addComputer(String type, long sigSuccess, long sigFail){
//        computers.put(type, new SuspendingMutex(computers.));
//    }
	
}
