package bgu.spl.a2;

import java.util.LinkedList;

public class Actor {
    private String id;
    private LinkedList<Action> myList;
    private PrivateState privateState;

    public Actor(){}

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    public void addActionToList(Action action){
        myList.add(action);
    }
    public PrivateState getPrivateState(){
        return privateState;
    }
}
